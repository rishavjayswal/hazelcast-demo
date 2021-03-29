package com.trivago.poc.hazelcastdemo.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "hazelcast")
@ConstructorBinding
@AllArgsConstructor
@Data
@Validated
public class HazelcastConfigurationProperties {

  private int writeDelay;
  private int writeBatchSize;
  private int asyncBackupCount;
  private int syncBackupCount;
  private int maxIdleSeconds;


}
