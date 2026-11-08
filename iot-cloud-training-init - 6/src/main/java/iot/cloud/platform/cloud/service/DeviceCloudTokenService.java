package iot.cloud.platform.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class DeviceCloudTokenService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceCloudTokenService.class);

    private static final String TOKEN_FILE = "device_token.txt";

    @Value("${cloud.platform.url:http://localhost:8098}")
    private String cloudPlatformUrl;

    @Value("${device.user.id:zhangsan}")
    private String userId;

    @Value("${device.user.secret:123456}")
    private String userSecret;

    private String cachedToken;
    private long cachedTokenExpireTs;
    private final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        loadTokenFromFile();
        isInitialized.set(true);
    }

    /**
     * 获取云平台Token
     * 首次调用或Token过期时从云平台获取
     */
    public String getCloudToken() {
        // 如果已有有效Token，直接返回
        if (cachedToken != null && !isTokenExpired()) {
            logger.info("返回缓存的Token: {}", cachedToken);
            return cachedToken;
        }

        // 从云平台获取新Token
        logger.info("从云平台获取新Token...");
        return fetchTokenFromCloud();
    }

    /**
     * 强制刷新Token
     */
    public String refreshToken() {
        logger.info("强制刷新Token...");
        return fetchTokenFromCloud();
    }

    /**
     * 从云平台获取Token
     */
    private String fetchTokenFromCloud() {
        try {
            String url = String.format("%s/token?user_id=%s&secret=%s",
                    cloudPlatformUrl, userId, userSecret);
            logger.info("请求云平台Token: {}", url);

            String response = restTemplate.getForObject(url, String.class);
            logger.info("云平台响应: {}", response);

            // 解析响应获取token
            // 响应格式: {"errcode":"0","errmsg":"...","data":{"userId":"zhangsan","token":"xxx",...}}
            String token = extractTokenFromResponse(response);

            if (token != null && !token.isEmpty()) {
                this.cachedToken = token;
                this.cachedTokenExpireTs = System.currentTimeMillis() + (8 * 60 * 60 * 1000L); // 8小时后过期
                saveTokenToFile(token);
                logger.info("Token获取成功并已缓存");
                return token;
            } else {
                logger.error("Token解析失败，响应: {}", response);
                // 如果解析失败但文件中有旧token，尝试使用
                String oldToken = loadTokenFromFile();
                if (oldToken != null) {
                    logger.warn("使用文件中保存的旧Token");
                    return oldToken;
                }
            }
        } catch (Exception e) {
            logger.error("获取Token失败: {}", e.getMessage(), e);
            // 出错时尝试使用缓存的Token
            if (cachedToken != null) {
                logger.warn("使用缓存的旧Token");
                return cachedToken;
            }
        }
        return null;
    }

    /**
     * 从JSON响应中提取token
     */
    private String extractTokenFromResponse(String response) {
        if (response == null) return null;
        try {
            // 简单解析: 查找 "token":"xxx" 模式
            int tokenIndex = response.indexOf("\"token\":\"");
            if (tokenIndex >= 0) {
                int start = tokenIndex + 9; // 跳过 "\"token\":\""
                int end = response.indexOf("\"", start);
                if (end > start) {
                    return response.substring(start, end);
                }
            }
        } catch (Exception e) {
            logger.error("解析Token失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 检查Token是否过期
     */
    private boolean isTokenExpired() {
        // 如果没有过期时间设置，认为需要刷新
        if (cachedTokenExpireTs == 0) {
            return true;
        }
        // 提前1分钟判断过期，避免临界情况
        return System.currentTimeMillis() >= (cachedTokenExpireTs - 60000);
    }

    /**
     * 从文件加载Token
     */
    private String loadTokenFromFile() {
        File file = new File(TOKEN_FILE);
        if (!file.exists()) {
            logger.info("Token文件不存在，需要从云平台获取");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String token = reader.readLine();
            if (token != null && !token.trim().isEmpty()) {
                this.cachedToken = token.trim();
                logger.info("从文件加载Token成功: {}", token.substring(0, Math.min(10, token.length())) + "...");
                return token.trim();
            }
        } catch (IOException e) {
            logger.error("读取Token文件失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 保存Token到文件
     */
    private void saveTokenToFile(String token) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TOKEN_FILE))) {
            writer.write(token);
            logger.info("Token已保存到文件");
        } catch (IOException e) {
            logger.error("保存Token文件失败: {}", e.getMessage());
        }
    }

    /**
     * 清除Token（设备重启后会重新获取）
     */
    public void clearToken() {
        this.cachedToken = null;
        this.cachedTokenExpireTs = 0;
        try {
            Files.deleteIfExists(Paths.get(TOKEN_FILE));
            logger.info("Token已清除");
        } catch (IOException e) {
            logger.error("删除Token文件失败: {}", e.getMessage());
        }
    }
}
