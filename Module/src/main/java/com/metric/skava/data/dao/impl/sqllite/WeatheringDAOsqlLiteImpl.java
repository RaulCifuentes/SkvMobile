package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.LocalWeatheringDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.WeatheringTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class WeatheringDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Weathering> implements LocalWeatheringDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public WeatheringDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    public Weathering getWeathering(String groupCode, String code) throws DAOException {
        String[] names = new String[]{WeatheringTable.INDEX_CODE_COLUMN, WeatheringTable.GROUP_CODE_COLUMN, WeatheringTable.CODE_COLUMN};
        String[] values = new String[]{Weathering.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(WeatheringTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<Weathering> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + Weathering.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
                     throw new DAOException("Multiple records for same code. [Index Code : " + Weathering.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
                 }
        cursor.close();
        return list.get(0);
    }

    @Override
    public Weathering getWeatheringByUniqueCode(String weatheringCode) throws DAOException {
        String[] names = new String[]{WeatheringTable.INDEX_CODE_COLUMN, WeatheringTable.CODE_COLUMN};
        String[] values = new String[]{Weathering.INDEX_CODE, weatheringCode};
        Cursor cursor = getRecordsFilteredByColumns(WeatheringTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<Weathering> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Weathering Code : " + weatheringCode + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + weatheringCode + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    protected List<Weathering> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Weathering> list = new ArrayList<Weathering>();
        while (cursor.moveToNext()) {
            Weathering newInstance = mappedIndexInstaceBuilder.buildWeatheringFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Weathering> getAllWeatherings() throws DAOException {
        Cursor cursor = getAllRecords(WeatheringTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Weathering> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveWeathering(Weathering newWeathering) throws DAOException {
        savePersistentEntity(WeatheringTable.MAPPED_INDEX_DATABASE_TABLE, newWeathering);
    }

    @Override
    protected void savePersistentEntity(String tableName, Weathering newSkavaEntity) throws DAOException {
//  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = Weathering.INDEX_CODE;

        String[] colNames = {WeatheringTable.INDEX_CODE_COLUMN,
                WeatheringTable.GROUP_CODE_COLUMN,
                WeatheringTable.CODE_COLUMN,
                WeatheringTable.KEY_COLUMN,
                WeatheringTable.SHORT_DESCRIPTION_COLUMN,
                WeatheringTable.DESCRIPTION_COLUMN,
                WeatheringTable.VALUE_COLUMN};

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
         public boolean deleteWeathering( String groupCode, String code) {
             String[] colNames = {WeatheringTable.INDEX_CODE_COLUMN,
                     WeatheringTable.GROUP_CODE_COLUMN,
                     WeatheringTable.CODE_COLUMN};
             Object[] colValues = {
                     Weathering.INDEX_CODE,
                     groupCode,
                     code};
             int howMany = deletePersistentEntitiesFilteredByColumns(WeatheringTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
             return (howMany == 1);
         }

    @Override
    public int deleteAllWeatherings() {
        return deleteAllPersistentEntities(WeatheringTable.MAPPED_INDEX_DATABASE_TABLE);
    }

    @Override
    public Long countWeatherings() {
        return countRecords(WeatheringTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
