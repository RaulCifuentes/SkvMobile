package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityShape;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface LocalDiscontinuityShapeDAO {

    public DiscontinuityShape getDiscontinuityShapeByCode(String code) throws DAOException;

    public List<DiscontinuityShape> getAllDiscontinuityShapes() throws DAOException;

    public void saveDiscontinuityShape(DiscontinuityShape newEntity) throws DAOException;

    public boolean deleteDiscontinuityShape(String code);

    public int deleteAllDiscontinuityShapes();

}

