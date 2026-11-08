package iot.cloud.platform.lock.mapper;

import iot.cloud.platform.lock.entity.ConfigEntity;
import org.apache.ibatis.annotations.Param;

public interface ConfigMapper {
    // 根据键名查询配置
    ConfigEntity getConfigByK(@Param("k") String k);
}