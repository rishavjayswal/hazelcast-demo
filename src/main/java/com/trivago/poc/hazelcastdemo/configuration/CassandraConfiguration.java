package com.trivago.poc.hazelcastdemo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {

  @Value("${spring.data.cassandra.contact-points:127.0.0.1}")
  private String contactPoints;

  @Value("${spring.data.cassandra.port:9042}")
  private int port;

  @Value("${spring.data.cassandra.keyspace:hazelcastdemo}")
  private String keySpace;

  @Override
  protected String getKeyspaceName() {
    return keySpace;
  }

  @Override
  protected String getContactPoints() {
    return contactPoints;
  }

  @Override
  protected int getPort() {
    return port;
  }


}
