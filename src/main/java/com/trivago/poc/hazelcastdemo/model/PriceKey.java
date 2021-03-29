package com.trivago.poc.hazelcastdemo.model;

import com.hazelcast.partition.PartitionAware;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceKey implements Serializable, PartitionAware {

  private int hotelId;

  private int priceId;

  @Override
  public Object getPartitionKey() {
    return hotelId;
  }
}
