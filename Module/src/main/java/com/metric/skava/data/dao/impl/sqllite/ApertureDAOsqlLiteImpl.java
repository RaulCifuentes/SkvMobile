package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.data.dao.LocalApertureDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.ApertureTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class ApertureDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Aperture> implements LocalApertureDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public ApertureDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    public Aperture getAperture(String groupCode, String code) throws DAOException {
        String[] names = new String[]{ApertureTable.INDEX_CODE_COLUMN, ApertureTable.GROUP_CODE_COLUMN, ApertureTable.CODE_COLUMN};
        String[] values = new String[]{Aperture.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(ApertureTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Aperture> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + Aperture.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + Aperture.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public Aperture getApertureByUniqueCode(String apertureCode) throws DAOException {
        String[] names = new String[]{ApertureTable.INDEX_CODE_COLUMN, ApertureTable.CODE_COLUMN};
        String[] values = new String[]{Aperture.INDEX_CODE, apertureCode};
        Cursor cursor = getRecordsFilteredByColumns(ApertureTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<Aperture> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Aperture Code : " + apertureCode + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + apertureCode + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    protected List<Aperture> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Aperture> list = new ArrayList<Aperture>();
        while (cursor.moveToNext()) {
            Aperture newInstance = mappedIndexInstaceBuilder.buildApertureFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Aperture> getAllApertures() throws DAOException {
        Cursor cursor = getAllRecords(ApertureTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Aperture> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }

    @Override
    protected void savePersistentEntity(String tableName, Aperture newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = Aperture.INDEX_CODE;

        String[] colNames = {ApertureTable.INDEX_CODE_COLUMN,
                ApertureTable.GROUP_CODE_COLUMN,
                ApertureTable.CODE_COLUMN,
                ApertureTable.KEY_COLUMN,
                ApertureTable.SHORT_DESCRIPTION_COLUMN,
                ApertureTable.DESCRIPTION_COLUMN,
                ApertureTable.VALUE_COLUMN};

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
    public void saveAperture(Aperture aperture) throws DAOException {
        savePersistentEntity(ApertureTable.MAPPED_INDEX_DATABASE_TABLE, aperture);
    }


    @Override
    public boolean deleteAperture(String indexCode, String groupCode, String code) {
        String[] colNames = {ApertureTable.INDEX_CODE_COLUMN,
                ApertureTable.GROUP_CODE_COLUMN,
                ApertureTable.CODE_COLUMN};
        Object[] colValues = {
                indexCode,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(ApertureTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllApertures() {
        return deleteAllPersistentEntities(ApertureTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
