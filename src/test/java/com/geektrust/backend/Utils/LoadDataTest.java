package com.geektrust.backend.Utils;

import com.geektrust.backend.AppConfig.ApplicationConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoadDataTest {

    @DisplayName("")
    @Test
    void testLoadData() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        System.out.println(applicationConfig);
    }
}
