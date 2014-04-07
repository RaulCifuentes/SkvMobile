package com.metric.skava.test.model;

/**
 * Created by metricboy on 2/27/14.
 */
public class NonHeaderImpl implements TestListItem {


    @Override
    public RowType getViewType() {
        return RowType.LIST_ITEM;
    }

    @Override
    public String getData() {
        return "Non Header Text";
    }
}
