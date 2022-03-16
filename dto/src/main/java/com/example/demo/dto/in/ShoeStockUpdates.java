package com.example.demo.dto.in;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ShoeStockUpdates {

    @NotEmpty(message = "Shoes cannot be empty")
    @Valid
    List<ShoeStockUpdate> shoes;
}
