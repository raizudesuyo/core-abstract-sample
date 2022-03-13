package com.example.demo.dto.out;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ShoeStock {
    Integer id;
    String name;
    String color;
    Integer size;
    Integer quantity;
}