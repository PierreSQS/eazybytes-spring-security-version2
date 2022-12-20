package com.eazybytes.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.model.Contact;
import com.eazybytes.repository.ContactRepository;

@RestController
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    public Contact saveContactInquiryDetails(@RequestBody Contact contact) throws NoSuchAlgorithmException {
        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(LocalDateTime.now());
        return contactRepository.save(contact);
    }

    public String getServiceReqNumber() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR"+ranNum;
    }
}
