package com.metric.skava.data.dao;

import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface RemoteExcavationSectionDAO {

    public List<ExcavationSection> getAllExcavationSections() throws DAOException;

}

