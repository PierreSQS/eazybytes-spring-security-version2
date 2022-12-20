package com.eazybytes.controller;

import com.eazybytes.model.AccountTransactions;
import com.eazybytes.repository.AccountTransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BalanceController.class)
class BalanceControllerTest {

    AccountTransactions mockTransactions;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountTransactionsRepository mockTransRepo;

    @BeforeEach
    void setUp() {
        mockTransactions = new AccountTransactions();
        mockTransactions.setTransactionId("1");
        mockTransactions.setTransactionAmt(10000);
        mockTransactions.setAccountNumber(11111111L);
        mockTransactions.setClosingBalance(111100);
        mockTransactions.setCreateDt(LocalDateTime.now().toString());
        mockTransactions.setTransactionDt(LocalDateTime.now());
        mockTransactions.setTransactionType("Debit");
    }


    @WithMockUser(username = "MockUser",password = "MockPwd")
    @Test
    void getBalanceDetails() throws Exception {
        // Given
        given(mockTransRepo.findByCustomerIdOrderByTransactionDtDesc(anyInt())).willReturn(List.of(mockTransactions));

        mockMvc.perform(get("/myBalance").param("id","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$").value(hasSize(1))) // equivalent to above assertion
                .andExpect(jsonPath("$.length()").value(equalTo(1)))
                .andExpect(jsonPath("$[0].accountNumber").value(equalTo(11111111)))
                .andExpect(jsonPath("$[0].createDt").value(containsString(LocalDate.now().toString())))
                .andDo(print());
    }
}