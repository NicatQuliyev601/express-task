package com.nicat.task.expresstask.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private String company;
    private Double price;
}
