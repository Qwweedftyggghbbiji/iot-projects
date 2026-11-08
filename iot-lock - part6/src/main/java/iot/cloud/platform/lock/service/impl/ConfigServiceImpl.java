package iot.cloud.platform.lock.service.impl;

import iot.cloud.platform.lock.entity.ConfigEntity;
import iot.cloud.platform.lock.mapper.ConfigMapper;
import iot.cloud.platform.lock.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public String getV(String k) {
        ConfigEntity config = configMapper.getConfigByK(k);
        return config != null ? config.getV() : null;
    }

    @Override
    public String getDeviceId() {
        return getV("iotId");
    }
}