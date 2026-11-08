package iot.cloud.platform.thermometer;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("iot.cloud.platform.thermometer.mapper")
public class IoTThermometerApplication {
	public static void main(String[] args) {
		SpringApplication.run(IoTThermometerApplication.class, args);
		log.info("http://localhost:8100/");
	}
}
