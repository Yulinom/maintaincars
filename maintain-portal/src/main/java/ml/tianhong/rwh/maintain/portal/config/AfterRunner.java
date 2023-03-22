package ml.tianhong.rwh.maintain.portal.config;

import ml.tianhong.rwh.maintain.portal.service.RCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * springboot程序启动后执行初始化任务：
 *  -创建临时用户
 */
@Component
@Order(value = 1)
public class AfterRunner implements ApplicationRunner {
    @Autowired
    private RCustomerService rCustomerService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
