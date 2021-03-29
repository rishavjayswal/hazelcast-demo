package com.trivago.poc.hazelcastdemo.entryprocessor;

import com.hazelcast.map.EntryProcessor;
import com.trivago.poc.hazelcastdemo.model.PriceDetail;
import com.trivago.poc.hazelcastdemo.model.PriceKey;
import java.util.Map;

public class IncrementPriceEP implements EntryProcessor<PriceKey, PriceDetail, PriceDetail> {

  @Override
  public PriceDetail process(final Map.Entry<PriceKey, PriceDetail> entry) {
    final PriceDetail price = entry.getValue();
    price.setPriceValue(price.getPriceValue() + 1);
    entry.setValue(price);
    return price;
  }

}
