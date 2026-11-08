package iot.cloud.platform.lock.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date parseJsonDateTime(String dtText){
        if(StringUtils.isBlank(dtText)){
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return format.parse(dtText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
