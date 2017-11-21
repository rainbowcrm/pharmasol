package com.primus.common;

import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.context.ISettings;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DefaultSettings  implements ISettings {

    @Override
    public SimpleDateFormat getDateFormat(IRadsContext iRadsContext) {
        return null;
    }

    @Override
    public NumberFormat getCurrencyFormat(IRadsContext iRadsContext) {
        return null;
    }

    @Override
    public String getCurrencySymbol(IRadsContext iRadsContext) {
        return "$";
    }

    @Override
    public Locale getLocale(IRadsContext iRadsContext) {
        return Locale.US;
    }

    @Override
    public ISettings getDefaultInstance() {
        return this;
    }
}
