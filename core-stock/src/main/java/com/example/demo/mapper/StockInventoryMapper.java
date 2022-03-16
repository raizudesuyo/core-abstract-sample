package com.example.demo.mapper;

import com.example.demo.dto.out.ShoeStock;
import com.example.demo.dto.out.Stock;
import com.example.demo.dto.out.Stock.StockBuilder;
import com.example.demo.model.Inventory;
import com.example.demo.model.Shop;

import org.springframework.stereotype.Component;

@Component
public class StockInventoryMapper {

    public Stock storeToStockDTO(Shop shop) {
        
        StockBuilder stock = Stock.builder()
            .maxQuanity(shop.getMaxStock())
            .shopName(shop.getName());
        
        for (Inventory inventoryEntry : shop.getInventory()) {    
            stock.shoe(ShoeStock.builder()
                .id(inventoryEntry.getShoe().getId())
                .name(inventoryEntry.getShoe().getName())
                .color(inventoryEntry.getShoe().getColor())
                .size(inventoryEntry.getShoe().getSize())
                .quantity(inventoryEntry.getQuantity())
                .build());
        }
        
        Integer shoesInStore = shop.getInventory().stream()
            .map(inventory -> inventory.getQuantity())
            .reduce(Integer::sum)
            .get();
        
        // Figure out stock state, based on store max stock, currently set globally at 30
        if(shoesInStore <= 0) 
            stock.state(Stock.StockState.EMPTY);
        else if (shoesInStore < shop.getMaxStock())
            stock.state(Stock.StockState.SOME);
        else // includes if it has a max stock, or more than that, still return FULL, saving shop inventory supposed to handle the validation
            stock.state(Stock.StockState.FULL);
        
        return stock.build();
    }

}
