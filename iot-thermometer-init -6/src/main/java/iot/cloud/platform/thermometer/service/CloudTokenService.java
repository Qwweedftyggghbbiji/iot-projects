package iot.cloud.platform.thermometer.service;

public interface CloudTokenService {
    /**
     * 调用云平台API获取访问令牌，内存缓存避免重复请求
     * @return 令牌字符串
     */
    String getCloudToken();
}