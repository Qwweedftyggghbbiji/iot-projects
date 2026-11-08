package iot.cloud.platform.cloud.service;

import iot.cloud.platform.cloud.entity.TokenEntity;

public interface TokenService {
  /**
   * 如果是有效的Token 返回true
   * @param token
   * @return
   */
  boolean isValidToken(String token);

  /**
   * 根据用户ID 从数据库获取Token
   * @param userId
   * @return
   */
  TokenEntity getTokenByUserId(String userId);

  /**
   * 生成一个Token，保存到数据库，并返回
   * @param userId
   * @return
   */
  TokenEntity generateToken(String userId);

  /**
   * 根据Token 获取所有 Token 信息
   * @param token
   * @return
   */
  TokenEntity getToken(String token);
}
