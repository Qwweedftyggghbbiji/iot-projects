package iot.cloud.device.controller;

import iot.cloud.device.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TokenService tokenService;

    @GetMapping("/get_cloud_token")
    public Map<String, Object> testGetToken() {
        Map<String, Object> result = new HashMap<>();
        try {
            String token = tokenService.getCloudToken();
            result.put("code", 200);
            result.put("msg", "令牌获取成功");
            result.put("token", token);
            logger.info("Token获取成功: {}...", token != null && token.length() > 10 ? token.substring(0, 10) : token);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "令牌获取失败：" + e.getMessage());
            logger.error("Token获取失败: {}", e.getMessage(), e);
        }
        return result;
    }

    @GetMapping("/refresh_token")
    public Map<String, Object> refreshToken() {
        Map<String, Object> result = new HashMap<>();
        try {
            String token = tokenService.refreshToken();
            result.put("code", 200);
            result.put("msg", "令牌刷新成功");
            result.put("token", token);
            logger.info("Token刷新成功: {}...", token != null && token.length() > 10 ? token.substring(0, 10) : token);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "令牌刷新失败：" + e.getMessage());
            logger.error("Token刷新失败: {}", e.getMessage(), e);
        }
        return result;
    }

    @GetMapping("/clear_token")
    public Map<String, Object> clearToken() {
        Map<String, Object> result = new HashMap<>();
        try {
            tokenService.clearToken();
            result.put("code", 200);
            result.put("msg", "Token已清除，下次请求将重新获取");
            logger.info("Token已清除");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "Token清除失败：" + e.getMessage());
            logger.error("Token清除失败: {}", e.getMessage(), e);
        }
        return result;
    }
}
