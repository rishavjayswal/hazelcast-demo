package com.trivago.poc.hazelcastdemo.entity;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("hotels")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelEntity {

  @Id
  @PrimaryKeyColumn(value = "hotelId", type = PrimaryKeyType.PARTITIONED)
  private int hotelId;

  @Column
  private String hotelName;

  @Column
  private Set<PriceEntity> prices;

  @Column
  private int latitude;

  @Column
  private int longitude;
}
