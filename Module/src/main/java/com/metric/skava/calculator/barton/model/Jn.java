package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Jn extends MappedIndex implements Serializable {

//	public static final Jn DEFAULT_VALUE = MappedIndexDataProvider.getAllJn().get(0);

	public Jn (String shortName, String longDescription, Double value){
		super();		
		setKey(shortName);
		getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
        getCategoriesAndValues()[1][0] = longDescription;
        setValue(value);
	}
	
}
