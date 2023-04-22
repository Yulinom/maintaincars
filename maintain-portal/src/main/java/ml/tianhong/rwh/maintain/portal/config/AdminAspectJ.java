package ml.tianhong.rwh.maintain.portal.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.common.util.MyException;
import ml.tianhong.rwh.maintain.common.util.TokenCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AdminAspectJ {

    @Pointcut("execution(public * ml.tianhong.rwh.maintain.portal.controller.AdminController.login(..))")
    public void point() {
    }

    @Pointcut("execution(public * ml.tianhong.rwh.maintain.portal.controller.AdminController.*(..))")
    public void point2() {
    }

    @Around("point2() && !point()")
    public Object validateAdmin(ProceedingJoinPoint pjp) {
        List<Object> args = Arrays.asList(pjp.getArgs());
        HttpServletRequest request = (HttpServletRequest) args.get(0);
        String token = (String) request.getSession().getAttribute("admin");
        System.out.println(token);
        try {
            if (request!=null && !StringUtils.isEmpty(token) && TokenCache.getLongKey("admin").equals(token)) {
                return pjp.proceed();
            } else return ResultVO.error().message("请先登录管理员");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return ResultVO.error().message("服务异常");
        }
    }

}
