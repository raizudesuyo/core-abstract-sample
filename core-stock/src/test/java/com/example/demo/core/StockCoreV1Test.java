package com.example.demo.core;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import com.example.demo.dto.in.ShoeStockUpdate;
import com.example.demo.dto.in.ShoeStockUpdates;
import com.example.demo.dto.out.Stock;
import com.example.demo.mapper.StockInventoryMapper;
import com.example.demo.repository.ShopRepository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@ExtendWith(MockitoExtension.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.example.demo.core", "com.example.demo.mapper", "com.example.demo.facade"})
@EntityScan(basePackages = {"com.example.demo.model"})
@EnableJpaRepositories("com.example.demo.repository")
@SpringBootTest(classes = { StockCoreV1Test.class })
@Sql(scripts = "/test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test-cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class StockCoreV1Test {

  @Autowired
  private StockCoreV1 stockCoreV1;

  private int SHOP_ID = 1;

  public StockCoreV1Test() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @Transactional
  void testUpdateStock_UpdateToFull() throws Exception {
    List<ShoeStockUpdate> shoes = new ArrayList<>();
    shoes.add(new ShoeStockUpdate(2, 10)); // Should bring shoe total to 30

    ShoeStockUpdates updates = new ShoeStockUpdates(shoes);

    stockCoreV1.UpdateStock(SHOP_ID, updates);

    Stock stock = stockCoreV1.getStock(1);
    Assertions.assertEquals(Stock.StockState.FULL, stock.getState());
  }

  @Test
  @Transactional
  void testUpdateStock_UpdateToEmpty() throws Exception {
    List<ShoeStockUpdate> shoes = new ArrayList<>();
    shoes.add(new ShoeStockUpdate(1, 0));
    shoes.add(new ShoeStockUpdate(2, 0));
    shoes.add(new ShoeStockUpdate(3, 0));
    ShoeStockUpdates updates = new ShoeStockUpdates(shoes);

    stockCoreV1.UpdateStock(SHOP_ID, updates);

    Stock stock = stockCoreV1.getStock(1);
    Assertions.assertEquals(Stock.StockState.EMPTY, stock.getState());
  }

  @Test
  @Transactional
  void testUpdateStock_UpdateToBeyondFull() throws Exception {
    List<ShoeStockUpdate> shoes = new ArrayList<>();
    shoes.add(new ShoeStockUpdate(1, 40));
    ShoeStockUpdates updates = new ShoeStockUpdates(shoes);

    String message = Assertions.assertThrows(ValidationException.class, () -> {
      stockCoreV1.UpdateStock(SHOP_ID, updates);
    }).getMessage();

    Assertions.assertEquals("Shop MAIN_SHOP cannot handle more than 30 boxes of shoes.", message);
  }

  @Test
  @Transactional
  void testUpdateStock_CantHaveEmptyShoes() throws Exception {
    List<ShoeStockUpdate> shoes = new ArrayList<>();
    ShoeStockUpdates updates = new ShoeStockUpdates(shoes);

    String message = Assertions.assertThrows(ValidationException.class, () -> {
      stockCoreV1.UpdateStock(SHOP_ID, updates);
    }).getMessage();

    Assertions.assertNotNull(message);
  }

  @Test
  void testGetStock_shouldGetFromDatabase() throws Exception {
    Stock stock = stockCoreV1.getStock(1);

    // Test if it can get stock used in the database
    Assertions.assertEquals(Stock.StockState.SOME, stock.getState());
    Assertions.assertEquals(30, stock.getMaxQuanity());
    Assertions.assertEquals(3, stock.getShoes().size());
  }
}
