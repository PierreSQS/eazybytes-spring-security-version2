package com.eazybytes.controller;

import com.eazybytes.model.Customer;
import com.eazybytes.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
    Customer customer;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerRepository mockCustomerRepo;

    @MockBean
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setPwd("PWD");
        customer.setCreateDt(LocalDateTime.now().toString());
        customer.setName("MockUser");
        customer.setEmail("mockuser@example.com");
        customer.setMobileNumber("1234567");
    }

    @Test
    void registerUser() throws Exception {
        // Given
        Customer savedMockCustomer = new Customer();
        savedMockCustomer.setId(1);
        savedMockCustomer.setEmail(customer.getEmail());
        savedMockCustomer.setPwd(customer.getPwd());
        savedMockCustomer.setName(customer.getName());
        savedMockCustomer.setCreateDt(customer.getCreateDt());
        savedMockCustomer.setMobileNumber(customer.getMobileNumber());

        given(passwordEncoder.encode("PWD"))
                .willReturn("$2a$10$8sNMOpPNVH98tUxPgqAfx.2rRO8Zo9Bvqr2A15xll/QBuAIYjddCG");

        given(mockCustomerRepo.save(any())).willReturn(savedMockCustomer);

        mockMvc.perform(post("/register")
                        .content(objectMapper.writeValueAsBytes(customer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Given user details are successfully registered !!")))
                .andDo(print());
    }

    @Test
    void getUserDetailsAfterLogin1() throws Exception {
        List<Customer> customerList = List.of(customer);
        given(mockCustomerRepo.findByEmail(anyString())).willReturn(customerList);
        mockMvc.perform(get("/user")
                        .with(authentication(new UsernamePasswordAuthenticationToken("mockUser@example.com","54321",null))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getUserDetailsAfterLogin2() throws Exception {
        List<Customer> customerList = List.of(customer);
        given(mockCustomerRepo.findByEmail(anyString())).willReturn(customerList);
        mockMvc.perform(get("/user")
                        .with(user("MockUser").password("mockPWD")))
                .andExpect(status().isOk())
                .andDo(print());
    }
}