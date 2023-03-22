package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.maintain.common.api.ResultVO;
import org.springframework.web.bind.annotation.RequestParam;

public interface AdminService {
    ResultVO login(String adminName, String adminPwd);
    ResultVO getAdminToken(String adminName, String adminPwd);
}
