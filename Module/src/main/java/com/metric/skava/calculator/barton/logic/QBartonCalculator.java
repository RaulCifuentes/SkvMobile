package com.metric.skava.calculator.barton.logic;

public class QBartonCalculator {

	public static QBartonOutput calculate(QBartonInput input) {

		QBartonOutput result = new QBartonOutput();
		
		Double rqdOverJn = null, jrOverJa = null, jwOverSRF = null;
		
		if (input != null) {

			if (input.getRqd() != null && input.getJn() != null) {
				rqdOverJn = (double) (input.getRqd() / input.getJn());
				result.setRqdOverJn(rqdOverJn);
			} 
			if (input.getJr() != null && input.getJa() != null) {
				jrOverJa = (double) (input.getJr() / input.getJa());
				result.setJrOverJa(jrOverJa);
			} 
			if (input.getJw() != null && input.getSrf() != null) {
				jwOverSRF = (double) (input.getJw() / input.getSrf());
				result.setJwOverSRF(jwOverSRF);
			} 
			
			Double qBarton = rqdOverJn * jrOverJa * jwOverSRF;
			result.setQBarton(qBarton);
		}
		return result;
	}

}
