package com.eazybytes.repository;

import com.eazybytes.model.Loans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoanRepositoryTest {

    @Autowired
    LoanRepository loanRepository;

    @Test
    void findByCustomerIdOrderByStartDtDesc() {
        List<Loans> byCustomerIdOrderByStartDtDesc = loanRepository.findByCustomerIdOrderByStartDtDesc(1);
        assertThat(byCustomerIdOrderByStartDtDesc).hasSize(4);
    }
}