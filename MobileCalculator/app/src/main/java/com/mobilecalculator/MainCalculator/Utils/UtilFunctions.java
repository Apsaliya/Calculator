package com.mobilecalculator.MainCalculator.Utils;

import android.text.TextUtils;

/**
 * Created by ankit on 10/12/17.
 */

public class UtilFunctions {

    public static boolean isOperator(String character) {
        if (!TextUtils.isEmpty(character)) {
            if (character.equalsIgnoreCase("+") ||
                    character.equalsIgnoreCase("-") ||
                    character.equalsIgnoreCase("*") ||
                    character.equalsIgnoreCase("^") ||
                    character.equalsIgnoreCase("%") ||
                    character.equalsIgnoreCase("/") ||
                    character.equalsIgnoreCase("/")) {
                return true;
            }
        }
        return false;
    }
}
