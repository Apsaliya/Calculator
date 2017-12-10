package com.mobilecalculator.MainCalculator.Calc;

import android.util.Log;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

/**
 * Created by ankit on 10/12/17.
 */

public class CalculatorPresenter implements CalculatorContract.Presenter {
    private final String TAG = CalculatorPresenter.class.getSimpleName();
    private CalculatorContract.View mView;

    @Override
    public void calculateExpression(String expressionString) {
        if (mView == null) {
            throw new IllegalStateException("presenter.init() must be called before calling this function");
        }

        try {
            Expression expression = new Expression(expressionString);
            BigDecimal decimal = expression.eval();
            Log.d(TAG, decimal.toString());
            mView.onExpressionCalculatedSuccessfully(decimal.toString());
        } catch (ArithmeticException ae) {
            mView.onArithmeticException(ae.getMessage());
        } catch (Exception e) {
            mView.onError();
        }
    }

    public void init(CalculatorContract.View view) {
        if (view == null) {
            throw new IllegalArgumentException("View can not be null. Cannot initialise presenter.");
        }
        this.mView = view;
    }
}
