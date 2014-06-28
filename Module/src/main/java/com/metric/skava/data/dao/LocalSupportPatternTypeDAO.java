package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.SupportPatternType;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalSupportPatternTypeDAO {

    public List<SupportPatternType> getAllSupportPatternTypes(SupportPatternType.Group group) throws DAOException;

    public SupportPatternType getSupportPatternTypeByUniqueCode(String code) throws DAOException;

    public void saveSupportPatternType(SupportPatternType aperture) throws DAOException;

    public boolean deleteSupportPatternType(String code);

    public int deleteAllSupportPatternTypes();

    public Long countSupportPatternTypes();
}
