package com.nicat.task.expresstask.controller;


import com.nicat.task.expresstask.payload.CustomerAccountRequest;
import com.nicat.task.expresstask.response.Response;
import com.nicat.task.expresstask.security.CurrentUser;
import com.nicat.task.expresstask.security.UserPrincipal;
import com.nicat.task.expresstask.service.CustomerAccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
@Slf4j
public class CustomerAccountController {

    private CustomerAccountService customerAccountService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("hasRole('USER') ")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public Response saveAccount(@RequestBody CustomerAccountRequest customerAccountRequest,
                                @CurrentUser UserPrincipal currentUser) {
        log.info("account payload {}", customerAccountRequest);
        Response response = customerAccountService.saveAccount(customerAccountRequest, currentUser);
        log.info("response {}", response);
        return response;
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("hasRole('USER') ")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public Response increaseBalance(@RequestParam("accountNumber") String accountNumber,
                                    @RequestParam("amount") Double amount) {
        log.info("account  {},amount {}", accountNumber, amount);

        Response response = customerAccountService.increaseBalance(accountNumber, amount);
        log.info("response {}", response);
        return response;
    }

    @RequestMapping(value = "/decrease", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("hasRole('USER') ")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public Response decreaseBalance(@RequestParam("accountNumber") String accountNumber,
                                    @RequestParam("amount") Double amount) {

        log.info("account  {},amount {}", accountNumber, amount);

        Response response = customerAccountService.decreaseBalance(accountNumber, amount);
        log.info("response {}", response);

        return response;
    }
}
