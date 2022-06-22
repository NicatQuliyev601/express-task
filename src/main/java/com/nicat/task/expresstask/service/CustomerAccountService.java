package com.nicat.task.expresstask.service;

import com.nicat.task.expresstask.entity.CustomerAccount;
import com.nicat.task.expresstask.entity.User;
import com.nicat.task.expresstask.enums.Status;
import com.nicat.task.expresstask.payload.CustomerAccountRequest;
import com.nicat.task.expresstask.repository.CustomerAccountRepository;
import com.nicat.task.expresstask.repository.UserRepository;
import com.nicat.task.expresstask.response.Response;
import com.nicat.task.expresstask.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;
    private final UserRepository userRepository;

    public Response saveAccount(CustomerAccountRequest customerAccountRequest, UserPrincipal currentUser) {

        Response response = new Response(Status.SUCCESS);

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setBalance(0.0);
        customerAccount.setCurrency("AZN");
        customerAccount.setAccountNumber(customerAccountRequest.getAccountNumber());
        User user = userRepository.findByUsername(currentUser.getUsername()).get();
        customerAccount.setUser(user);
        response.setData(customerAccountRepository.save(customerAccount));
        return response;
    }


    public CustomerAccount increase(String account, Double amount) {
        CustomerAccount customerAccount = customerAccountRepository.findByAccountNumber(account);
        customerAccount.setBalance(customerAccount.getBalance() + amount);
        return customerAccountRepository.save(customerAccount);
    }

    public CustomerAccount decrease(String account, Double amount) {
        CustomerAccount customerAccount = customerAccountRepository.findByAccountNumber(account);
        customerAccount.setBalance(customerAccount.getBalance() - amount);
        return customerAccountRepository.save(customerAccount);
    }

    public Response increaseBalance(String account, Double amount) {
        Response response = new Response(Status.SUCCESS);
        response.setData(increase(account, amount));
        return response;
    }

    public Response decreaseBalance(String account, Double amount) {
        Response response = new Response(Status.SUCCESS);
        response.setData(decrease(account, amount));
        return response;
    }

}
