package ml.tianhong.rwh.maintain.portal.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RCustomer;
import ml.tianhong.rwh.maintain.portal.entity.vo.UserVO;
import ml.tianhong.rwh.maintain.portal.service.RCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Api("用户Controller")
@RestController
@RequestMapping("/portal/r-customer")
public class RCustomerController {
    @Autowired
    private RCustomerService rCustomerService;

    @ApiOperation("测试环境搭建是否成功")
    @GetMapping("/hello")
    public ResultVO hello() {
        return ResultVO.ok().message("hello world");
    }

    @ApiOperation("注册控制，如果新注册用户信息合法，则会向注册邮箱发送激活邮件")
    @PostMapping("/register")
    public ResultVO register(UserVO user) {
        if (!rCustomerService.matchPhone(user.getPhone()) || !rCustomerService.matchEmail(user.getEmail())) {
            return ResultVO.error().message("手机或邮箱格式不正确");
        }
        rCustomerService.register(user);
        return ResultVO.ok();
    }

    @ApiOperation("激活邮箱验证控制，点击邮件链接后传入token，测试时可查看服务器控制台打印的token，如果命中cache中的token，激活用户")
    @GetMapping("/validate/{token}")
    public ResultVO validateClick(@PathVariable String token) {
        String validate = rCustomerService.validate(token);
        if (!StringUtils.isEmpty(validate)) {
            RCustomer updateRS = new RCustomer().setId(validate).setDisable("0");
            rCustomerService.updateById(updateRS);
            return ResultVO.ok().data("token", token);
        }
        return ResultVO.error();
    }

    @ApiOperation("登录控制，使用唯一邮箱和密码验证，通过则，生成该用户的token保存在服务器session并将用户token推送到用户浏览器cookie中")
    @PostMapping("/login")
    public ResultVO login(HttpServletResponse response, HttpServletRequest request, @RequestParam String email, @RequestParam String password) {
        return rCustomerService.login(email, password, request.getSession(), response);
    }

    @ApiOperation("忘记密码控制，验证账号邮箱和手机号，通过验证则重置密码为123456")
    @PostMapping("/forgetPassword")
    public ResultVO forgetPassword(@RequestParam String email, @RequestParam String phone) {
        return rCustomerService.forgetPassword(email, phone);
    }

    @ApiOperation("用户登录后修改密码控制，需要校验用户邮箱和原密码，通过校验则更新密码为新输入密码,同时强制用户端登出")
    @PostMapping("/updatePassword")
    public ResultVO updatePassword(HttpServletResponse response, HttpServletRequest request, @RequestParam String email, @RequestParam String oldPwd, @RequestParam String newPwd) {
        return rCustomerService.updatePassword(response, request, email, oldPwd, newPwd);
    }

    @ApiOperation("登出控制，清除服务器session并清除用户浏览器cookie")
    @PostMapping("/logout")
    public ResultVO logout(HttpServletResponse response, HttpServletRequest request) {
        request.getSession().removeAttribute("token");

        // 将Cookie的值设置为null
        Cookie cookie = new Cookie("token", null);
        //将`Max-Age`设置为0
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResultVO.ok();
    }

    @ApiOperation("通过token获取用户信息控制，获取HttpServletRequest对应的session，并从session中取到携带id信息的token，查询到用户对象，返回json格式的用户对象")
    @PostMapping("/getUserInfo")
    public ResultVO getUserInfoByToken(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        if (token == null) return ResultVO.error().message("请先登录");
        return rCustomerService.getUserInfoByToken(token);
    }

}

