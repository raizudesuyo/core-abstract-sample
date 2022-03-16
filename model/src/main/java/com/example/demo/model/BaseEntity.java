package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
}
