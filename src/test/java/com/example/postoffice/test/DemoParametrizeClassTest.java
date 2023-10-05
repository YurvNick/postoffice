package com.example.postoffice.test;

import com.example.postoffice.spock.test.DemoParametrizeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DemoParametrizeClassTest {

    static DemoParametrizeClass demoParametrizeClass;

    @BeforeAll
    static void beforeAll() {
        demoParametrizeClass = new DemoParametrizeClass();
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3 })
    void plus(int argument) {
        int plus = demoParametrizeClass.plus(argument);
        if (argument == 0) {
            Assertions.assertEquals(argument + 100,plus);
        } else {
            Assertions.assertEquals(argument + 1,plus);

        }
    }
}