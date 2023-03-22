package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RCar;
import com.baomidou.mybatisplus.extension.service.IService;
import ml.tianhong.rwh.maintain.portal.entity.vo.CarVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
public interface RCarService extends IService<RCar> {

    ResultVO addCar(String token, CarVO car);

    ResultVO getCarsByToken(HttpServletRequest request);

    /**
     *
     * @param id
     * @param request 避免横向越权
     * @return 查询成功则返回一个json格式的data对象，里面是查询到的单个car对象的信息
     */
    ResultVO getCarById(String id, HttpServletRequest request);

    ResultVO deleteCarById(HttpServletRequest request, String id);
}
