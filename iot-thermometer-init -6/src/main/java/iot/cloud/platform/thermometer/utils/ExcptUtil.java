package iot.cloud.platform.thermometer.utils;

import org.apache.commons.lang3.StringUtils;

public class ExcptUtil {

    private final static String PACKAGE="iot.cloud.platform.lock";

    public static String getKeyInfo(Throwable t,String packageName){
        String msg="";
        if(t!=null){
            msg+=t.getClass().getName()+" -> ";
        }
        msg+=t.getMessage()+"\n"
                + filterStack(t,packageName);
        return msg;
    }

    public static String filterStack(Throwable throwable, String packageName) {
        if (throwable==null) {
            return "";
        } else {
            StackTraceElement[] stackTraceElements = throwable.getStackTrace();
            String stackTraceElementString = stackTraceElements[0].toString()+"\n";
            if (StringUtils.isEmpty(packageName)) {
                return stackTraceElementString;
            } else {
                StackTraceElement[] eles = stackTraceElements;
                int length = Math.min(stackTraceElements.length,10);

                for(int i = 1; i < length; ++i) {
                    StackTraceElement stackTraceElement = eles[i];
                    stackTraceElementString += stackTraceElement.toString()+"\n";
                    if (stackTraceElement.toString().contains(packageName)) {
                        break;
                    }
                }

                return stackTraceElementString;
            }
        }
    }
    public static String filterStack(Throwable throwable) {
        if (throwable==null) {
            return "";
        } else {
            StackTraceElement[] stackTraceElements = throwable.getStackTrace();
            String stackTraceElementString = stackTraceElements[0].toString()+"\n";
            StackTraceElement[] eles = stackTraceElements;
            int length = Math.min(stackTraceElements.length,10);

            for(int i = 1; i < length; ++i) {
                StackTraceElement stackTraceElement = eles[i];
                stackTraceElementString += stackTraceElement.toString()+"\n";
                if (stackTraceElement.toString().contains(PACKAGE)) {
                    break;
                }
            }
            return stackTraceElementString;
        }
    }

    public static String filterStack(Throwable throwable,Class clazz) {
        if (throwable==null) {
            return "";
        } else {
            StackTraceElement[] stackTraceElements = throwable.getStackTrace();
            String stackTraceElementString = stackTraceElements[0].toString()+"\n";
            StackTraceElement[] eles = stackTraceElements;
            int length = Math.min(stackTraceElements.length,10);

            for(int i = 1; i < length; ++i) {
                StackTraceElement stackTraceElement = eles[i];
                stackTraceElementString += stackTraceElement.toString()+"\n";
                if (stackTraceElement.toString().contains(clazz.getPackage().getName())) {
                    break;
                }
            }
            return stackTraceElementString;
        }
    }
}
