package ml.tianhong.rwh.maintain.portal.mapper;

import ml.tianhong.rwh.maintain.portal.entity.ROrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ml.tianhong.rwh.maintain.portal.entity.bo.OrderBO;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
public interface ROrderMapper extends BaseMapper<ROrder> {
    ArrayList<ROrder> getOrdersByUserId(String userId);
}
