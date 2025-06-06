package com.devcalc;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class CalculatorServiceTest {

    private CalculatorService svc;

    @BeforeEach
    void setUp() {
        svc = new CalculatorService();
    }

    @Test
    void testAdd() {
        assertEquals(15, svc.add(10, 5));
    }

    @Test
    void testSubtract() {
        assertEquals(5, svc.subtract(10, 5));
    }

    @Test
    void testMultiply() {
        assertEquals(50, svc.multiply(10, 5));
    }

    @Test
    void testDivide() {
        assertEquals(2, svc.divide(10, 5));
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> svc.divide(10, 0));
    }

    @Test
    void testSqrt() {
        assertEquals(4.0, svc.sqrt(16));
    }

    @Test
    void testSqrtNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            svc.sqrt(-1);
        });
    }


}