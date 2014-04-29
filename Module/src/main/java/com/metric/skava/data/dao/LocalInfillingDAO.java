package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalInfillingDAO {

    public List<Infilling> getAllInfillings() throws DAOException;

    public Infilling getInfilling(String indexCode, String groupCode, String code) throws DAOException;

    public void saveInfilling(Infilling infilling) throws DAOException;

    public boolean deleteInfilling(String indexCode, String groupCode, String code);

    public int deleteAllInfillings();




}
