package iot.cloud.platform.cloud.controller;

import iot.cloud.platform.cloud.service.DeviceCloudTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private DeviceCloudTokenService tokenService;

    /**
     * 设备端获取云平台Token接口
     * GET /test/get_cloud_token
     *
     * 功能：
     * 1. 首次请求或Token过期时，从云平台获取新Token
     * 2. 返回缓存的Token（如果有效）
     * 3. 重启后首次请求会重新获取Token
     */
    @GetMapping(value = "/get_cloud_token", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getCloudToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("收到设备端Token请求");

        try {
            // 获取Token
            String token = tokenService.getCloudToken();

            if (token != null && !token.isEmpty()) {
                // 构建成功响应
                Map<String, Object> result = new HashMap<>();
                result.put("errcode", "0");
                result.put("errmsg", "success");
                result.put("token", token);

                // 直接写入响应，不跳转
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(toJsonString(result));
                response.getWriter().flush();

                logger.info("Token响应成功: {}...", token.substring(0, Math.min(10, token.length())));
            } else {
                // Token获取失败
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "获取Token失败");
                logger.error("Token获取失败");
            }
        } catch (Exception e) {
            logger.error("处理Token请求异常: {}", e.getMessage(), e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器内部错误");
        }
    }

    /**
     * 强制刷新Token
     * GET /test/refresh_token
     */
    @GetMapping(value = "/refresh_token", produces = MediaType.APPLICATION_JSON_VALUE)
    public void refreshToken(HttpServletResponse response) throws IOException {
        logger.info("收到强制刷新Token请求");

        try {
            String token = tokenService.refreshToken();

            if (token != null && !token.isEmpty()) {
                Map<String, Object> result = new HashMap<>();
                result.put("errcode", "0");
                result.put("errmsg", "success");
                result.put("token", token);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(toJsonString(result));
                response.getWriter().flush();

                logger.info("Token刷新成功");
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "刷新Token失败");
            }
        } catch (Exception e) {
            logger.error("刷新Token异常: {}", e.getMessage(), e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器内部错误");
        }
    }

    /**
     * 清除缓存的Token（用于测试重启场景）
     * GET /test/clear_token
     */
    @GetMapping(value = "/clear_token", produces = MediaType.APPLICATION_JSON_VALUE)
    public void clearToken(HttpServletResponse response) throws IOException {
        logger.info("收到清除Token请求");

        try {
            tokenService.clearToken();

            Map<String, Object> result = new HashMap<>();
            result.put("errcode", "0");
            result.put("errmsg", "Token已清除，下次请求将重新获取");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(toJsonString(result));
            response.getWriter().flush();
        } catch (Exception e) {
            logger.error("清除Token异常: {}", e.getMessage(), e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器内部错误");
        }
    }

    /**
     * 发送错误响应
     */
    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);

        Map<String, Object> result = new HashMap<>();
        result.put("errcode", String.valueOf(status));
        result.put("errmsg", message);

        response.getWriter().write(toJsonString(result));
        response.getWriter().flush();
    }

    /**
     * 简单的JSON转换（避免引入额外依赖）
     */
    private String toJsonString(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            sb.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof Number) {
                sb.append(value);
            } else {
                sb.append("\"").append(escapeJsonString(String.valueOf(value))).append("\"");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * JSON字符串转义
     */
    private String escapeJsonString(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
