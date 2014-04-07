package com.metric.skava.test.model;

/**
 * Created by metricboy on 2/27/14.
 */
public class HeaderImpl implements TestListItem {

    @Override
    public RowType getViewType() {
        return RowType.HEADER_ITEM;
    }

    @Override
    public String getData() {
        return "Header Text";
    }
}
