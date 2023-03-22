package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RCustomer;
import ml.tianhong.rwh.maintain.portal.entity.vo.UserVO;
import ml.tianhong.rwh.maintain.portal.mapper.RCustomerMapper;
import ml.tianhong.rwh.maintain.portal.service.EmailService;
import ml.tianhong.rwh.maintain.portal.service.RCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ml.tianhong.rwh.common.util.JwtUtils;
import ml.tianhong.rwh.common.util.MD5;
import ml.tianhong.rwh.common.util.MyException;
import ml.tianhong.rwh.common.util.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Service
public class RCustomerServiceImpl extends ServiceImpl<RCustomerMapper, RCustomer> implements RCustomerService {

    @Autowired
    private EmailService emailService;

    @Override
    public void register(UserVO registerVo) {
        //获取注册的数据
        String mobile = registerVo.getPhone(); //手机号
        String nickname = registerVo.getName(); //昵称
        String password = registerVo.getPassword(); //密码
        String email = registerVo.getEmail(); //邮箱

        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email) || StringUtils.isEmpty(nickname)) {
            throw new MyException(20001, "注册失败");
        }

        //判断邮箱是否重复，表里面存在相同邮箱不进行添加
        QueryWrapper<RCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new MyException(20001, "邮箱已被注册，注册失败");
        }

        RCustomer ucenterMember = new RCustomer();
        ucenterMember.setPhone(mobile)
                .setEmail(email)
                .setName(nickname)
                .setDisable("1");
        ucenterMember.setPassword(MD5.encrypt(password));
        baseMapper.insert(ucenterMember);

        //发送激活邮件
        String authMail = generateAuthMail(ucenterMember);
        TokenCache.setKey(ucenterMember.getId(), authMail);
        emailService.sendEmail(email, authMail);
        ucenterMember.setPassword(null);
    }

    @Override
    public String validate(String token) {
        String id = JwtUtils.getMemberIdByJwtToken(token);
        if (TokenCache.getKey(id).equals(token)) {
            return id;
        }
        return "";
    }

    @Override
    public ResultVO login(String email, String password, HttpSession session, HttpServletResponse response) {
        QueryWrapper<RCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        queryWrapper.eq("password", MD5.encrypt(password));
        RCustomer selectOne = baseMapper.selectOne(queryWrapper);
        if (selectOne != null) {
            String token = JwtUtils.getJwtToken(selectOne.getId(), new Date().toString());
            session.setAttribute("token", token);
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            return ResultVO.ok();
        }
        return ResultVO.error().message("登录失败，邮箱或密码错误");
    }

    @Override
    public ResultVO forgetPassword(String email, String phone) {
        if (matchEmail(email) && matchPhone(phone)) {
            QueryWrapper<RCustomer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", email).eq("phone", phone);
            RCustomer selectOne = baseMapper.selectOne(queryWrapper);
            if (selectOne != null) {
                //验证通过，重置密码
                selectOne.setPassword(MD5.encrypt("123456"));
                baseMapper.updateById(selectOne);
                emailService.sendNotice(email, "您的重置密码为123456", "汽车维修中心通知消息");
                return ResultVO.ok();
            }
            return ResultVO.error().message("邮箱或密码错误");
        }
        return ResultVO.error().message("邮箱或电话号码格式错误");
    }

    @Override
    public ResultVO updatePassword(HttpServletResponse response, HttpServletRequest request, String email, String oldPwd, String newPwd) {
        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd) || (!matchEmail(email))) {
            return ResultVO.error().message("请填写完整");
        }
        QueryWrapper<RCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email).eq("password", MD5.encrypt(oldPwd));
        RCustomer selectOne = baseMapper.selectOne(wrapper);
        if (selectOne != null) {
            selectOne.setPassword(MD5.encrypt(newPwd));
            baseMapper.updateById(selectOne);

            //服务器存储session
            request.getSession().removeAttribute("token");

            // 将Cookie的值设置为null
            Cookie cookie = new Cookie("token", null);
            //将`Max-Age`设置为0
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResultVO.ok();
        } else return ResultVO.error().message("邮箱或密码错误");
    }

    @Override
    public ResultVO getUserInfoByToken(String token) {
        String id = JwtUtils.getMemberIdByJwtToken(token);
        QueryWrapper<RCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return ResultVO.ok().data("data", baseMapper.selectOne(queryWrapper));
    }


    private String generateAuthMail(RCustomer user) {
        return JwtUtils.getJwtToken(user.getId(), user.getName());
    }

    /**
     * 正则表达式校验手机号，包括非空校验和格式校验
     *
     * @param phone
     * @return
     */
    public boolean matchPhone(String phone) {
        if (!StringUtils.isEmpty(phone)) {
            String regex_phone = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";//匹配phone正则
            Pattern p_phone = Pattern.compile(regex_phone);
            Matcher m = p_phone.matcher(phone);
            boolean isMatch = m.matches();
            if (isMatch) {
                return true;
            } else return false;
        }
        return false;
    }

    public boolean matchEmail(String email) {
        if (!StringUtils.isEmpty(email)) {
            String regex_email = "[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9]+)+";//匹配email正则
            Pattern p_email = Pattern.compile(regex_email);
            if (p_email.matcher(email).matches()) {
                return true;
            } else return false;
        }
        return false;
    }


}
