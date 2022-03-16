package com.example.demo.core;

import com.example.demo.dto.in.ShoeStockUpdate;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.ValidationException;

import com.example.demo.dto.in.ShoeStockUpdates;
import com.example.demo.dto.out.Stock;
import com.example.demo.mapper.StockInventoryMapper;
import com.example.demo.repository.ShopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Implementation(version = 3)
public class StockCoreV3 extends AbstractStockCore {

  @Autowired
  private ShopRepository shopRepository;

  @Autowired
  private StockInventoryMapper stockInventoryMapper;

  public StockCoreV3(ShopRepository shopRepository, StockInventoryMapper stockInventoryMapper) {
    this.shopRepository = shopRepository;
    this.stockInventoryMapper = stockInventoryMapper;
  }

  @Override
  public Stock getStock(Integer shopId) throws Exception {
    com.example.demo.model.Shop shopEntity = shopRepository.findById(shopId).orElseThrow();

    return stockInventoryMapper.storeToStockDTO(shopEntity);
  }

  @Override
  @Transactional
  public void UpdateStock(Integer shopId, @Valid ShoeStockUpdates updates) throws Exception {
    com.example.demo.model.Shop shopEntity = shopRepository.findById(shopId).orElseThrow();

    // Get entities to update
    List<Integer> shoeIds = updates.getShoes().stream()
      .map(i -> i.getShoeId())
      .collect(Collectors.toList());

    // Validate if shop inventory contains shoe ids
    Boolean anyShoeInvalid = shopEntity.getInventory().stream()
      .anyMatch(i -> shoeIds.contains(i.getShoe().getId()));

    if(!anyShoeInvalid) {
      throw new ValidationException("Shoe doesn't exist for shop");
    }

    shopEntity.getInventory().stream()
      .filter(inv -> shoeIds.contains(inv.getShoe().getId()))
      .forEach(inv -> inv.setQuantity(getQuantity(updates.getShoes(), inv.getShoe().getId())));

    // Validate if not above Max Stock
    Integer stock = shopEntity.getInventory().stream()
      .map(i -> i.getQuantity())
      .reduce(Integer::sum)
      .get();

    // Instead of relying on validators, validate here because shop's max stock is configurable
    if(stock > shopEntity.getMaxStock()) {
      String errorMessage = MessageFormat.format("Shop {0} cannot handle more than {1} boxes of shoes.", shopEntity.getName(), shopEntity.getMaxStock());
      throw new ValidationException(errorMessage);
    }
    
    shopRepository.saveAndFlush(shopEntity);
  }

  private Integer getQuantity (List<ShoeStockUpdate> stockUpdates, Integer shoeId) {
    return stockUpdates.stream()
      .filter(i -> i.getShoeId() == shoeId)
      .findAny()
      .orElseThrow()
      .getQuantity();
  }

}
