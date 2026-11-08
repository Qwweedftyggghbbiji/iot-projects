package iot.cloud.platform.lock.controller;

import iot.cloud.platform.lock.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }

    @GetMapping("/api/deviceId")  // 路径必须完全一致
    @ResponseBody
    public String getDeviceId() {
        return configService.getDeviceId();
    }
}