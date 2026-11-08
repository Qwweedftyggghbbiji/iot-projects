package iot.cloud.platform.cloud.config;

import iot.cloud.platform.cloud.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
   private static final List<String> EXCLUDE_PATH= new LinkedList<>();
   static{
      EXCLUDE_PATH.addAll(Arrays.asList("/static/**","/403.html"));//静态资源
      EXCLUDE_PATH.addAll(Arrays.asList("/","/logout","/signin"));//登录和注销
      EXCLUDE_PATH.addAll(Arrays.asList("/token","/device_all"));//开放API
      EXCLUDE_PATH.addAll(Arrays.asList("/swagger-resources/**","/v2/**","/swagger-ui.html","/webjars/**"));//swagger
   }

   private static final List<String> TOKEN_VALID_PATH= Arrays.asList("/device_all");

   @Autowired
   AuthInterceptor authInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATH);
   }
}