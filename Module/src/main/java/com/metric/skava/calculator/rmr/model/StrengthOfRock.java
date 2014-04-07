package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

/**
 * Created by metricboy on 2/16/14.
 */
public class StrengthOfRock extends MappedIndex {

    public static String POINT_LOAD_KEY = "Point-load strenght index";
    public static String UNIAXIAL_KEY = "Uniaxial compression strenght";

    //public static final StrengthOfRock DEFAULT_VALUE = MappedIndexDataProvider.getAllStrenghts().get(0);

    public StrengthOfRock(String shortName, String pointLoad, String uniaxial, Double ratingValue) {
        super();
        setKey(shortName);
        getCategoriesAndValues()[0][0] = StrengthOfRock.POINT_LOAD_KEY;
        getCategoriesAndValues()[1][0] = pointLoad;
        getCategoriesAndValues()[0][1] = StrengthOfRock.UNIAXIAL_KEY;
        getCategoriesAndValues()[1][1] = uniaxial;
        setValue(ratingValue);
    }

}
