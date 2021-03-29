package com.trivago.poc.hazelcastdemo.controller;

import com.trivago.poc.hazelcastdemo.model.PriceDetail;
import com.trivago.poc.hazelcastdemo.model.PriceKey;
import com.trivago.poc.hazelcastdemo.service.PriceService;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel-prices")
public class PriceController {

  @Autowired
  private PriceService priceService;

  @PostMapping
  public PriceDetail getPrice(@RequestBody final PriceKey priceKey) {
    return priceService.getPrice(priceKey);
  }

  @PostMapping("/save")
  public PriceDetail savePrice(@RequestBody final PriceDetail priceDetail) {
    return priceService.savePrice(priceDetail.getKey(), priceDetail);
  }

  @GetMapping("/average")
  public Double calculateAveragePrice() throws ExecutionException, InterruptedException {
    return priceService.getAveragePrice();
  }

  @GetMapping("/increment")
  public Map<PriceKey, PriceDetail> incrementPrices() {
    return priceService.incrementPrices();
  }
}
