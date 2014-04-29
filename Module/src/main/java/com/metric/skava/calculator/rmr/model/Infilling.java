package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Infilling extends MappedIndex implements Serializable {

//	public static final ConditionDiscontinuities DEFAULT_VALUE = MappedIndexDataProvider.getAllConditions().get(0);

	public Infilling(String code, String key, String shortDescription, String longDescription, Double value) {
        super(code, key, shortDescription, longDescription,value);
		setKey(key);
//        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
//        getCategoriesAndValues()[1][0] = longDescription;
//		setValue(value);
	}

    @Override
    public String getGroupName() {
        return null;
    }
}
