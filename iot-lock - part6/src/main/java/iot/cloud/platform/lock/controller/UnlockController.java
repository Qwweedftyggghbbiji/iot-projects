package iot.cloud.platform.lock.controller;

import iot.cloud.platform.lock.service.PwdService;
import iot.cloud.platform.lock.vo.ResMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnlockController {

    @Autowired
    private PwdService pwdService;

    @PostMapping("/unlock")
    public ResMsg unlock(@RequestParam String pwd) {
        return pwdService.verifyPwd(pwd);
    }
}