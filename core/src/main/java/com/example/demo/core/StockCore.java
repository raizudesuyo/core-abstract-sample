package com.example.demo.core;

import com.example.demo.dto.in.ShoeStockUpdates;
import com.example.demo.dto.out.Stock;

public interface StockCore {

  Stock getStock(Integer shopId) throws Exception;
  void UpdateStock(Integer shopId, ShoeStockUpdates updates) throws Exception;

}
