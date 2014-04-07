package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class SRF extends MappedIndex implements Serializable {

    public static final int a = 10;
    public static final int b = 20;
    public static final int c = 30;
    public static final int d = 40;

	public SRF(String shortName, String longDescription, Double value) {
		super();
		setKey(shortName);
        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
        getCategoriesAndValues()[1][0] = longDescription;
		setValue(value);
	}

    public String getGroupTypeName(){
        String text = null;
        switch (getGroupType()){
            case SRF.a:
                text = "a.";
                break;
            case SRF.b:
                text = "b.";
                break;
            case SRF.c:
                text = "c.";
                break;
            case SRF.d:
                text = "d.";
                break;
        }
        return text;
    }
}
