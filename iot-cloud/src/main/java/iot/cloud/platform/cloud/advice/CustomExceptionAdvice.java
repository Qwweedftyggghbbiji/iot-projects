package iot.cloud.platform.cloud.advice;

import iot.cloud.platform.cloud.vo.ResMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@ControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleValidateException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        // 这里用一个Map来模拟开发中的一个返回对象
        ResMsg resMsg=new ResMsg();
        HashMap data = new HashMap<>(16);
        bindingResult.getFieldErrors().stream().forEach(item -> {
            String message = item.getDefaultMessage();
            String field = item.getField();
            data.put(field, message);
        });

        resMsg.setErrcode("400");
        resMsg.setErrmsg(StringUtils.join(data.values(),";"));
        resMsg.setData(data);
        return resMsg;
    }
}