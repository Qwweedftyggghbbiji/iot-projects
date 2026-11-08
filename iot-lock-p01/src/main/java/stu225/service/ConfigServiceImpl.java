package stu225.service;

import stu225.entity.ConfigEntity;
import iot.cloud.platform.lock.mapper.ConfigMapper;
import stu225.service.impl.ConfigService;
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



