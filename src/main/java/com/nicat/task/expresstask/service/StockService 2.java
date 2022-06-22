package com.nicat.task.expresstask.service;

import com.nicat.task.expresstask.entity.Stock;
import com.nicat.task.expresstask.entity.StockHolding;
import com.nicat.task.expresstask.entity.User;
import com.nicat.task.expresstask.enums.Status;
import com.nicat.task.expresstask.repository.StockHoldingRepository;
import com.nicat.task.expresstask.repository.StockRepository;
import com.nicat.task.expresstask.repository.UserRepository;
import com.nicat.task.expresstask.response.Response;
import com.nicat.task.expresstask.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    private final StockHoldingRepository stockHoldingRepository;
    private final CustomerAccountService customerAccountService;
    private EmailService emailService;

    public Response getStockList() {
        Response response = new Response(Status.SUCCESS);
        response.setData(stockRepository.findAll());
        return response;
    }

    public Response buyStock(String symbol, int numberOfShares, UserPrincipal userPrincipal) {
        Response response = new Response(Status.SUCCESS);
        User user = userRepository.findByUsername(userPrincipal.getUsername()).get();
        Stock stock = stockRepository.findBySymbol(symbol);
        StockHolding stockHolding = StockHolding.buyShares(user, symbol, numberOfShares, stock.getPrice());
        stockHoldingRepository.save(stockHolding);
        customerAccountService.decrease(user.getCustomerAccounts().getAccountNumber(), stock.getPrice());
        emailService.sendMail(user.getEmail(),
                "Buy- " + stock.getSymbol(),
                "You bought this " + stock.getSymbol() + " stock"
                        + ", this stock price is " + stock.getPrice()
                        + ", numberOfShares : " + numberOfShares
                        + ", Current balance is  " + user.getCustomerAccounts().getBalance()
        );

        response.setData(stockHoldingRepository.save(stockHolding));
        return response;
    }

    public Response sellStock(String symbol, int numberOfShares, UserPrincipal userPrincipal) {
        Response response = new Response(Status.SUCCESS);
        User user = userRepository.findByUsername(userPrincipal.getUsername()).get();
        Stock stock = stockRepository.findBySymbol(symbol);
        StockHolding stockHolding = StockHolding.buyShares(user, symbol, numberOfShares, stock.getPrice());
        stockHoldingRepository.save(stockHolding);
        customerAccountService.increase(user.getCustomerAccounts().getAccountNumber(), stock.getPrice());

        emailService.sendMail(user.getEmail(),
                "Sell- " + stock.getSymbol(),
                "You sell this " + stock.getSymbol() + " stock"
                        + ", this stock price is " + stock.getPrice()
                        + ", numberOfShares: " + numberOfShares
                        + ", Current balance is: " + user.getCustomerAccounts().getBalance()
        );
        response.setData(stockHoldingRepository.save(stockHolding));
        return response;
    }
}
