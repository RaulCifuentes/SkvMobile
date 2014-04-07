package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Ja extends MappedIndex implements Serializable {

    public static final int a = 10;
    public static final int b = 20;
    public static final int c = 30;



	public Ja(String shortName, String longDescription, Double value) {
		super();
		setKey(shortName);
        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
        getCategoriesAndValues()[1][0] = longDescription;
		setValue(value);
	}

    public String getGroupTypeName(){
        String text = null;
        switch (getGroupType()){
            case Ja.a:
                text = "a.";
                break;
            case Ja.b:
                text = "b.";
                break;
            case Ja.c:
                text = "c.";
                break;
        }
        return text;
    }
}
