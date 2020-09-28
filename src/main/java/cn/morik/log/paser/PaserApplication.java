package cn.morik.log.paser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaserApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaserApplication.class, args);
        new LogPaser().downlogs();
    }
}
