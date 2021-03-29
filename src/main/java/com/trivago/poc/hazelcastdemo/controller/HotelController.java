package com.trivago.poc.hazelcastdemo.controller;

import com.trivago.poc.hazelcastdemo.model.HotelDetail;
import com.trivago.poc.hazelcastdemo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel")
public class HotelController {

  @Autowired
  private HotelService hotelService;

  @GetMapping("/{id}")
  public HotelDetail getHotel(@PathVariable final int id) {
    return hotelService.getHotel(id);
  }

  @PostMapping
  public HotelDetail saveHotel(@RequestBody final HotelDetail hotel) {
    return hotelService.saveHotel(hotel);
  }
}
