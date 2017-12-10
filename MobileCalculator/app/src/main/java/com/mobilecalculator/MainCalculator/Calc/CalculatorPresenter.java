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
    private CalcRepository mRepository;


    public CalculatorPresenter(CalcRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public void calculateExpression(String expressionString) {
        if (mView == null) {
            throw new IllegalStateException("presenter.attachView() must be called before calling this function");
        }

        try {
            mView.onExpressionCalculatedSuccessfully(mRepository.getCalculatedStringFromExpression(expressionString));
        } catch (ArithmeticException ae) {
            ae.printStackTrace();
            mView.onArithmeticException(ae.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mView.onError();
        }
    }

    @Override
    public String parseExpression(@NonNull String expressionToBeParsed, @NonNull String toBeAppended) {
        if (!toBeAppended.endsWith(Constants.CHAR_DOT) && !expressionToBeParsed.contains(Constants.CHAR_DOT)) {
            if (expressionToBeParsed.startsWith(Constants.DEFAULT_EXPRESSION)) {
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
