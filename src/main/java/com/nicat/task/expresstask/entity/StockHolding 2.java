package com.nicat.task.expresstask.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "stock_holdings")
@Data
public class StockHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private Long sharesOwned;
    private Long ownerId;

    @OneToMany(mappedBy = "stockHolding", cascade = CascadeType.ALL)
    private List<StockTransaction> transactions;

    private StockHolding() {}

    private StockHolding(String symbol, Long ownerId) {
        this.symbol = symbol.toUpperCase();
        this.sharesOwned = 0L;
        this.ownerId = ownerId;
        transactions = new ArrayList<StockTransaction>();
    }

    private void buyShares(int numberOfShares, Double price) {
        if (numberOfShares < 0) {
            throw new IllegalArgumentException("Can not purchase a negative number of shares.");
        }
        setSharesOwned(sharesOwned + numberOfShares);
        StockTransaction transaction = new StockTransaction(this, numberOfShares,
                StockTransaction.TransactionType.BUY, price);
        this.transactions.add(transaction);
    }


    private void sellShares(int numberOfShares, Double price) {

        if (numberOfShares > sharesOwned) {
            throw new IllegalArgumentException("Number to sell exceeds shares owned for stock" + symbol);
        }

        setSharesOwned(sharesOwned - numberOfShares);

        StockTransaction transaction = new StockTransaction(this,
                numberOfShares, StockTransaction.TransactionType.SELL, price);
        this.transactions.add(transaction);
    }


    public static StockHolding buyShares(User user, String symbol, int numberOfShares, Double price) {
        Map<String, StockHolding> userPortfolio = user.getPortfolio();
        StockHolding holding;
        // Create new holding, if user has never owned the stock before
        if (!userPortfolio.containsKey(symbol)) {
            holding = new StockHolding(symbol, user.getId());
            user.addHolding(holding);
        }
        holding = userPortfolio.get(symbol);
        holding.buyShares(numberOfShares, price);

        return holding;
    }


    public static StockHolding sellShares(User user, String symbol, int numberOfShares, Double price) {
        Map<String, StockHolding> userPortfolio = user.getPortfolio();
        StockHolding holding;
        if (!userPortfolio.containsKey(symbol)) {
            return null;
        }
        holding = userPortfolio.get(symbol);
        holding.sellShares(numberOfShares, price);


        return holding;
    }
}
