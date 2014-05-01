package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Aperture extends MappedIndex implements Serializable {

    public static final String INDEX_CODE = "Aperture";

	public Aperture(String code, String key, String shortDescription, String longDescription, Double value) {
        super(code, key, shortDescription, longDescription,value);
	}


    @Override
    public String getGroupName() {
        return null;
    }
}
