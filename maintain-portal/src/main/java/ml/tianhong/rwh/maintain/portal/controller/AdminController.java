package ml.tianhong.rwh.maintain.portal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Api("管理页Controller")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     *
     * @param adminName 管理员名字，可在application.yml文件中配置
     * @param adminPwd 管理员密码
     * @return
     */
    @ApiOperation("管理员登录控制")
    @PostMapping("/login")
    public ResultVO login(@RequestParam String adminName, @RequestParam String adminPwd){
        return adminService.login(adminName,adminPwd);
    }

    @ApiOperation("获取管理员操作同行token控制器")
    @PostMapping("/getAdminToken")
    public ResultVO getAdminToken(@RequestParam String adminName, @RequestParam String adminPwd){
        return adminService.getAdminToken(adminName,adminPwd);
    }

//    public ResultVO addCustomer()
}
