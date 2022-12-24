package com.eazybytes.controller;

import com.eazybytes.model.Contact;
import com.eazybytes.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Contact> contactArgCaptor;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ContactRepository mockContactRepo;

    Contact contactToSave, savedContact;

    @BeforeEach
    void setUp() {
        contactToSave = new Contact();
        contactToSave.setContactName("Pierrot User");

        savedContact = new Contact();
        savedContact.setContactName(contactToSave.getContactName());
        savedContact.setCreateDt(LocalDateTime.now());
        savedContact.setContactId("1");
    }

    @Test
    @WithMockUser(username = "MockUser",password = "MockPWD")
    void saveContactInquiryDetails() throws Exception {
        given(mockContactRepo.save(contactToSave)).willReturn(savedContact);
        mockMvc.perform(post("/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactToSave))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());

        verify(mockContactRepo).save(contactArgCaptor.capture());
        assertThat(contactArgCaptor.getValue().getContactName()).isEqualTo("Pierrot User");
    }
}