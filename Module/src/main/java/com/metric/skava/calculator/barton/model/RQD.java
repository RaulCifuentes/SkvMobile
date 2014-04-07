package com.metric.skava.calculator.barton.model;

import java.io.Serializable;

public class RQD implements Serializable {

    private Integer value;
	private Integer pieces;
	private Integer jv;
	
	public Integer getJv() {
		return jv;
	}


	public void setJv(Integer jv) {
		this.jv = jv;
	}


	public RQD(){
		super();	
	}

    public RQD(Integer value) {
        super();
        this.value = value;
    }

	public RQD(Integer pieces, Integer value) {
		super();	
		this.pieces = pieces;
		this.value = value;
	}



	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}


    @Override
    public String toString() {
        return "RQD{" +
                "value=" + value +
                ", pieces=" + pieces +
                ", jv=" + jv +
                '}';
    }
}
