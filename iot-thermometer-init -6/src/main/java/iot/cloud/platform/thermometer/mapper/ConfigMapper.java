package iot.cloud.platform.thermometer.mapper;

import iot.cloud.platform.thermometer.entity.ConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ConfigMapper {
  /**
   * 根据 k 获取所有配置。
   * @param k
   * @return
   */
  ConfigEntity getConfigByK(String k);

  int saveConfigs(@Param("kv") Map<String,String> map);
}
