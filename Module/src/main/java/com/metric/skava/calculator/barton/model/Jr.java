package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Jr extends MappedIndex implements Serializable {

    //This values will be wraped on groupType property, at least meanwhile Group and Index is modeled
//    public static final int a = 10;
//    public static final int b = 20;
//    public static final int c = 30;

    private Group group;

    public enum Group {
        a("Rock-wall contact"),
        b("Rock-wall contact before 10 cm shear"),
        c("No rock-wall contact when sheared");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

	public Jr(Group group, String code, String key, String shortDescription, String longDescription, Double value) {
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
//            case Jr.a:
//                text = "a.";
//                break;
//            case Jr.b:
//                text = "b.";
//                break;
//            case Jr.c:
//                text = "c.";
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
