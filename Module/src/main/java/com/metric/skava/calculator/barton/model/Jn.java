package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Jn extends MappedIndex implements Serializable {


    public static final String INDEX_CODE = "Jn";

    public Jn (String code,String key, String shortDescription, String longDescription, Double value){
        super(code, key, shortDescription, longDescription,value);
	}


    @Override
    public String getGroupName() {
        return null;
    }
}
