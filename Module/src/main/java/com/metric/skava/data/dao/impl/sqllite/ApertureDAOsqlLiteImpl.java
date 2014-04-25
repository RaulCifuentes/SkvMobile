package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

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
public class ApertureDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalApertureDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public ApertureDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Aperture getAperture(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{ApertureTable.INDEX_CODE_COLUMN, GROUP_CODE_COLUMN, CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(ApertureTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Aperture> list = assambleApertures(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Aperture> assambleApertures(Cursor cursor) throws DAOException {
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
        List<Aperture> list = assambleApertures(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveAperture(Aperture assessment) throws DAOException {

    }

    @Override
    public boolean deleteAperture(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllApertures() {
        return 0;
    }


}
