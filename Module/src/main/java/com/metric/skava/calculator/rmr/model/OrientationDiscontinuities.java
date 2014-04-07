package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class OrientationDiscontinuities extends MappedIndex implements Serializable {

    //This values will be wraped on groupType property, at least meanwhile Group and Index is modeled
    public static final int TUNNEL_MINES = 10;
    public static final int FOUNDATIONS = 20;
    public static final int SLOPES = 30;

    public OrientationDiscontinuities(String shortName, String longDescription, Double value) {
        super();
        setKey(shortName);
        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
        getCategoriesAndValues()[1][0] = longDescription;
        setValue(value);
    }

    public String getGroupTypeName(){
        String text = null;
        switch (getGroupType()){
            case OrientationDiscontinuities.TUNNEL_MINES:
                text = "Tunnels";
                break;
            case OrientationDiscontinuities.FOUNDATIONS:
                text = "Foundations";
                break;
            case OrientationDiscontinuities.SLOPES:
                text = "Slopes";
                break;
        }
        return text;
    }
}
