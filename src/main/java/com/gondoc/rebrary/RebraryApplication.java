package com.gondoc.rebrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.gondoc.rebrary.domain.member"})
@EnableJpaRepositories(basePackages = {"com.gondoc.rebrary.domain.member.repository"})
@EnableJpaAuditing
@SpringBootApplication
public class RebraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebraryApplication.class, args);
    }

}
