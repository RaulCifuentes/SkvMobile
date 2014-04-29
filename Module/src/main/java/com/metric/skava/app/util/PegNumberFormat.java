package com.metric.skava.app.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Implements formatting rules for Peg Numbers
 */
public class PegNumberFormat extends DecimalFormat {
    public PegNumberFormat() {
        DecimalFormatSymbols custom=new DecimalFormatSymbols();
        custom.setGroupingSeparator('+');
        custom.setDecimalSeparator(',');
        this.setMaximumFractionDigits(2);
        this.setDecimalFormatSymbols(custom);
    }
}