package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.model.Group;
import com.metric.skava.calculator.model.Index;
import com.metric.skava.data.dao.LocalIndexDAO;
import com.metric.skava.data.dao.RemoteGroupDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrCategoriesDropboxTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class GroupDAODropboxImpl extends DropBoxBaseDAO implements RemoteGroupDAO {

    private RmrCategoriesDropboxTable mGroupsTable;


    public GroupDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mGroupsTable = new RmrCategoriesDropboxTable(getDatastore());

    }


    @Override
    public List<Group> getAllGroups() throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            List<Group> listGroups = new ArrayList<Group>();
            List<DbxRecord> groupList = mGroupsTable.findAll();
            for (DbxRecord currentGroupRecord : groupList) {
                String codigo = currentGroupRecord.getString("CategoryId");
                String key = currentGroupRecord.getString("CategoryCode");
                String nombre = currentGroupRecord.getString("CategoryName");

                Group newGroup = new Group(codigo, key, nombre);
                LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
                String indexCode = currentGroupRecord.getString("FkParameterId");
                Index index = indexDAO.getIndexByCode(indexCode);
                newGroup.setIndex(index);
                listGroups.add(newGroup);
            }
            Collections.sort(listGroups, new Comparator<Group>() {
                @Override
                public int compare(Group lhs, Group rhs) {
                    return lhs.getIndex().getKey().compareTo(rhs.getIndex().getKey());
                }
            });
            return listGroups;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }


}
