package com.metric.skava.data.dao;

import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rocksupport.model.ExcavationFactors;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalExcavationFactorDAO {


    public List<ExcavationFactors> getAllExcavationFactors() throws DAOException;

    public ExcavationFactors getExcavationFactorsByTunnel(Tunnel tunnelCode) throws DAOException;

    public void saveExcavationFactors(String tunnelCode, ExcavationFactors newEntity) throws DAOException;

    public boolean deleteExcavationFactorsByTunnel(Tunnel tunnel);

    public int deleteAllExcavationFactors() throws DAOException;

}
