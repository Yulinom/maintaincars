package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RTimeSet;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rwh
 * @since 2023-03-24
 */
public interface RTimeSetService extends IService<RTimeSet> {

    ResultVO addTimeSet(Date startTime, Date endTime);

    ResultVO getTimeSets();

    ResultVO getTimeSetById(String id);
}
