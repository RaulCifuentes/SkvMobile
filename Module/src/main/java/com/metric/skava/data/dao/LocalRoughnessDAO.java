package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalRoughnessDAO {


    public List<Roughness> getAllRoughnesses() throws DAOException;

    public Roughness getRoughness(String indexCode, String groupCode, String code) throws DAOException;

    public void saveRoughness(Roughness roughness) throws DAOException;

    public boolean deleteRoughness(String indexCode, String groupCode, String code);

    public int deleteAllRoughnesses();


}
