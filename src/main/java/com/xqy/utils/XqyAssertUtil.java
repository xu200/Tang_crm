package com.xqy.utils;

import com.xqy.exceptions.XqyParamsException;

public class XqyAssertUtil {
    public static void isTrue(Boolean flag, String msg) {
        if (flag) {
            throw new XqyParamsException(msg);
        }
    }
}
