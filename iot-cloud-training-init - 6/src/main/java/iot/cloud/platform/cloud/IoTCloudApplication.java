package iot.cloud.platform.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("iot.cloud.platform.cloud.mapper")
public class IoTCloudApplication {

  public static void main(String[] args) {
    SpringApplication.run(IoTCloudApplication.class, args);
  }

}
