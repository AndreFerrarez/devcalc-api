package com.devcalc;

// Testandoo.
public class CalculatorService {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Divisão por zero não permitida");
        }
        return a / b;
    }

    public double sqrt(double x) {
        if (x < 0) {
            throw new IllegalArgumentException("Raiz quadrada de número negativo não é permitida.");
        }
        return Math.sqrt(x);
    }
}