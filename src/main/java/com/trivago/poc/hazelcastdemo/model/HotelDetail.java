package com.trivago.poc.hazelcastdemo.model;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelDetail implements Serializable {

  private int hotelId;

  private String hotelName;

  private Set<PriceDetail> prices;

  private int latitude;

  private int longitude;

}

