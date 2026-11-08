package iot.cloud.platform.cloud;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@Slf4j
@SpringBootApplication
@MapperScan("iot.cloud.platform.cloud.mapper")
public class IoTCloudApplication {

  public static void main(String[] args) {
    SpringApplication.run(IoTCloudApplication.class, args);
    log.info("http://localhost:8098");
  }

}
