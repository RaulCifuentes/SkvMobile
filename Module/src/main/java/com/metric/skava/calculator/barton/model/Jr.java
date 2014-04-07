package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Jr extends MappedIndex implements Serializable {

    //This values will be wraped on groupType property, at least meanwhile Group and Index is modeled
    public static final int a = 10;
    public static final int b = 20;
    public static final int c = 30;

	public Jr(String shortName, String longDescription, Double value) {
		super();
		setKey(shortName);
        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
        getCategoriesAndValues()[1][0] = longDescription;
		setValue(value);
	}

    public String getGroupTypeName(){
        String text = null;
        switch (getGroupType()){
            case Jr.a:
                text = "a.";
                break;
            case Jr.b:
                text = "b.";
                break;
            case Jr.c:
                text = "c.";
                break;
        }
        return text;
    }


}
