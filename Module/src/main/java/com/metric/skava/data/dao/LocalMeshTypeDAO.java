package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.MeshType;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalMeshTypeDAO {

    public List<MeshType> getAllMeshTypes() throws DAOException;

    public MeshType getMeshTypeByCode(String code) throws DAOException;

    public void saveMeshType(MeshType newEntity) throws DAOException;

    public boolean deleteMeshType(String code);

    public int deleteAllMeshTypes();


}
