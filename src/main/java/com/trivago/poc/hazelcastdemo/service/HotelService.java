package com.trivago.poc.hazelcastdemo.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.trivago.poc.hazelcastdemo.configuration.Constants;
import com.trivago.poc.hazelcastdemo.listener.HotelsKafkaEntryListener;
import com.trivago.poc.hazelcastdemo.model.HotelDetail;
import com.trivago.poc.hazelcastdemo.model.PriceDetail;
import com.trivago.poc.hazelcastdemo.model.PriceKey;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

  @Autowired
  private HazelcastInstance hazelcastInstance;

  @Autowired
  private HotelsKafkaEntryListener hotelsKafkaEntryListener;

  @PostConstruct
  private void init() {
    getHotelHzMap().addEntryListener(hotelsKafkaEntryListener, true);
  }

  private IMap<Integer, HotelDetail> getHotelHzMap() {
    return hazelcastInstance.getMap(Constants.HOTELS_MAP_NAME);
  }

  private IMap<PriceKey, PriceDetail> getPriceHzMap() {
    return hazelcastInstance.getMap(Constants.PRICES_MAP_NAME);
  }

  public HotelDetail getHotel(final int id) {
    return getHotelHzMap().get(id);
  }

  public HotelDetail saveHotel(final HotelDetail hotel) {
    getHotelHzMap().put(hotel.getHotelId(), hotel);
    savePrices(hotel);
    return hotel;
  }

  private void savePrices(final HotelDetail hotel) {
    hotel.getPrices().forEach(price -> {
      getPriceHzMap().put(price.getKey(), price);
    });
  }


}
