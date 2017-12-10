package com.mobilecalculator;

import com.mobilecalculator.MainCalculator.Calc.CalculatorContract;
import com.mobilecalculator.MainCalculator.Calc.CalculatorPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by ankit on 10/12/17.
 */

public class CalculatorPresenterTest {

    private String mArithmeticErrorExpression = "7/0";
    private String mValidExpression = "2*4+5-6";

    @Mock
    CalculatorContract.View mView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void attachView() {
        CalculatorPresenter presenter = new CalculatorPresenter();
        assertNull(presenter.getView());

        presenter.attachView(mView);
        assertNotNull(presenter.getView());
    }

    @Test
    public void arithMeticErrorCheck() {

    }

    @Test
    public void generalErrorCheck() {

    }

    @Test
    public void validExpressionCheck() {

    }
}
