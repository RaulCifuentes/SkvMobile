package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalSpacingDAO {


    public List<Spacing> getAllSpacings() throws DAOException;

    public Spacing getSpacing(String groupCode, String code) throws DAOException;

    public Spacing getSpacingByUniqueCode(String spacingCode) throws DAOException;

    public void saveSpacing(Spacing spacing) throws DAOException;

    public boolean deleteSpacing(String groupCode, String code);

    public int deleteAllSpacings();

    public Long countSpacings();
}
