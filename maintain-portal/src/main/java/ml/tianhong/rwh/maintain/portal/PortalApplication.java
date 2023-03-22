package ml.tianhong.rwh.maintain.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {"ml.tianhong.rwh.maintain"})
@EnableWebMvc   //接口文档地址：http://127.0.0.1:8080/swagger-ui.html
public class PortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class);
    }
}
