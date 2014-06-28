package com.metric.skava.data.dao;

import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface LocalExcavationSectionDAO {

    public ExcavationSection getExcavationSectionByCode(String code) throws DAOException;

    public List<ExcavationSection> getAllExcavationSections() throws DAOException;

    public void saveExcavationSection(ExcavationSection newEntity) throws DAOException;

    public boolean deleteExcavationSection(String code);

    public int deleteAllExcavationSections();

    public Long countExcavationSections();
}

