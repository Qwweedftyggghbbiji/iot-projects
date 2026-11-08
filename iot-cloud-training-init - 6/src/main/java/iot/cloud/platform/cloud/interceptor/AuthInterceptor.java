package iot.cloud.platform.cloud.interceptor;

import iot.cloud.platform.cloud.constants.Const;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler) throws Exception {
    boolean result=false;
    // 获取 session
      HttpSession session = request.getSession();
      Object obj = session.getAttribute(Const.SESSION_KEY_USER);
      if (obj != null) {
        result = true;
      }else{
        if(Const.CONTENT_TYPE_JSON.equals(request.getContentType())){
          response.setContentType(Const.CONTENT_TYPE_JSON+";charset=utf-8");
          response.setCharacterEncoding("utf-8");
          response.setContentType("application/json; charset=utf-8");
          PrintWriter writer = response.getWriter();
          writer.write("{\"errcode\":403,\"errmsg\":\"No Authentication\"}");
        }else{
          request.getRequestDispatcher("/403.html").forward(request,
                  response);
        }
      }
    return result;
  }

}
