package com.burdettracker.budgedtrackerproject.exeption;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {


    private GlobalExceptionHandler globalExceptionHandler;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RuntimeException exception;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        exception = new RuntimeException();
    }

    @Test
    public void testHandleRuntimeException() {
        ModelAndView modelAndView = globalExceptionHandler.handleRuntimeException();

        assertEquals("error/404", modelAndView.getViewName());
    }
}