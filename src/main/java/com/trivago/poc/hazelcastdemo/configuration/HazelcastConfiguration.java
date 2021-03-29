package com.trivago.poc.hazelcastdemo.configuration;

import com.hazelcast.config.ClasspathYamlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.context.SpringManagedContext;
import com.trivago.poc.hazelcastdemo.mapstore.GenericMS;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

  private static final String MAP_NAME = "map_name";

  @Autowired
  private HazelcastConfigurationProperties properties;

  @Bean
  public HazelcastInstance hazelcastInstance(final ApplicationContext applicationContext,
      final Collection<GenericMS> mapsStores) throws FileNotFoundException {
    return createHazelcastInstance(applicationContext, mapsStores);
  }

  private HazelcastInstance createHazelcastInstance(final ApplicationContext applicationContext,
      final Collection<GenericMS> mapsStores) throws FileNotFoundException {

    final Config cfg = new ClasspathYamlConfig("hazelcast.yml");
    cfg.setNetworkConfig(getNetworkConfig());
    //cfg.setManagementCenterConfig(getManagementCenterConfig());

    configureMapStores(cfg, mapsStores);

    final SpringManagedContext springManagedContext = new SpringManagedContext();
    springManagedContext.setApplicationContext(applicationContext);
    cfg.setManagedContext(springManagedContext);
    cfg.addListenerConfig(
        new ListenerConfig("com.trivago.poc.hazelcastdemo.listener.ClusterMembershipListener"));
    return Hazelcast.newHazelcastInstance(cfg);

  }

  private NetworkConfig getNetworkConfig() {
    final MulticastConfig multicastConfig = new MulticastConfig();
    multicastConfig.setEnabled(true);
    final JoinConfig joinConfig = new JoinConfig();
    joinConfig.setMulticastConfig(multicastConfig);
    final NetworkConfig networkConfig = new NetworkConfig();
    networkConfig.setJoin(joinConfig);
    return networkConfig;
  }

  private ManagementCenterConfig getManagementCenterConfig() {
    final ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
    managementCenterConfig
        .setTrustedInterfaces(Set.of("192.168.11.32"));
    return managementCenterConfig;
  }

  private void configureMapStores(final Config config, final Collection<GenericMS> mapStores) {
    mapStores.stream()
        .map(mapStore -> new MapStoreConfig().setImplementation(mapStore)
            .setWriteDelaySeconds(properties.getWriteDelay())
            .setWriteBatchSize(properties.getWriteBatchSize())
            .setProperty(MAP_NAME, mapStore.mapName()))
        .forEach(mapStoreConfig -> config
            .getMapConfig(mapStoreConfig.getProperty(MAP_NAME))
            .setBackupCount(properties.getSyncBackupCount())
            .setAsyncBackupCount(properties.getAsyncBackupCount())
            .setMaxIdleSeconds(properties.getMaxIdleSeconds())
            .setNearCacheConfig(getNearCacheConfig())
            .setMapStoreConfig(mapStoreConfig));
  }

  private NearCacheConfig getNearCacheConfig() {
    final EvictionConfig evictionConfig = new EvictionConfig()
        .setEvictionPolicy(EvictionPolicy.LRU)
        .setMaxSizePolicy(MaxSizePolicy.ENTRY_COUNT)
        .setSize(5000);

    return new NearCacheConfig()
        .setInMemoryFormat(InMemoryFormat.OBJECT)
        .setInvalidateOnChange(true)
        .setTimeToLiveSeconds(600)
        .setEvictionConfig(evictionConfig);
  }
}
