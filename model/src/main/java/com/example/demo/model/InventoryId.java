package com.example.demo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class InventoryId implements Serializable {
    Integer shoe;
    Integer shop;
}
