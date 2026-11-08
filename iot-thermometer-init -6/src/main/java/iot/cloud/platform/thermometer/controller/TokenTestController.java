package iot.cloud.platform.thermometer.controller;

import iot.cloud.platform.thermometer.service.CloudTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenTestController {

    @Resource
    private CloudTokenService cloudTokenService;

    /**
     * 测试接口：获取云平台用户令牌
     */
    @GetMapping("/test/get_cloud_token")
    public Map<String, Object> testGetToken() {
        Map<String, Object> result = new HashMap<>();
        try {
            String token = cloudTokenService.getCloudToken();
            result.put("code", 200);
            result.put("msg", "令牌获取成功");
            result.put("token", token);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "令牌获取失败：" + e.getMessage());
        }
        return result;
    }
}