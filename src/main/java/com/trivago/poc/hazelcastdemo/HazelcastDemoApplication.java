package com.trivago.poc.hazelcastdemo;

import com.trivago.poc.hazelcastdemo.configuration.HazelcastConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    HazelcastConfigurationProperties.class
})
public class HazelcastDemoApplication {

  public static void main(final String[] args) {
    SpringApplication.run(HazelcastDemoApplication.class, args);
  }


}
