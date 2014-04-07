package com.metric.skava.dummy.model;

public class DummySpinnerItem {
	 
    private String title;
    private int icon;
     
    public DummySpinnerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }
     
    public String getTitle(){
        return this.title;      
    }
     
    public int getIcon(){
        return this.icon;
    }
}
