package com.trivago.poc.hazelcastdemo.mapstore;

import static com.trivago.poc.hazelcastdemo.configuration.Constants.HOTELS_MAP_NAME;

import com.trivago.poc.hazelcastdemo.entity.HotelEntity;
import com.trivago.poc.hazelcastdemo.entity.PriceEntity;
import com.trivago.poc.hazelcastdemo.model.HotelDetail;
import com.trivago.poc.hazelcastdemo.model.PriceDetail;
import com.trivago.poc.hazelcastdemo.model.PriceKey;
import com.trivago.poc.hazelcastdemo.repository.HotelRepository;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HotelMS implements GenericMS<Integer, HotelDetail> {

  @Autowired
  private HotelRepository hotelRepository;

  @Override
  public String mapName() {
    return HOTELS_MAP_NAME;
  }

  @Override
  public void store(final Integer key, final HotelDetail value) {
    log.info("Saving into DB....");
    hotelRepository.save(getHotelEntity(value));
    log.info("Saved into DB!");

  }

  @Override
  public void storeAll(final Map<Integer, HotelDetail> map) {
    log.info("Saving into DB....");
    final List<HotelEntity> hotelEntities = map.values().stream().map(this::getHotelEntity)
        .collect(Collectors.toList());
    hotelRepository.saveAll(hotelEntities);
    log.info("Saved into DB!");
  }

  @Override
  public void delete(final Integer key) {
    log.info("Removing from DB....");
    hotelRepository.deleteById(key);
    log.info("Removed from DB!");
  }

  @Override
  public void deleteAll(final Collection<Integer> keys) {
    log.info("Removing all from DB....");
    keys.forEach(hotelRepository::deleteById);
    log.info("Removed all from DB!");
  }

  @Override
  public HotelDetail load(final Integer key) {
    log.info("Fetching from DB....");
    final Optional<HotelEntity> optional = hotelRepository.findById(key);
    log.info("Fetched from DB!");
    if (!optional.isPresent()) {
      return null;
    }
    final HotelDetail hotelDetail = getHotelDetail(hotelRepository.findById(key).get());
    return hotelDetail;
  }

  @Override
  public Map<Integer, HotelDetail> loadAll(final Collection<Integer> keys) {
    log.info("Fetching all from DB....");
    final Map<Integer, HotelDetail> hotelDetailsMap = new HashMap<>();
    keys.forEach(id -> {
      final Optional<HotelEntity> optional = hotelRepository.findById(id);
      if (optional.isEmpty()) {
        hotelDetailsMap.put(id, null);
      }
      hotelDetailsMap.put(id, getHotelDetail(optional.get()));
    });
    log.info("Fetched all from DB!");
    return hotelDetailsMap;
  }

  @Override
  public Iterable<Integer> loadAllKeys() {
    return hotelRepository.findAll().stream().map(HotelEntity::getHotelId)
        .collect(Collectors.toList());
  }

  // Mapping methods

  private PriceEntity getPriceEntity(final PriceDetail priceDetail) {
    return new PriceEntity(priceDetail.getKey().getHotelId(), priceDetail.getKey().getPriceId(),
        priceDetail.getPriceValue(), priceDetail.isBlocked());
  }

  private HotelEntity getHotelEntity(final HotelDetail hotelDetail) {
    final Set<PriceEntity> priceEntities = hotelDetail.getPrices().stream()
        .map(this::getPriceEntity).collect(
            Collectors.toSet());
    return new HotelEntity(hotelDetail.getHotelId(), hotelDetail.getHotelName(), priceEntities,
        hotelDetail.getLatitude(), hotelDetail.getLongitude());
  }

  private PriceDetail getPriceDetail(final PriceEntity priceEntity) {
    return new PriceDetail(
        new PriceKey(priceEntity.getHotelId(), priceEntity.getPriceId()),
        priceEntity.getPriceValue(), priceEntity.isBlocked());
  }

  private HotelDetail getHotelDetail(final HotelEntity hotelEntity) {
    final Set<PriceDetail> priceDetails = hotelEntity.getPrices().stream()
        .map(this::getPriceDetail).collect(
            Collectors.toSet());
    return new HotelDetail(hotelEntity.getHotelId(), hotelEntity.getHotelName(), priceDetails,
        hotelEntity.getLatitude(), hotelEntity.getLongitude());
  }


}
