package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Spacing extends MappedIndex implements Serializable {

//	public static final Spacing DEFAULT_VALUE = MappedIndexDataProvider.getAllSpacings().get(0);

	public Spacing(String shortName, String longDescription, Double value) {
		super();
		setKey(shortName);
        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
        getCategoriesAndValues()[1][0] = longDescription;
		setValue(value);
	}
}
