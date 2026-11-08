package iot.cloud.platform.cloud.service.impl;

import iot.cloud.platform.cloud.entity.ConfigEntity;
import iot.cloud.platform.cloud.mapper.ConfigMapper;
import iot.cloud.platform.cloud.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public String getV(String k) {
        ConfigEntity config=configMapper.getConfigByK(k);
        if(config!=null){
            return config.getV();
        }else{
            return null;
        }
    }

    @Override
    public String getDeviceId() {
        return getV("iotId");
    }
}
