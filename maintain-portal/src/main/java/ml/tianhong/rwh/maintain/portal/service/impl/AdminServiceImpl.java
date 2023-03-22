package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.common.util.JwtUtils;
import ml.tianhong.rwh.maintain.common.util.TokenCache;
import ml.tianhong.rwh.maintain.portal.service.AdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService {
    @Value("adminAccount.name")
    private String adminName;
    @Value("adminAccount.password")
    private String adminPwd;

    @Override
    public ResultVO login(String adminName, String adminPwd) {
        if (StringUtils.isEmpty(adminName) || StringUtils.isEmpty(adminPwd))
            return ResultVO.error().message("管理员账号或密码不能为空");
        if (adminName.equals(this.adminName) && adminPwd.equals(this.adminPwd)) {
            String token = generateToken();
            return ResultVO.ok().data("adminToken", token);
        } else return ResultVO.error().message("管理员账号或密码错误");
    }

    @Override
    public ResultVO getAdminToken(String adminName, String adminPwd) {
        if (login(adminName, adminPwd).getSuccess()) {
            String token;
            String cacheToken = TokenCache.getKey("admin");
            if (!StringUtils.isEmpty(cacheToken)) token = cacheToken;
            else token = generateToken();

            return ResultVO.ok().data("adminToken", token);
        } else return ResultVO.error();
    }

    private String generateToken() {
        String token = JwtUtils.getJwtToken("admin", new Date().toString());
        TokenCache.setLongKey("admin", token);
        return token;
    }

}
