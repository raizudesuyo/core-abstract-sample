package com.example.demo.dto.in;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ShoeStockUpdate {

    @NotNull(message = "Shoe Id cannot be null")
    @Min(value = 0, message = "Shoe quantity cannot be negative")
    Integer shoeId;

    @NotNull(message = "Shoe quantity cannot be null")
    @Min(value = 0, message = "Shoe quantity cannot be negative")
    Integer quantity;
}
