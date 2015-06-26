package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Jr extends MappedIndex implements Serializable {

    public static final String INDEX_CODE = "Jr";

    private Group group;

    public enum Group {
//        a("a. Rock-wall contact"),
//        b("b. Rock-wall contact before 10 cm of shear movement"),
//        c("c. No rock-wall contact when sheared");

        a("a. Contacto entre las paredes"),
        b("b. Contacto entre las paredes ante un desplazamiento de cizalla de 10 cm"),
        c("c. No existe contacto entre las paredes durante el desplazamiento de cizalla");

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
	}


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
