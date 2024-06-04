package com.burdettracker.budgedtrackerproject.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class IndexPageControllerTest {

    @Autowired
    IndexPageController indexPageController;

    @BeforeEach
    void setUp() {
        indexPageController = new IndexPageController();
    }

    @Test
    void homePage() {
        String viewName = indexPageController.homePage();
        Assertions.assertEquals("index", viewName);
    }

    @Test
    void home() {
        String viewName = indexPageController.home();
        Assertions.assertEquals("index", viewName);
    }

    @Test
    void getFAQPage() {
        String viewName = indexPageController.getFAQPage();
        Assertions.assertEquals("/FAQsPage", viewName);
    }

    @Test
    void getContactPage() {
        String viewName = indexPageController.getContactPage();
        Assertions.assertEquals("/contactsPage", viewName);
    }
}