package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import ml.tianhong.rwh.maintain.portal.entity.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
public interface RCustomerService extends IService<RCustomer> {

    void register(UserVO registerVo);

    boolean matchPhone(String phone);
    boolean matchEmail(String email);

    String validate(String token);
    ResultVO login(String email, String password, HttpSession session, HttpServletResponse response);

    ResultVO forgetPassword(String email, String phone);

    ResultVO updatePassword(HttpServletResponse response, HttpServletRequest request, String email, String oldPwd, String newPwd);

    ResultVO getUserInfoByToken(String token);
}
