package com.metric.skava.app.model;

import com.metric.skava.app.data.SkavaEntity;
import com.metric.skava.rocksupport.model.ExcavationFactors;

/**
 * Created by metricboy on 2/16/14.
 */
public class Tunnel extends SkavaEntity {

    private ExcavationProject project;

    private ExcavationFactors excavationFactors;

    public Tunnel(ExcavationProject project, String id, String name, ExcavationFactors excavationFactors) {
        super(id, name);
        this.project = project;
        this.excavationFactors = excavationFactors;
    }

    public ExcavationProject getProject() {
        return project;
    }

    public void setProject(ExcavationProject project) {
        this.project = project;
    }

    public ExcavationFactors getExcavationFactors() {
        return excavationFactors;
    }

    public void setExcavationFactors(ExcavationFactors excavationFactors) {
        this.excavationFactors = excavationFactors;
    }
}
