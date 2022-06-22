package com.nicat.task.expresstask.service;

import com.nicat.task.expresstask.enums.Status;
import com.nicat.task.expresstask.payload.CustomerAccountRequest;
import com.nicat.task.expresstask.repository.UserRepository;
import com.nicat.task.expresstask.response.Response;
import com.nicat.task.expresstask.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CustomerAccountServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CustomerAccountService customerAccountService;

    @Test
    void saveAccount() {
        CustomerAccountRequest customerAccountRequest = new CustomerAccountRequest();
        customerAccountRequest.setAccountNumber("AA456789");
        customerAccountRequest.setCurrency("AZN");
        customerAccountRequest.setBalance(0.0);

        UserPrincipal currentUser = new UserPrincipal(1L, "Nicat Quliyev", "quliyevnicat",
                "quliyev.nicat2003@gmail.com", "test1234", null);

        Response response = customerAccountService.saveAccount(customerAccountRequest, currentUser);
        //assertEquals(response.getStatus(),Status.SUCCESS);
        assertThat(response.getStatus()).isEqualTo(Status.SUCCESS);
    }

    @Test
    void increaseBalance() {
        Response response = customerAccountService.increaseBalance("AA456789", 345.0);
        assertThat(response.getStatus()).isEqualTo(Status.SUCCESS);
    }

    @Test
    void decreaseBalance() {
        Response response = customerAccountService.increaseBalance("AA456789", 3.0);
        assertThat(response.getStatus()).isEqualTo(Status.SUCCESS);
    }
}