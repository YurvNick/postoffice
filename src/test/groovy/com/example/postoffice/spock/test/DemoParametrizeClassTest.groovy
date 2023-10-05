package com.example.postoffice.spock.test

import spock.lang.Specification

class DemoParametrizeClassTest extends Specification {

    static DemoParametrizeClass demoParametrizeClass

    def setupSpec() {
        demoParametrizeClass = new DemoParametrizeClass()
    }

    void plus() {
        when: "вызываем метод тестового класса"
        def plus = demoParametrizeClass.plus(argument)

        then: "проверяем, что метод для 0 прибавит 100, а для остальных чисел 1"
        plus == result

        where:
        argument | result
        0        | 100
        1        | 2
        3        | 4
        5        | 6
    }
}