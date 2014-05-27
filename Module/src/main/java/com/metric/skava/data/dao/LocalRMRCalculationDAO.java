package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.data.dao.exception.DAOException;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalRMRCalculationDAO {

    public RMR_Calculation getRMRCalculation(String assessmentCode) throws DAOException;

    public void saveRMRCalculation(String assessmentCode, RMR_Calculation newEntity) throws DAOException;

    public boolean deleteRMRCalculation(String assessmentCode) throws DAOException;

    public int deleteAllRMRCalculations() throws DAOException;


}
