package com.mobilecalculator.MainCalculator.Calc;

/**
 * Created by ankit on 10/12/17.
 */

public class CalculatorContract {

    public interface View {
        void onExpressionCalculatedSuccessfully(String result);
        void onArithmeticException(String messageToBeDisplayed);
        void onError();
    }

    public interface Presenter {
        void calculateExpression(String expression);
    }
}
