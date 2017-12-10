package com.mobilecalculator.MainCalculator.Calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mobilecalculator.MainCalculator.Utils.Constants;
import com.mobilecalculator.R;

public class MainActivity extends AppCompatActivity {

    private TextView mResult, mButtonCe, mButtonCl, mButtonCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mResult = findViewById(R.id.result);
        mButtonCe = findViewById(R.id.btn_ce);
        mButtonCl = findViewById(R.id.btn_cl);
        mButtonCalculate = findViewById(R.id.btn_equals);
        initListners();
    }

    private void initListners() {
        
    }

    public void onExpressionButtonClicked(View view) {
        Object object = view.getTag();
        if (object instanceof String) {
            setTextToResultView((String) object);
        }
    }

    private void setTextToResultView(String expression) {
        if (TextUtils.isEmpty(expression)) {
            mResult.setText(Constants.DEFAULT_EXPRESSION);
        } else {
            mResult.append(expression);
        }
    }
}
