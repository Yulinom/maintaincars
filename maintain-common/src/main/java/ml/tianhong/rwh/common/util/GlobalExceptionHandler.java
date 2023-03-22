package ml.tianhong.rwh.common.util;

import lombok.extern.slf4j.Slf4j;
import ml.tianhong.rwh.common.api.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j  //把日志输出到文件
public class GlobalExceptionHandler {
    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO error(Exception e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return ResultVO.error().message(e.getMessage());
    }

    //自定义异常
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public ResultVO error(MyException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return ResultVO.error().code(e.getCode()).message(e.getMsg());
    }
}
