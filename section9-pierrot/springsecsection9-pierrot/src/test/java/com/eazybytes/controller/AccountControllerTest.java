package com.eazybytes.controller;

import com.eazybytes.model.Accounts;
import com.eazybytes.repository.AccountsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    private Accounts mockAccounts;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountsRepository mockAccountsRepo;

    @BeforeEach
    void setUp() {
        mockAccounts = new Accounts();
        mockAccounts.setAccountNumber(11111111111L);
        mockAccounts.setAccountType("BUSINESS");
        mockAccounts.setCreateDt(LocalDateTime.now().toString());
        mockAccounts.setCustomerId(1);
    }

    @WithMockUser(username = "MockUser",password = "MockPwd")
    @Test
    void getAccountDetails() throws Exception {
        // Given
        given(mockAccountsRepo.findByCustomerId(anyInt())).willReturn(mockAccounts);
        mockMvc.perform(get("/myAccount").param("id","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(equalTo(11111111111L)))
                .andExpect(jsonPath("$.createDt").value(containsString(LocalDate.now().toString())))
                .andDo(print());
    }
}