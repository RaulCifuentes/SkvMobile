package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalApertureDAO {

    public List<Aperture> getAllApertures() throws DAOException;

    public Aperture getAperture(String groupCode, String code) throws DAOException;

    public Aperture getApertureByUniqueCode(String apertureCode) throws DAOException;

    public void saveAperture(Aperture aperture) throws DAOException;

    public boolean deleteAperture(String indexCode, String groupCode, String code);

    public int deleteAllApertures();

    public Long countApertures();
}
