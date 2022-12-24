package com.eazybytes.repository;

import com.eazybytes.model.AccountTransactions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountTransactionsRepositoryTest {

    @Autowired
    AccountTransactionsRepository accountTransRepo;


    @BeforeEach
    void setUp() {
    }

    @Test
    void findByCustomerIdOrderByTransactionDtDesc() {
        List<AccountTransactions> byCustomerIdOrderByTransactionDtDesc
                = accountTransRepo.findByCustomerIdOrderByTransactionDtDesc(1);

        assertThat(byCustomerIdOrderByTransactionDtDesc).hasSize(6);
    }
}