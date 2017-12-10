package com.mobilecalculator.MainCalculator.Calc;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mobilecalculator.MainCalculator.Utils.Constants;
import com.mobilecalculator.R;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements CalculatorContract.View {

    private TextView mResult, mButtonCe, mButtonCl, mButtonCalculate;
    private Activity mActivity;
    private CalculatorPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initPresenter();
    }

    private void initViews() {
        mActivity = this;
        mResult = findViewById(R.id.result);
        mButtonCe = findViewById(R.id.btn_ce);
        mButtonCl = findViewById(R.id.btn_cl);
        mButtonCalculate = findViewById(R.id.btn_equals);
        initListeners();
    }

    private void initPresenter() {
        mPresenter = new CalculatorPresenter();
        mPresenter.init(this);
    }

    private void initListeners() {
        mButtonCe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLastCharAndSetExpression();
            }
        });

        mButtonCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextToResultView(null);
            }
        });

        mButtonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence currentCharSequence = mResult.getText();
                if (currentCharSequence != null) {
                    mPresenter.calculateExpression(currentCharSequence.toString());
                } else {
                    setTextToResultView(null);
                }
            }
        });
    }

    private void removeLastCharAndSetExpression() {
        CharSequence charSequence = mResult.getText();
        if (charSequence != null) {
            String currentExpression = charSequence.toString();
            String expressionToSet = currentExpression.substring(0, currentExpression.length() - 1);
            clearResultView();
            setTextToResultView(expressionToSet);
        } else {
            setTextToResultView(null);
        }
    }

    private void clearResultView() {
        mResult.setText(null);
    }

    public void onExpressionButtonClicked(View view) {
        Object object = view.getTag();
        if (object instanceof String) {
            setTextToResultView((String) object);
        }
    }

    @Override
    public void onExpressionCalculatedSuccessfully(String result) {
        setTextToResultView(result, true);
    }

    @Override
    public void onError() {
        setTextToResultView(getString(R.string.general_error_text), true);
    }

    @Override
    public void onArithmeticException(String messageToBeDisplayed) {
        setTextToResultView(messageToBeDisplayed);
    }

    private void setTextToResultView(String expression, boolean isErrorText) {
        if (TextUtils.isEmpty(expression)) {
            mResult.setText(Constants.DEFAULT_EXPRESSION);
        } else {
            mResult.append(expression);
        }

        int colorResourceId;
        if (isErrorText) {
            colorResourceId = ContextCompat.getColor(mActivity, R.color.error_color);
        } else {
            colorResourceId = ContextCompat.getColor(mActivity, R.color.text_color);
        }
        mResult.setTextColor(colorResourceId);
    }



    private void setTextToResultView(String expression) {
       setTextToResultView(expression, false);
    }
}
