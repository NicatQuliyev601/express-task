package com.nicat.task.expresstask.controller;

import com.nicat.task.expresstask.response.Response;
import com.nicat.task.expresstask.security.CurrentUser;
import com.nicat.task.expresstask.security.UserPrincipal;
import com.nicat.task.expresstask.service.StockService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @RequestMapping(value = "/stocks", method = RequestMethod.GET)
    public Response getStockList() {
        Response response = stockService.getStockList();
        return response;
    }

    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public Response buyStock(String symbol, int numberOfShares, @CurrentUser UserPrincipal userPrincipal) {
        Response response = stockService.buyStock(symbol, numberOfShares, userPrincipal);

        return response;
    }

    @RequestMapping(value = "/sell", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public Response sellStock(String symbol, int numberOfShares, @CurrentUser UserPrincipal userPrincipal) {
        Response response = stockService.sellStock(symbol, numberOfShares, userPrincipal);

        return response;
    }
}
