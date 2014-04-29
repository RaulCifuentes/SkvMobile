package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

/**
 * Created by metricboy on 2/16/14.
 */
public class Groundwater extends MappedIndex {

//    public static final int INFLOW_LENGHT = 10;
//    public static final int JOINT_PRESS_PRINCIPAL = 20;
//    public static final int GENERAL_CONDITIONS = 30;


//    public Groundwater(String shortName, String inflow, String jointPress, String general,  Double ratingValue) {
//        super();
//        setKey(key);
//        getCategoriesAndValues()[0][0] = Groundwater.INFLOW_LENGHT;
//        getCategoriesAndValues()[1][0] = inflow;
//        getCategoriesAndValues()[0][1] = Groundwater.JOINT_PRESS_PRINCIPAL;
//        getCategoriesAndValues()[1][1] = jointPress;
//        getCategoriesAndValues()[0][2] = Groundwater.GENERAL_CONDITIONS;
//        getCategoriesAndValues()[1][2] = general;
//        setValue(ratingValue);
//    }

    private Group group;

    public enum Group {
        INFLOW_LENGHT("Inflow per 10 m tunnel length (l/m)"),
        JOINT_PRESS_PRINCIPAL("Joint water press / Major principal(σ)"),
        GENERAL_CONDITIONS("General Conditions");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    public Groundwater(Group group, String code, String key, String shortDescription, String longDescription, Double ratingValue) {
        super(code, key, shortDescription, longDescription, ratingValue);
        setGroup(group);
//        setKey(key);
//        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
//        getCategoriesAndValues()[1][0] = longDescription;
//        setValue(ratingValue);
    }

//    public String getGroupTypeName(){
//        String text = null;
//        switch (getGroupType()){
//            case OrientationDiscontinuities.TUNNEL_MINES:
//                text = "Inflow per 10 m tunnel length (l/m)";
//                break;
//            case OrientationDiscontinuities.FOUNDATIONS:
//                text = "Joint water press / Major principal(σ)";
//                break;
//            case OrientationDiscontinuities.SLOPES:
//                text = "General Conditions";
//                break;
//        }
//        return text;
//    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String getGroupName() {
        return this.group.name();
    }
}
