package ml.tianhong.rwh.maintain.common.util;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

}
