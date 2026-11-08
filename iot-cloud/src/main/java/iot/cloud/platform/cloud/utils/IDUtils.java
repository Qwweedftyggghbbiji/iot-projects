package iot.cloud.platform.cloud.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

public class IDUtils {

    public static String genUniqueId(){
        return MsgDigestUtils.encodeSHA256(RandomStringUtils.randomAlphanumeric(16)+new Date().getTime());
    }

}
