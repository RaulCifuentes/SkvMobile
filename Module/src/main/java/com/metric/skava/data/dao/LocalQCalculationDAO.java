package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.data.dao.exception.DAOException;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalQCalculationDAO {

    public Q_Calculation getQCalculation(String assessmentCode) throws DAOException;

    public void saveQCalculation(String assessmentCode, Q_Calculation newEntity) throws DAOException;

    public boolean deleteQCalculation(String assessmentCode);

    public int deleteAllQCalculations() throws DAOException;

}
