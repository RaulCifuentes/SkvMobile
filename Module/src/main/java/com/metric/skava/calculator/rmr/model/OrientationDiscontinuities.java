package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class OrientationDiscontinuities extends MappedIndex implements Serializable {

    //This values will be wraped on groupType property, at least meanwhile Group and Index is modeled
//    public static final int TUNNEL_MINES = 10;
//    public static final int FOUNDATIONS = 20;
//    public static final int SLOPES = 30;

    private Group group;

    public enum Group {
        TUNNELS_MINES("Tunnels & Mines"),
        FOUNDATIONS("Foundations"),
        SLOPES("Slopes");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    public OrientationDiscontinuities(Group group, String code, String key, String shortDescription, String longDescription, Double value) {
        super(code, key, shortDescription, longDescription,value);
        setGroup(group);
//        setKey(key);
//        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
//        getCategoriesAndValues()[1][0] = longDescription;
//        setValue(value);
    }

    //    public String getGroupTypeName(){
//        String text = null;
//        switch (getGroupType()){
//            case OrientationDiscontinuities.TUNNEL_MINES:
//                text = "Tunnels";
//                break;
//            case OrientationDiscontinuities.FOUNDATIONS:
//                text = "Foundations";
//                break;
//            case OrientationDiscontinuities.SLOPES:
//                text = "Slopes";
//                break;
//        }
//        return text;
//    }

    @Override
    public String getGroupName() {
        return this.group.name();
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
