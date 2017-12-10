package com.mobilecalculator.MainCalculator.Calc;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mobilecalculator.MainCalculator.Utils.Constants;
import com.mobilecalculator.MainCalculator.Utils.UtilFunctions;
import com.mobilecalculator.R;

public class MainActivity extends AppCompatActivity implements CalculatorContract.View {

    private final String TAG = MainActivity.class.getSimpleName();
    private AppCompatTextView mResult;
    private TextView mButtonCe, mButtonCl, mButtonCalculate;
    private Activity mActivity;
    private boolean mIsInErrorMode = false, mIsResultDisplayed = false;
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
        setTextToResultView(null);
        initListeners();
    }

    private void initPresenter() {
        mPresenter = new CalculatorPresenter(new CalcRepository());
        mPresenter.attachView(this);
    }

    private void initListeners() {
        mButtonCe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLastCharAndSetExpression();
                mIsResultDisplayed = false;
            }
        });

        mButtonCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextToResultView(null);
                mIsResultDisplayed = false;
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
            if (mIsInErrorMode) {
                setTextToResultView(null);
            }
            setTextToResultView((String) object);
        }
        mIsResultDisplayed = false;
    }

    @Override
    public void onExpressionCalculatedSuccessfully(String result) {
        clearResultView();
        setTextToResultView(result);
        mIsResultDisplayed = true;
    }

    @Override
    public void onError() {
        clearResultView();
        setTextToResultView(getString(R.string.general_error_text), true);
    }

    @Override
    public void onArithmeticException(String messageToBeDisplayed) {
        clearResultView();
        setTextToResultView(messageToBeDisplayed, true);
    }

    private void setTextToResultView(String expression, boolean isErrorText) {
        if (TextUtils.isEmpty(expression)) {
            mResult.setText(Constants.DEFAULT_EXPRESSION);
        } else {
            CharSequence charSequence = mResult.getText();
            if (charSequence != null) {
                if (mIsResultDisplayed && !UtilFunctions.isOperator(expression)) {
                    mResult.setText(expression);
                } else {
                    String currentText = charSequence.toString();
                    String newExpression = mPresenter.parseExpression(currentText, expression);
                    Log.d(TAG, newExpression);
                    mResult.setText(newExpression);
                }
            }
        }

        int colorResourceId;
        int textSizeInSp;
        if (isErrorText) {
            mIsInErrorMode = true;
            colorResourceId = ContextCompat.getColor(mActivity, R.color.error_color);
            textSizeInSp = (int) getResources().getDimension(R.dimen.small_text_size);
        } else {
            mIsInErrorMode = false;
            colorResourceId = ContextCompat.getColor(mActivity, R.color.text_color);
            textSizeInSp = (int) getResources().getDimension(R.dimen.display_text_size);
        }
        mResult.setTextColor(colorResourceId);
        //mResult.setTextSize(textSizeInSp);
    }


    private void setTextToResultView(String expression) {
        setTextToResultView(expression, false);
    }
}
