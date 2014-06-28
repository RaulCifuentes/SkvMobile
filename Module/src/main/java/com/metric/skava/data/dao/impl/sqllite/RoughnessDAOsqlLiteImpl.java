package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.data.dao.LocalRoughnessDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.RoughnessTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class RoughnessDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Roughness> implements LocalRoughnessDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public RoughnessDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }



    @Override
    protected List<Roughness> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Roughness> list = new ArrayList<Roughness>();
        while (cursor.moveToNext()) {
            Roughness newInstance = mappedIndexInstaceBuilder.buildRoughnessFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }

    @Override
    public Roughness getRoughness( String groupCode, String code) throws DAOException {
        String[] names = new String[]{RoughnessTable.INDEX_CODE_COLUMN, RoughnessTable.GROUP_CODE_COLUMN, RoughnessTable.CODE_COLUMN};
        String[] values = new String[]{Roughness.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(RoughnessTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Roughness> list = assambleRoughnesss(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + Roughness.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + Roughness.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public Roughness getRoughnessByUniqueCode(String roughnessCode) throws DAOException {
        String[] names = new String[]{RoughnessTable.INDEX_CODE_COLUMN, RoughnessTable.CODE_COLUMN};
        String[] values = new String[]{Roughness.INDEX_CODE, roughnessCode};
        Cursor cursor = getRecordsFilteredByColumns(RoughnessTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<Roughness> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Roughness Code : " + roughnessCode + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + roughnessCode + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Roughness> assambleRoughnesss(Cursor cursor) throws DAOException {
        List<Roughness> list = new ArrayList<Roughness>();
        while (cursor.moveToNext()) {
            Roughness newInstance = mappedIndexInstaceBuilder.buildRoughnessFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Roughness> getAllRoughnesses() throws DAOException {
        Cursor cursor = getAllRecords(RoughnessTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Roughness> list = assambleRoughnesss(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveRoughness(Roughness newRoughness) throws DAOException {
        savePersistentEntity(RoughnessTable.MAPPED_INDEX_DATABASE_TABLE, newRoughness);
    }

    @Override
    protected void savePersistentEntity(String tableName, Roughness newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = Roughness.INDEX_CODE;

        String[] colNames = {RoughnessTable.INDEX_CODE_COLUMN,
                RoughnessTable.GROUP_CODE_COLUMN,
                RoughnessTable.CODE_COLUMN,
                RoughnessTable.KEY_COLUMN,
                RoughnessTable.SHORT_DESCRIPTION_COLUMN,
                RoughnessTable.DESCRIPTION_COLUMN,
                RoughnessTable.VALUE_COLUMN};

        Object[] colValues = {
                indexCode,
                newSkavaEntity.getGroupName(),
                newSkavaEntity.getCode(),
                newSkavaEntity.getKey(),
                newSkavaEntity.getShortDescription(),
                newSkavaEntity.getDescription(),
                newSkavaEntity.getValue()
        };
        saveRecord(tableName, colNames, colValues);
    }

    @Override
    public boolean deleteRoughness(String groupCode, String code) {
        String[] colNames = {RoughnessTable.INDEX_CODE_COLUMN,
                RoughnessTable.GROUP_CODE_COLUMN,
                RoughnessTable.CODE_COLUMN};
        Object[] colValues = {
                Roughness.INDEX_CODE,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(RoughnessTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllRoughnesses() {
        return deleteAllPersistentEntities(RoughnessTable.MAPPED_INDEX_DATABASE_TABLE);
    }

    @Override
    public Long countRoughnesses() {
        return countRecords(RoughnessTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
