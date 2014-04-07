package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

/**
 * Created by metricboy on 2/16/14.
 */
public class Groundwater extends MappedIndex {

    public static String INFLOW_LENGHT = "Inflow per 10 m tunnel length (l/m)";
    public static String JOINT_PRESS_PRINCIPAL = "Joint water press / Major principal(σ)";
    public static String GENERAL_CONDITIONS = "General Conditions";

//    public static final Groundwater DEFAULT_VALUE = MappedIndexDataProvider.getAllGroundwaters().get(0);

    public Groundwater(String shortName, String inflow, String jointPress, String general,  Double ratingValue) {
        super();
        setKey(shortName);
        getCategoriesAndValues()[0][0] = Groundwater.INFLOW_LENGHT;
        getCategoriesAndValues()[1][0] = inflow;
        getCategoriesAndValues()[0][1] = Groundwater.JOINT_PRESS_PRINCIPAL;
        getCategoriesAndValues()[1][1] = jointPress;
        getCategoriesAndValues()[0][2] = Groundwater.GENERAL_CONDITIONS;
        getCategoriesAndValues()[1][2] = general;
        setValue(ratingValue);
    }



}
