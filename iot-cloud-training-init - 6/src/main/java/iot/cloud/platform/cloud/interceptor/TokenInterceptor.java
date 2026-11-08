package iot.cloud.platform.cloud.interceptor;

import iot.cloud.platform.cloud.constants.Const;
import iot.cloud.platform.cloud.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class TokenInterceptor implements HandlerInterceptor {

  @Autowired
  private TokenService tokenService;

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler) throws Exception {
    boolean result=false;
    String token=request.getParameter("token");
    if (token != null&& tokenService.isValidToken(token)) {
      result = true;
    }else{
        response.setContentType(Const.CONTENT_TYPE_JSON+";charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"errcode\":403,\"errmsg\":\"Token无效\"}");
    }
    return result;
  }
}
