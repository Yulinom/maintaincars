package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RBrand;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
public interface RBrandService extends IService<RBrand> {

    ResultVO addBrand(String brand);

    ResultVO getBrandByName(String brand);
}
