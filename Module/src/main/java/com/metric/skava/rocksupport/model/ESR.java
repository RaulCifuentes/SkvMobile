package com.metric.skava.rocksupport.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

/**
 * Created by metricboy on 3/9/14.
 */
public class ESR extends MappedIndex implements Serializable {

    public static final String INDEX_CODE = "ESR";

    private Group group;

    public enum Group {
        i("Circular Sections"),
        ii("Rectangular Sections");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }


    public ESR(Group group, String code, String key, String shortDescription, String longDescription, Double value) {
        super(code, key, shortDescription, longDescription,value);
        setGroup(group);
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String getGroupName() {
        //Only works when Dropbox model includes Groups for the ESR index
        return null;
    }

}
