package iot.cloud.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IotDeviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotDeviceApplication.class, args);
        System.out.println("==============================================");
        System.out.println("设备端应用启动成功！");
        System.out.println("访问地址: http://localhost:8080/test/get_cloud_token");
        System.out.println("==============================================");
    }
}
