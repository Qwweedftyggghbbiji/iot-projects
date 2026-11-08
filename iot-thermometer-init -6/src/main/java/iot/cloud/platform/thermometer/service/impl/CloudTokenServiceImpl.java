package iot.cloud.platform.thermometer.service.impl;

import iot.cloud.platform.thermometer.service.CloudTokenService;
import iot.cloud.platform.thermometer.service.HttpService;
import iot.cloud.platform.thermometer.vo.ResMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudTokenServiceImpl implements CloudTokenService {

    @Value("${cloud.platform.user-id}")
    private String userId;

    @Value("${cloud.platform.secret}")
    private String secret;

    private HttpService httpService = HttpService.RETROFIT.create(HttpService.class);

    // 内存缓存令牌，全局复用
    private String cacheToken;

    @Override
    public String getCloudToken() throws RuntimeException {
        // 缓存存在直接返回，减少网络请求
        if (StringUtils.isNotEmpty(cacheToken)) {
            return cacheToken;
        }
        // 调用云平台接口获取令牌
        Call<ResMsg> call = httpService.getUserToken(userId, secret);
        try {
            Response<ResMsg> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                ResMsg resMsg = response.body();
                // 判断接口返回状态码
                if (!"0".equals(resMsg.getErrcode())) {
                    throw new RuntimeException("获取令牌失败：" + resMsg.getErrmsg());
                }
                // 取出data内部的token字符串
                Map<String, Object> tokenObj = (Map<String, Object>) resMsg.getData();
                cacheToken = tokenObj.get("token").toString();
                return cacheToken;
            }
            throw new RuntimeException("获取令牌失败：接口响应异常");
        } catch (IOException e) {
            throw new RuntimeException("获取令牌失败：网络请求异常", e);
        }
    }
}