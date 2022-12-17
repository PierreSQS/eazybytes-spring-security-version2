package com.eazybytes.controller;

import com.eazybytes.model.Cards;
import com.eazybytes.repository.CardsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardsController.class)
class CardsControllerTest {

    @Autowired
    MockMvc mockMvc;

    List<Cards> mockCardsList;

    @MockBean
    CardsRepository mockCardsRepo;

    @BeforeEach
    void setUp() {
        Cards card1 = new Cards();
        card1.setCardId(111111111);
        card1.setCreateDt(LocalDateTime.now());

        Cards card2 = new Cards();
        card2.setCardId(222222222);
        card2.setCreateDt(LocalDateTime.now());

        mockCardsList = List.of(card1,card2);
    }

    @Test
    @WithMockUser(username = "MockUser",password = "MockPWD")
    void getCardDetails() throws Exception {
        String customerId = "121212";
        given(mockCardsRepo.findByCustomerId(anyInt())).willReturn(mockCardsList);
        mockMvc.perform(get("/myCards").param("customerId",customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$.length()").value(equalTo(2)))
                .andDo(print());
    }
}