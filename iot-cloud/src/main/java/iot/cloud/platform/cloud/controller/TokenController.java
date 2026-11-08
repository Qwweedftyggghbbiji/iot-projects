package iot.cloud.platform.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import iot.cloud.platform.cloud.entity.TokenEntity;
import iot.cloud.platform.cloud.service.TokenService;
import iot.cloud.platform.cloud.service.UserService;
import iot.cloud.platform.cloud.vo.ResMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Api("平台访问令牌接口")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @GetMapping("/token")
    @ResponseBody
    @ApiOperation("/获取用户访问令牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "用户ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "secret", value = "用户密钥", required = true, paramType = "query", dataType = "string")
    })
    public ResMsg token(@RequestParam("user_id") String userId, @RequestParam("secret") String secret) {
        ResMsg msg = new ResMsg();

        // 1. 基础非空校验：必须同时拥有 userId 和 secret
        if (userId != null && !userId.isEmpty() && secret != null && !secret.isEmpty()) {

            // 2. 校验 userId 和 secret 是否匹配
            // 假设 UserService 中有 checkSecret 方法返回 boolean
            if (userService.verifySecret(userId, secret)) {

                // 3. 根据 userId 获取现有的 TokenEntity
                TokenEntity token = tokenService.getTokenByUserId(userId);

                // 4. 判断逻辑：如果 token 为空 或者 token 已过期，则重新生成
                if (token == null || token.expired()) {
                    // 重新生成一个令牌
                    token = tokenService.generateToken(userId);
                }

                // 5. 返回成功结果
                msg.setErrcode("0");
                msg.setErrmsg("获取令牌成功");
                msg.setData(token);

            } else {
                // Secret 校验失败
                msg.setErrcode("1003");
                msg.setErrmsg("用户不存在或secret不匹配");
            }

        } else {
            // 参数缺失
            msg.setErrcode("1003");
            msg.setErrmsg("参数缺失，请检查 user_id 和 secret");
        }

        return msg;
    }
}