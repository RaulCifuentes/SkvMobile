package com.metric.skava.test.model;

/**
 * Created by metricboy on 2/27/14.
 */
public interface TestListItem {

    public enum RowType {
        // Here we have two items types, you can have as many as you like though
        LIST_ITEM, HEADER_ITEM
    }

    public RowType getViewType();

    public String getData();



}
