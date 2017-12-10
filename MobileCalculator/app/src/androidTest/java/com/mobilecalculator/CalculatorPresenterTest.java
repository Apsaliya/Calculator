package com.mobilecalculator;

import com.mobilecalculator.MainCalculator.Calc.CalcRepository;
import com.mobilecalculator.MainCalculator.Calc.CalculatorContract;
import com.mobilecalculator.MainCalculator.Calc.CalculatorPresenter;
import com.mobilecalculator.MainCalculator.Utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ankit on 10/12/17.
 */

public class CalculatorPresenterTest {

    private String mArithmeticErrorExpression = "7/0";
    private String mValidExpression = "2*4+5-6";

    @Mock
    CalcRepository mCalcRepository;

    @Mock
    CalculatorContract.View mView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void attachView() {
        CalculatorPresenter presenter = new CalculatorPresenter(mCalcRepository);
        assertNull(presenter.getView());

        presenter.attachView(mView);
        assertNotNull(presenter.getView());
    }

    @Test
    public void arithMeticErrorCheck() {
        CalculatorPresenter presenter = new CalculatorPresenter(mCalcRepository);

        when(mCalcRepository.getCalculatedStringFromExpression(Mockito.anyString())).thenThrow(new ArithmeticException("Some Arithmetic error."));

        presenter.attachView(mView);
        presenter.calculateExpression(mArithmeticErrorExpression);
        waitFor(100);
        verify(mView, times(1)).onArithmeticException("Some Arithmetic error.");

    }

    @Test
    public void generalErrorCheck() {
        CalculatorPresenter presenter = new CalculatorPresenter(mCalcRepository);

        when(mCalcRepository.getCalculatedStringFromExpression(Mockito.anyString())).thenThrow(new NullPointerException("Invalid expression."));

        presenter.attachView(mView);
        presenter.calculateExpression(Mockito.anyString());
        waitFor(100);
        verify(mView, times(1)).onError();
    }

    @Test
    public void validExpressionCheck() {
        CalculatorPresenter presenter = new CalculatorPresenter(mCalcRepository);

        when(mCalcRepository.getCalculatedStringFromExpression(Mockito.anyString())).thenReturn("7");

        presenter.attachView(mView);
        presenter.calculateExpression(mValidExpression);
        waitFor(100);
        verify(mView, times(1)).onExpressionCalculatedSuccessfully("7");
    }

    @Test
    public void doubleZeroTest() {
        CalculatorPresenter presenter = new CalculatorPresenter(mCalcRepository);
        String parsedExpression = presenter.parseExpression(Constants.DEFAULT_EXPRESSION, Constants.DEFAULT_EXPRESSION);
        assertNotNull(parsedExpression);
        assertEquals(parsedExpression, Constants.DEFAULT_EXPRESSION);
    }

    @Test
    public void redundantZeroTest() {
        CalculatorPresenter presenter = new CalculatorPresenter(mCalcRepository);
        String parsedExpression = presenter.parseExpression(Constants.DEFAULT_EXPRESSION, "6");
        assertNotNull(parsedExpression);
        assertEquals(parsedExpression, "6");
    }

    @Test
    public void decimalZeroTest() {
        CalculatorPresenter presenter = new CalculatorPresenter(mCalcRepository);
        String parsedExpression = presenter.parseExpression(Constants.DEFAULT_EXPRESSION, ".");
        assertNotNull(parsedExpression);
        assertEquals("0.", parsedExpression);
    }

    @Test
    public void decimalInMiddleOfExpressionTest() {
        CalculatorPresenter presenter = new CalculatorPresenter(mCalcRepository);
        String parsedExpression = presenter.parseExpression("0.", "5");
        assertNotNull(parsedExpression);
        assertEquals("0.5", parsedExpression);
    }

    /**
     * Wait for the specific time using {@link Thread#sleep(long)}
     *
     * @param milliseconds The time we want to wait for in millis
     */
    private void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            fail();
        }
    }
}
