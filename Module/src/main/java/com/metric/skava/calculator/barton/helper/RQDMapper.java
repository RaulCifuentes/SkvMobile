package com.metric.skava.calculator.barton.helper;

import com.metric.skava.calculator.barton.model.RQD;


public class RQDMapper {

	private static RQDMapper mInstance; 
	
	private RQDMapper(){
	}
	
	public static RQDMapper getInstance () {
		if (mInstance == null){
			mInstance = new RQDMapper();
		}
		return mInstance;
	}	
	
	
	public RQD mapCorePiecesToRQD(Integer pieces){
		if ((0 < pieces ) && (pieces < 22)) {
			RQD rQD = new RQD();
			rQD.setPieces(pieces);
			rQD.setValue((pieces - 1) * 5);			
			return rQD;
		}
		return null;		
	}
	
	public RQD mapJvToRQD(Integer jv){
		if ((4 <= jv ) && (jv <= 44)) {
			RQD rQD = new RQD();
			rQD.setJv(jv);
			Double value = 110 - 2.5 * jv;
			if (value < 10){
				value = (double) 10;
			}	
			rQD.setValue(value.intValue());			
			return rQD;
		}
		return null;		
	}
	
}
