package com.metric.skava.calculator.data.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorDummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<CalculatorDummyItem> ITEMS = new ArrayList<CalculatorDummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, CalculatorDummyItem> ITEM_MAP = new HashMap<String, CalculatorDummyItem>();

    static {
        // Add 3 sample items.
        addItem(new CalculatorDummyItem("Q", "Q Barton"));
        addItem(new CalculatorDummyItem("RMR", "RMR Bienawski"));
    }

    private static void addItem(CalculatorDummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class CalculatorDummyItem {
        public String id;
        public String content;

        public CalculatorDummyItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
