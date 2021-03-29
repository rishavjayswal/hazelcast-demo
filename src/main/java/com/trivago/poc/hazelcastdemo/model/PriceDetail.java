package com.trivago.poc.hazelcastdemo.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
public class PriceDetail implements Serializable {

  @EqualsAndHashCode.Include
  private PriceKey key;

  private int priceValue;

  private boolean blocked;

}
