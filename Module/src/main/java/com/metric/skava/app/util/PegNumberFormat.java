package com.metric.skava.app.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Implements formatting rules for Peg Numbers
 */
public class PegNumberFormat extends DecimalFormat {

    public PegNumberFormat() {

        DecimalFormatSymbols custom = new DecimalFormatSymbols();
        custom.setGroupingSeparator('+');
        custom.setDecimalSeparator(',');
        setMinimumFractionDigits(2);
        setMaximumFractionDigits(2);

        setDecimalFormatSymbols(custom);
    }
}