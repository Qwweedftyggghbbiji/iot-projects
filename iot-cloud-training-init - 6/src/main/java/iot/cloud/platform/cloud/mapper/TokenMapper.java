package iot.cloud.platform.cloud.mapper;

import iot.cloud.platform.cloud.entity.TokenEntity;

public interface TokenMapper {
  /**
   * 根据 Token 获取 Token信息
   * @param token
   * @return
   */
  TokenEntity getToken(String token);

  /**
   * 根据用户ID获取 Token信息
   * @param userId
   * @return
   */
  TokenEntity getTokenByUserId(String userId);

  /**
   * 保存 Token
   * @param token
   * @return
   */
  boolean saveToken(TokenEntity token);

  /**
   * 更新用户 Token
   * @param token
   * @return
   */
  boolean updateToken(TokenEntity token);
}
