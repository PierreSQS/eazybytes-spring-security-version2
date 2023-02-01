package com.eazybytes.controller;

import com.eazybytes.model.Contact;
import com.eazybytes.repository.ContactRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping("/contact")
    // @PreFilter("filterObject.contactName != 'Test'")
    @PostFilter("filterObject.contactName != 'Test'") // filterObject is in fact apply on the return Object not on the argument !!!
    public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) throws NoSuchAlgorithmException {
        Contact contact = contacts.get(0);
        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(LocalDateTime.now());
        contact = contactRepository.save(contact);
        List<Contact> returnContacts = new ArrayList<>();
        returnContacts.add(contact);
        return returnContacts;
    }

    public static String getServiceReqNumber() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();  // SecureRandom is preferred to Random
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR"+ranNum;
    }
}
