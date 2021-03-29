package com.trivago.poc.hazelcastdemo.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import com.trivago.poc.hazelcastdemo.configuration.Constants;
import com.trivago.poc.hazelcastdemo.entryprocessor.IncrementPriceEP;
import com.trivago.poc.hazelcastdemo.model.HotelDetail;
import com.trivago.poc.hazelcastdemo.model.PriceDetail;
import com.trivago.poc.hazelcastdemo.model.PriceKey;
import com.trivago.poc.hazelcastdemo.task.AveragePriceTask;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  @Autowired
  private HazelcastInstance hazelcastInstance;

  private IMap<PriceKey, PriceDetail> getPricesHzMap() {
    return hazelcastInstance.getMap(Constants.PRICES_MAP_NAME);
  }

  private IMap<Integer, HotelDetail> getHotelHzMap() {
    return hazelcastInstance.getMap(Constants.HOTELS_MAP_NAME);
  }

  public PriceDetail getPrice(final PriceKey key) {
    return getPricesHzMap().get(key);
  }

  public PriceDetail savePrice(final PriceKey key, final PriceDetail price) {
    getPricesHzMap().put(key, price);
    saveHotel(key, price);
    return price;
  }

  public Double getAveragePrice() throws ExecutionException, InterruptedException {
    final IExecutorService executorService = hazelcastInstance.getExecutorService("averagePrice");
    final Future<Double> future = executorService.submit(new AveragePriceTask());
    return future.get();
  }

  public Map<PriceKey, PriceDetail> incrementPrices() {
    final Map<PriceKey, PriceDetail> priceDetailMap = getPricesHzMap()
        .executeOnEntries(new IncrementPriceEP());
    priceDetailMap.values().forEach(price -> {
      saveHotel(price.getKey(), price);
    });
    return priceDetailMap;
  }

  private void saveHotel(final PriceKey key, final PriceDetail price) {
    final HotelDetail hotelDetail = getHotelHzMap().get(key.getHotelId());
    hotelDetail.getPrices().remove(price);
    hotelDetail.getPrices().add(price);
    getHotelHzMap().put(hotelDetail.getHotelId(), hotelDetail);
  }
}
