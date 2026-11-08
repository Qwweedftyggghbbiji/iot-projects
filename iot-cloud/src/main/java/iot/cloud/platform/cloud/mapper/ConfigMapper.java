package iot.cloud.platform.cloud.mapper;

import iot.cloud.platform.cloud.entity.ConfigEntity;

public interface ConfigMapper {
    ConfigEntity getConfigByK(String k);
}
