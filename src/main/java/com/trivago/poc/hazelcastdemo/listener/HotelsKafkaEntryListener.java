package com.trivago.poc.hazelcastdemo.listener;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.map.MapEvent;
import com.trivago.poc.hazelcastdemo.model.HotelDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HotelsKafkaEntryListener implements EntryListener<Integer, HotelDetail> {

  @Autowired
  private KafkaTemplate<Integer, HotelDetail> kafkaTemplate;

  @Override
  public void entryAdded(final EntryEvent<Integer, HotelDetail> event) {
    log.info("Entry {}: Key: {} Val: {}", event.getEventType(), event.getKey(), event.getValue());
    kafkaTemplate.send("hotels", event.getKey(), event.getValue());
  }

  @Override
  public void entryEvicted(final EntryEvent<Integer, HotelDetail> event) {
    log.info("Entry {}: Key: {} Val: {}", event.getEventType(), event.getKey(), event.getValue());
    kafkaTemplate.send("hotels", event.getKey(), event.getValue());
  }

  @Override
  public void entryExpired(final EntryEvent<Integer, HotelDetail> event) {
    log.info("Entry {}: Key: {} Val: {}", event.getEventType(), event.getKey(), event.getValue());
    kafkaTemplate.send("hotels", event.getKey(), event.getValue());
  }

  @Override
  public void entryRemoved(final EntryEvent<Integer, HotelDetail> event) {
    log.info("Entry {}: Key: {} Val: {}", event.getEventType(), event.getKey(), event.getValue());
    kafkaTemplate.send("hotels", event.getKey(), event.getValue());
  }

  @Override
  public void entryUpdated(final EntryEvent<Integer, HotelDetail> event) {
    log.info("Entry {}: Key: {} Val: {}", event.getEventType(), event.getKey(), event.getValue());
    kafkaTemplate.send("hotels", event.getKey(), event.getValue());
  }

  @Override
  public void mapCleared(final MapEvent event) {
    log.info("Map {}", event.getEventType());
  }

  @Override
  public void mapEvicted(final MapEvent event) {
    log.info("Map {}", event.getEventType());
  }

}
