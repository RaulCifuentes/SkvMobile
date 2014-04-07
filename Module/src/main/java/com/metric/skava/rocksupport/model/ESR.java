package com.metric.skava.rocksupport.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

/**
 * Created by metricboy on 3/9/14.
 */
public class ESR extends MappedIndex implements Serializable {

    public static final String ESR_CODE = "ESR";

    public ESR (String shortName, String longDescription, Double value){
        super();
        setKey(shortName);
        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
        getCategoriesAndValues()[1][0] = longDescription;
        setValue(value);
    }

}
