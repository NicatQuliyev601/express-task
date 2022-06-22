package com.nicat.task.expresstask.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
public class StockTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum TransactionType {
        BUY("buy"), SELL("sell");
        @SuppressWarnings("unused")
        private String type;

        TransactionType(String type) {
            this.type = type;
        }
    }

    ;

    private TransactionType type;
    private Integer shares;
    private Double price;
    private Date transactionTime;
    private String symbol;
    private Long userId;

    @ManyToOne
    @ToString.Exclude
    private StockHolding stockHolding;

    public StockTransaction() {
    }

    public StockTransaction(StockHolding stockHolding, Integer shares, TransactionType type, Double price) {
        this.shares = shares;
        this.stockHolding = stockHolding;
        this.transactionTime = new Date();
        this.symbol = stockHolding.getSymbol();
        this.type = type;
        this.userId = stockHolding.getOwnerId();
        this.price = price;
    }
}
