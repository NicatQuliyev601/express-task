package com.nicat.task.expresstask.repository;

import com.nicat.task.expresstask.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount,Long> {
    CustomerAccount findByAccountNumber(String accountNumber);
}
