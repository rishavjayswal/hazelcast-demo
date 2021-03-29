package com.trivago.poc.hazelcastdemo.task;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.map.IMap;
import com.trivago.poc.hazelcastdemo.configuration.Constants;
import com.trivago.poc.hazelcastdemo.model.PriceDetail;
import com.trivago.poc.hazelcastdemo.model.PriceKey;
import java.io.Serializable;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AveragePriceTask
    implements Callable<Double>, Serializable, HazelcastInstanceAware {

  private transient HazelcastInstance hazelcastInstance;

  @Override
  public void setHazelcastInstance(final HazelcastInstance hazelcastInstance) {
    this.hazelcastInstance = hazelcastInstance;
  }

  @Override
  public Double call() throws Exception {
    final IMap<PriceKey, PriceDetail> map = hazelcastInstance.getMap(Constants.PRICES_MAP_NAME);
    int sum = 0;
    final int size = map.localKeySet().size();
    for (final PriceKey key : map.localKeySet()) {
      log.info("Calculating for key: {}", key);
      sum += map.get(key).getPriceValue();
    }
    final double result = size != 0 ? sum / size : 0;
    log.info("Local Result: {}", result);
    return result;
  }
}
