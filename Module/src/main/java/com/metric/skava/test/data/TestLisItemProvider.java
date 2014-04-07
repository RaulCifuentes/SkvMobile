package com.metric.skava.test.data;

import com.metric.skava.test.model.HeaderImpl;
import com.metric.skava.test.model.NonHeaderImpl;
import com.metric.skava.test.model.TestListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 2/27/14.
 */
public class TestLisItemProvider {

    public static List<TestListItem> getAllTestListItems() {
        List<TestListItem> lista = new ArrayList<TestListItem>();

        lista.add(new HeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new HeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new HeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new NonHeaderImpl());
        lista.add(new NonHeaderImpl());

        return lista;

    }


}
