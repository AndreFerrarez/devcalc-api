package com.devcalc;


import io.javalin.Javalin;
import io.javalin.http.Context;

public class App {

    public static void main(String[] args) {
        CalculatorService calc = new CalculatorService();
        Javalin app = Javalin.create().start(8080);

        app.get("/add",       ctx -> handle(ctx, calc::add));
        app.get("/subtract",  ctx -> handle(ctx, calc::subtract));
        app.get("/multiply",  ctx -> handle(ctx, calc::multiply));
        app.get("/divide",    ctx -> {
            try {
                handle(ctx, calc::divide);
            } catch (ArithmeticException e) {
                ctx.status(400).result(e.getMessage());
            }
        });
        app.get("/sqrt", ctx -> {
            try {
                double x = Double.parseDouble(ctx.queryParam("x"));
                double result = calc.sqrt(x);
                ctx.result(String.valueOf(result));

            } catch (NumberFormatException e) {
                ctx.status(400).result("Erro: O parâmetro 'x' deve ser um número.");
            } catch (IllegalArgumentException e) {
                ctx.status(400).result(e.getMessage());
            }
        });



    }

    private static void handle(Context ctx, Operation op) {
        int a = Integer.parseInt(ctx.queryParam("a"));
        int b = Integer.parseInt(ctx.queryParam("b"));
        int result = op.apply(a, b);
        ctx.result(String.valueOf(result));
    }

    @FunctionalInterface
    private interface Operation {
        int apply(int a, int b);
    }
}
