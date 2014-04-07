package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class RQD_RMR extends MappedIndex implements Serializable {

	//public static final RQD_RMR DEFAULT_VALUE = MappedIndexDataProvider.getAllRqdRmr().get(0);

	public RQD_RMR(String shortName, String longDescription, Double value) {
		super();
		setKey(shortName);
        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
        getCategoriesAndValues()[1][0] = longDescription;
		setValue(value);
	}
}
