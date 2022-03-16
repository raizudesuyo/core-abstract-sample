package com.example.demo.controller;

import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.ValidationException;

import com.example.demo.dto.in.ShoeStockUpdates;
import com.example.demo.dto.out.Stock;
import com.example.demo.facade.StockFacade;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api(value = "Stock", description = "Manipulate stock, or get store stocks")
@Controller(value = "Stocks")
@RequestMapping(path = "/stock")
@RequiredArgsConstructor
public class StockController {

  private final StockFacade stockFacade;

  @GetMapping(path = "{shopId}")
  public ResponseEntity<Stock> all(
    @PathVariable Integer shopId, 
    @RequestHeader Integer version
  ) throws Exception {
    try {
      return ResponseEntity.ok(stockFacade.get(version).getStock(shopId));
    } 
    catch(NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping(path = "{shopId}")
  public ResponseEntity<?> updateShopStock(
    @PathVariable Integer shopId,
    @Valid @RequestBody ShoeStockUpdates stockUpdates, 
    @RequestHeader Integer version
  ) throws Exception {
    try {
      stockFacade.get(version).UpdateStock(shopId, stockUpdates);  
    } catch (ValidationException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

    
    return ResponseEntity.ok().build();
  } 
}
