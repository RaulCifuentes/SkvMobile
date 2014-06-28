package com.metric.skava.data.dao;

import com.metric.skava.app.model.Client;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalClientDAO {


    public Client getClientByCode(String code) throws DAOException;

    public List<Client> getAllClients() throws DAOException;

    public void saveClient(Client newEntity) throws DAOException;

    public boolean deleteClient(String code);

    public int deleteAllClients() throws DAOException;

    public Long countClients();
}
