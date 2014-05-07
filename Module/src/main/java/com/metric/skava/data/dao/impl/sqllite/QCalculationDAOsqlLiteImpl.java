package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.barton.model.RQD;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.data.dao.LocalJaDAO;
import com.metric.skava.data.dao.LocalJnDAO;
import com.metric.skava.data.dao.LocalJrDAO;
import com.metric.skava.data.dao.LocalJwDAO;
import com.metric.skava.data.dao.LocalQCalculationDAO;
import com.metric.skava.data.dao.LocalSrfDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.QCalculationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class QCalculationDAOsqlLiteImpl extends SqlLiteBasePersistentEntityDAO<Q_Calculation> implements LocalQCalculationDAO {


    private LocalJnDAO mLocalJnDAO;
    private LocalJrDAO mLocalJrDAO;
    private LocalJwDAO mLocalJwDAO;
    private LocalJaDAO mLocalJaDAO;
    private LocalSrfDAO mLocalSrfDAO;


    public QCalculationDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mLocalJaDAO = getDAOFactory().getLocalJaDAO();
        mLocalJnDAO = getDAOFactory().getLocalJnDAO();
        mLocalJrDAO = getDAOFactory().getLocalJrDAO();
        mLocalJwDAO = getDAOFactory().getLocalJwDAO();
        mLocalSrfDAO = getDAOFactory().getLocalSrfDAO();
    }


    @Override
    protected List<Q_Calculation> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Q_Calculation> list = new ArrayList<Q_Calculation>();
        while (cursor.moveToNext()) {
            Integer rqdValue = CursorUtils.getInt(QCalculationTable.RQD_COLUMN, cursor);
            String jnCode = CursorUtils.getString(QCalculationTable.Jn_CODE_COLUMN, cursor);
            String jrCode = CursorUtils.getString(QCalculationTable.Jr_CODE_COLUMN, cursor);
            String jaCode = CursorUtils.getString(QCalculationTable.Ja_CODE_COLUMN, cursor);
            String jwCode = CursorUtils.getString(QCalculationTable.Jw_CODE_COLUMN, cursor);
            String srfCode = CursorUtils.getString(QCalculationTable.SRF_CODE_COLUMN, cursor);
            //This seems to be persisted only to transfer to Dropbox but not needed in the deserialization/parsing process
            Double qValue = CursorUtils.getDouble(QCalculationTable.Q_COLUMN, cursor);

            RQD rqd = new RQD(rqdValue);

            Jn jn = mLocalJnDAO.getJnByUniqueCode(jnCode);
            Jr jr = mLocalJrDAO.getJrByUniqueCode(jrCode);
            Ja ja = mLocalJaDAO.getJaByUniqueCode(jaCode);
            Jw jw = mLocalJwDAO.getJwByUniqueCode(jwCode);
            SRF srf = mLocalSrfDAO.getSrfByUniqueCode(srfCode);

            Q_Calculation newInstance = new Q_Calculation(rqd, jn, jr, ja, jw, srf);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public Q_Calculation getQCalculation(String assessmentCode) throws DAOException {
        Q_Calculation qCalculation = getPersistentEntityByCandidateKey(QCalculationTable.Q_CALCULATION_DATABASE_TABLE, QCalculationTable.ASSESSMENT_CODE_COLUMN, assessmentCode);
        return qCalculation;

    }

    @Override
    public void saveQCalculation(String assessmentCode, Q_Calculation newQCalculation) throws DAOException {
        savePersistentEntity(QCalculationTable.Q_CALCULATION_DATABASE_TABLE, assessmentCode, newQCalculation);
    }

    @Override
    protected void savePersistentEntity(String tableName, Q_Calculation newSkavaEntity) throws DAOException {
        savePersistentEntity(tableName, null, newSkavaEntity);
    }

    protected void savePersistentEntity(String tableName, String assessmentCode, Q_Calculation newQCalculation) throws DAOException {
        if (newQCalculation != null) {
            String[] qCalculationNames = new String[]{
                    QCalculationTable.GLOBAL_KEY_ID,
                    QCalculationTable.ASSESSMENT_CODE_COLUMN,
                    QCalculationTable.RQD_COLUMN,
                    QCalculationTable.Jn_CODE_COLUMN,
                    QCalculationTable.Jr_CODE_COLUMN,
                    QCalculationTable.Ja_CODE_COLUMN,
                    QCalculationTable.Jw_CODE_COLUMN,
                    QCalculationTable.SRF_CODE_COLUMN,
                    QCalculationTable.Q_COLUMN
            };
            Object[] qCalculationValues = new Object[]{
                    newQCalculation.get_id(),
                    assessmentCode,
                    newQCalculation.getRqd().getValue(),
                    newQCalculation.getJn().getCode(),
                    newQCalculation.getJr().getCode(),
                    newQCalculation.getJa().getCode(),
                    newQCalculation.getJw().getCode(),
                    newQCalculation.getSrf().getCode(),
                    newQCalculation.getQResult().getQBarton(),
            };
            Long qCalculationId = saveRecord(tableName, qCalculationNames, qCalculationValues);
            newQCalculation.set_id(qCalculationId);
        }
    }


    @Override
    public boolean deleteQCalculation(String assessmentCode) {
        int numDeleted = deletePersistentEntitiesFilteredByColumn(QCalculationTable.Q_CALCULATION_DATABASE_TABLE, QCalculationTable.ASSESSMENT_CODE_COLUMN, assessmentCode);
        return numDeleted != -1;
    }


    @Override
    public int deleteAllQCalculations() throws DAOException {
        return deleteAllPersistentEntities(QCalculationTable.Q_CALCULATION_DATABASE_TABLE);
    }


}
