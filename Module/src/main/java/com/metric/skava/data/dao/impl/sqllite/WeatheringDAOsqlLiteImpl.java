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
public class WeatheringDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalWeatheringDAO {

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
    public Weathering getWeathering(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{WeatheringTable.INDEX_CODE_COLUMN, WeatheringTable.GROUP_CODE_COLUMN, WeatheringTable.CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(WeatheringTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Weathering> list = assambleWeatherings(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Weathering> assambleWeatherings(Cursor cursor) throws DAOException {
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
        List<Weathering> list = assambleWeatherings(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveWeathering(Weathering assessment) throws DAOException {

    }

    @Override
    public boolean deleteWeathering(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllWeatherings() {
        return 0;
    }


}
