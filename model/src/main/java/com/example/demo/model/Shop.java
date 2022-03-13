package com.example.demo.model;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Shop extends BaseEntity {
    
    @NotNull
    String name;

    @NotNull
    Integer maxStock;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    List<Inventory> inventory;

}
