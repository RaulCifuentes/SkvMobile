package com.metric.skava.calculator.barton.logic;

import com.metric.skava.calculator.barton.model.RockQuality;

public class QBartonOutput {

    private RockQuality rockQuality;

	private Double QBarton;

	private Double rqdOverJn;

	private Double jrOverJa;

	private Double jwOverSRF;

	public Double getQBarton() {
		return QBarton;
	}

	public void setQBarton(Double qBarton) {
		this.QBarton = qBarton;
	}

	public Double getRqdOverJn() {
		return rqdOverJn;
	}

	public void setRqdOverJn(Double rqdOverJn) {
		this.rqdOverJn = rqdOverJn;
	}

	public Double getJrOverJa() {
		return jrOverJa;
	}

	public void setJrOverJa(Double jrOverJa) {
		this.jrOverJa = jrOverJa;
	}

	public Double getJwOverSRF() {
		return jwOverSRF;
	}

	public void setJwOverSRF(Double jwOverSRF) {
		this.jwOverSRF = jwOverSRF;
	}

    public RockQuality getRockQuality() {
        return rockQuality;
    }

    public void setRockQuality(RockQuality rockQuality) {
        this.rockQuality = rockQuality;
    }
}
