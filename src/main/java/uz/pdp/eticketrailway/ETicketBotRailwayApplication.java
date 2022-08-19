package uz.pdp.eticketrailway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ETicketBotRailwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ETicketBotRailwayApplication.class, args);
    }

}
