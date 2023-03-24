package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RCarModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
public interface RCarModelService extends IService<RCarModel> {

    ResultVO addModel(String model, String brand);

    ResultVO getModelById(String id);

    ResultVO getModels();
}
