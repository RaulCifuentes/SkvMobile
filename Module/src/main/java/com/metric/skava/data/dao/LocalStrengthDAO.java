package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalStrengthDAO {


    public List<StrengthOfRock> getAllStrengths(StrengthOfRock.Group group) throws DAOException;

    public StrengthOfRock getStrength(String groupCode, String code) throws DAOException;

    public StrengthOfRock getStrengthByUniqueCode(String code) throws DAOException;

    public void saveStrength(StrengthOfRock strength) throws DAOException;

    public boolean deleteStrength(String indexCode, String groupCode, String code);

    public int deleteAllStrengths();


}
