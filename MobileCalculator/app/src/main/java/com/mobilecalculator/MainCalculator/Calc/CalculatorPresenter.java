package com.mobilecalculator.MainCalculator.Calc;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mobilecalculator.MainCalculator.Utils.Constants;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by ankit on 10/12/17.
 */

public class CalculatorPresenter implements CalculatorContract.Presenter {
    private final String TAG = CalculatorPresenter.class.getSimpleName();
    private CalculatorContract.View mView;

    @Override
    public void calculateExpression(String expressionString) {
        if (mView == null) {
            throw new IllegalStateException("presenter.attachView() must be called before calling this function");
        }

        try {
            Expression expression = new Expression(expressionString);
            BigDecimal decimal = expression.eval();
            decimal = decimal.stripTrailingZeros();
            Log.d(TAG, decimal.toString());
            DecimalFormat format = new DecimalFormat("#.##########");
            mView.onExpressionCalculatedSuccessfully(String.valueOf(format.format(decimal.doubleValue())));
        } catch (ArithmeticException ae) {
            ae.printStackTrace();
            mView.onArithmeticException(ae.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mView.onError();
        }
    }

    @Override
    public String parseExpression(@NonNull String expressionToBeParsed, String toBeAppended) {
        Log.d(TAG, expressionToBeParsed);
        if (!expressionToBeParsed.endsWith(Constants.CHAR_DOT)) {
            if (expressionToBeParsed.startsWith(Constants.DEFAULT_EXPRESSION)) {
                Log.d(TAG, expressionToBeParsed.substring(1, expressionToBeParsed.length()));

                expressionToBeParsed = expressionToBeParsed.substring(1, expressionToBeParsed.length());
            }
        }

        if (Constants.DEFAULT_EXPRESSION.equalsIgnoreCase(toBeAppended) && Constants.DEFAULT_EXPRESSION.equalsIgnoreCase(expressionToBeParsed)) {
            return expressionToBeParsed;
        }
        return expressionToBeParsed + toBeAppended;
    }

    public CalculatorContract.View getView() {
        return this.mView;
    }

    public void attachView(CalculatorContract.View view) {
        if (view == null) {
            throw new IllegalArgumentException("View can not be null. Cannot initialise presenter.");
        }
        this.mView = view;
    }
}
