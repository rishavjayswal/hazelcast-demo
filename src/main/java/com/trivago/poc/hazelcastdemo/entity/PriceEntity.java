package com.trivago.poc.hazelcastdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UserDefinedType("price")
public class PriceEntity {

  private int hotelId;

  private int priceId;

  private int priceValue;

  private boolean blocked;

}
