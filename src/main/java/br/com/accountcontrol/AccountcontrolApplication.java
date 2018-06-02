package br.com.accountcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AccountcontrolApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountcontrolApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bcyrpt() {
        return new BCryptPasswordEncoder();
    }
}
