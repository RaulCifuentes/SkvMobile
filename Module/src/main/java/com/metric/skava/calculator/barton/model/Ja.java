package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Ja extends MappedIndex implements Serializable {

//    public static final int a = 10;
//    public static final int b = 20;
//    public static final int c = 30;

    private Group group;

    public enum Group {
        a("Rock-wall contact (no mineral fillings, only coatings)"),
        b("Rock-wall contact before 10 cm shear (thin mineral fillings)"),
        c("No rock-wall contact when sheared (thick mineral fillings)");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }


	public Ja(Group group, String code, String key, String shortDescription, String longDescription, Double value) {
        super(code, key, shortDescription, longDescription,value);
        setGroup(group);
//		setKey(key);
//        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
//        getCategoriesAndValues()[1][0] = longDescription;
//		setValue(value);
	}

//    public String getGroupTypeName(){
//        String text = null;
//        switch (getGroupType()){
//            case Ja.a:
//                text = "a.";
//                break;
//            case Ja.b:
//                text = "b.";
//                break;
//            case Ja.c:
//                text = "c.";
//                break;
//        }
//        return text;
//    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String getGroupName() {
        return this.group.name();
    }

}
