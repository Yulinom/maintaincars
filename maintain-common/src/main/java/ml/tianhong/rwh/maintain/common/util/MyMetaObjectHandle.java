package ml.tianhong.rwh.maintain.common.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandle implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //gmtCreate传的是类中的属性名
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtUpdate",new Date(),metaObject);
        this.setFieldValByName("deleted","0",metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtUpdate",new Date(),metaObject);
    }
}
