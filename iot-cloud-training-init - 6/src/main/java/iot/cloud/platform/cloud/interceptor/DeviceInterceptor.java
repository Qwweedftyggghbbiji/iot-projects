package iot.cloud.platform.cloud.interceptor;

import iot.cloud.platform.cloud.constants.Const;
import iot.cloud.platform.cloud.service.DeviceService;
import iot.cloud.platform.cloud.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class DeviceInterceptor implements HandlerInterceptor {

  @Autowired
  private DeviceService deviceService;

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler) throws Exception {
    boolean result=false;
    String iotId=request.getParameter("iotId");
    String secret=request.getParameter("secret");
    if (StringUtils.isNotBlank(iotId)&& StringUtils.isNotBlank(secret)&& deviceService.validSecret(iotId,secret)) {
      result = true;
    }else{
        response.setContentType(Const.CONTENT_TYPE_JSON+";charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"errcode\":403,\"errmsg\":\"设备密钥无效\"}");
    }
    return result;
  }
}
