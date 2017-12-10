package com.mobilecalculator.MainCalculator.Calc;

import android.util.Log;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by ankit on 10/12/17.
 */

public class CalcRepository {

    private final String TAG = CalcRepository.class.getSimpleName();

    public String getCalculatedStringFromExpression(String expressionString) {
        Expression expression = new Expression(expressionString);
        BigDecimal decimal = expression.eval();
        decimal = decimal.stripTrailingZeros();
        DecimalFormat format = new DecimalFormat("#.##########");
        Log.d(TAG, "" + decimal.doubleValue());
        double result = decimal.doubleValue();
        String resultString;
        if (result == (long) result) {
            resultString = String.valueOf((long) result);
        } else {
            resultString = String.valueOf(result);
        }
        return resultString;
    }
}
