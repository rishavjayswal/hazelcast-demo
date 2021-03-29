package com.trivago.poc.hazelcastdemo.repository;

import com.trivago.poc.hazelcastdemo.entity.HotelEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends CassandraRepository<HotelEntity, Integer> {

}
