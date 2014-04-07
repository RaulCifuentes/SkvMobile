package com.metric.skava.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class BoundariesNumberPicker extends NumberPicker {

	public BoundariesNumberPicker(Context context) {
		super(context);
	}

	public BoundariesNumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		processAttributeSet(attrs);
	}

	public BoundariesNumberPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		processAttributeSet(attrs);
	}

	private void processAttributeSet(AttributeSet attrs) {		
		// This method reads the parameters given in the xml file and sets the
		// properties according to it
		this.setMinValue(attrs.getAttributeIntValue(null, "min", 1));
		this.setMaxValue(attrs.getAttributeIntValue(null, "max", 25));
	}
}
