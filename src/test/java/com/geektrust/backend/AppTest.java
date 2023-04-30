package com.geektrust.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("App Test")
class AppTest {

    private List<String> commandArgs;

    @BeforeEach
    public void setUp() {
        commandArgs = Arrays.asList("sample_input.txt");
    }


    @Test
    public void Application_Test() {
        Assertions.assertTrue(true);
    }
}
