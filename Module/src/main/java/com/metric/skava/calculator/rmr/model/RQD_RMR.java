package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.barton.model.RQD;
import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RQD_RMR extends MappedIndex implements Serializable {

	//public static final RQD_RMR DEFAULT_VALUE = MappedIndexDataProvider.getAllRqdRmr().get(0);

    static ArrayList<RQD_RMR> commonValuesTable;
    static {
        commonValuesTable = new ArrayList<RQD_RMR>();
        commonValuesTable.add(new RQD_RMR(
                null,
                "A",
                "< 25 %",
                "< 25 %",
                3d));
        commonValuesTable.add(new RQD_RMR(
                null,
                "B",
                "25 % - 50 %",
                "25 % - 50 %",
                8d));
        commonValuesTable.add(new RQD_RMR(
                null,
                "C",
                "50 % - 75 %",
                "50 % - 75 %",
                13d));
        commonValuesTable.add(new RQD_RMR(
                null,
                "D",
                "75 % - 90 %",
                "75 % - 90 %",
                17d));
        commonValuesTable.add(new RQD_RMR(
                null,
                "E",
                "90 % - 100 %",
                "90 % - 100 %",
                20d));
    }

        public RQD_RMR(String code, String key, String shortDescription, String longDescription, Double value) {
        super(code, key, shortDescription, longDescription, value);
//		setKey(key);
//        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
//        getCategoriesAndValues()[1][0] = longDescription;
//		setValue(value);
	}

    private RQD wrappedRqd;

    public RQD getWrappedRqd() {
        return wrappedRqd;
    }

    public void setWrappedRqd(RQD wrappedRqd) {
        this.wrappedRqd = wrappedRqd;
    }

    public static RQD_RMR findWrapper(RQD rqd){
        for (RQD_RMR rqd_rmr : commonValuesTable) {
            if (rqd_rmr.getValue().intValue() == rqd.getValue()) {
                return rqd_rmr;
            }
        }
        return null;
    }

    public static List<RQD_RMR> getAllRqdRmr() {
        return commonValuesTable;
    }

    @Override
    public String getGroupName() {
        return null;
    }
}
