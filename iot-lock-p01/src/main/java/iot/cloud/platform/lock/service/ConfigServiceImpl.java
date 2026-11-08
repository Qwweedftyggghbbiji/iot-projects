package iot.cloud.platform.lock.service;

import iot.cloud.platform.lock.mapper.ConfigMapper;
import iot.cloud.platform.lock.service.impl.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu225.entity.ConfigEntity;

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



