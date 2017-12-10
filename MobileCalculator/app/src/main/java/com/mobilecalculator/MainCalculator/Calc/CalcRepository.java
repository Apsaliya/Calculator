package com.mobilecalculator.MainCalculator.Calc;

import android.util.Log;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by ankit on 10/12/17.
 */

public class CalcRepository {

    public String getCalculatedStringFromExpression(String expressionString) {
        Expression expression = new Expression(expressionString);
        BigDecimal decimal = expression.eval();
        decimal = decimal.stripTrailingZeros();
        DecimalFormat format = new DecimalFormat("#.##########");
        return String.valueOf(format.format(decimal.doubleValue()));
    }
}
