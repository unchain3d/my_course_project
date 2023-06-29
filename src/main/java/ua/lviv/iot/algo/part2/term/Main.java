package ua.lviv.iot.algo.part2.term;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan("ua.lviv.iot.algo.part2.term")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}