package com.example.demo.model;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Shoe extends BaseEntity {

    @NotEmpty
    String name;

    @NotEmpty
    String color;

    @NotNull
    Integer size;

    @OneToMany
    List<Inventory> Inventory;

}
