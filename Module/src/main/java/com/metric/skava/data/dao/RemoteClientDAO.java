package com.metric.skava.data.dao;

import com.metric.skava.app.model.Client;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteClientDAO {

    public List<Client> getAllClients() throws DAOException;

}
