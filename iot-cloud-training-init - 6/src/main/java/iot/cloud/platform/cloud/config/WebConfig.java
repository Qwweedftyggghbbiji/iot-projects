package iot.cloud.platform.cloud.config;

import iot.cloud.platform.cloud.interceptor.AuthInterceptor;
import iot.cloud.platform.cloud.interceptor.DeviceInterceptor;
import iot.cloud.platform.cloud.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
   private static final List<String> TOKEN_VALID_PATH= Arrays.asList("/device_all","/device_register","/device_get");
   private static final List<String> DEVICE_VALID_PATH= Arrays.asList("/devicemsg_add","/devicemsg_get");
   private static final List<String> EXCLUDE_PATH= new LinkedList<>();

   static{
      EXCLUDE_PATH.addAll(Arrays.asList("/static/**","/403.html"));//静态资源
      EXCLUDE_PATH.addAll(Arrays.asList("/","/login","/logout","/signin"));//登录和注销
      EXCLUDE_PATH.addAll(Arrays.asList("/token"));//开放API
      EXCLUDE_PATH.addAll(TOKEN_VALID_PATH);//设备开放API
      EXCLUDE_PATH.addAll(DEVICE_VALID_PATH);//设备开放API
      EXCLUDE_PATH.addAll(Arrays.asList("/swagger-resources/**","/v2/**","/swagger-ui.html","/webjars/**"));//swagger
      EXCLUDE_PATH.addAll(Arrays.asList("/test/**"));//设备端测试接口
   }

   @Autowired
   AuthInterceptor authInterceptor;

   @Autowired
   TokenInterceptor tokenInterceptor;

   @Autowired
   DeviceInterceptor deviceInterceptor;

   @Override
   public void configurePathMatch(PathMatchConfigurer configurer) {
      UrlPathHelper urlPathHelper = new UrlPathHelper();
      // 不移除;后面的内容
      urlPathHelper.setRemoveSemicolonContent(false);
      configurer.setUrlPathHelper(urlPathHelper);
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATH);
      registry.addInterceptor(tokenInterceptor).addPathPatterns(TOKEN_VALID_PATH);
      registry.addInterceptor(deviceInterceptor).addPathPatterns(DEVICE_VALID_PATH);
   }
}