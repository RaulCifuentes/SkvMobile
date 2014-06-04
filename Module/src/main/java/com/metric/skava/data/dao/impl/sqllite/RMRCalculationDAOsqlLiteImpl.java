package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.calculator.rmr.model.RQD_RMR;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.LocalApertureDAO;
import com.metric.skava.data.dao.LocalGroundwaterDAO;
import com.metric.skava.data.dao.LocalInfillingDAO;
import com.metric.skava.data.dao.LocalOrientationDiscontinuitiesDAO;
import com.metric.skava.data.dao.LocalPersistenceDAO;
import com.metric.skava.data.dao.LocalRMRCalculationDAO;
import com.metric.skava.data.dao.LocalRoughnessDAO;
import com.metric.skava.data.dao.LocalSpacingDAO;
import com.metric.skava.data.dao.LocalStrengthDAO;
import com.metric.skava.data.dao.LocalWeatheringDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.RMRCalculationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class RMRCalculationDAOsqlLiteImpl extends SqlLiteBasePersistentEntityDAO<RMR_Calculation> implements LocalRMRCalculationDAO {

    private LocalStrengthDAO mLocalStrengthDAO;
    private LocalSpacingDAO mLocalSpacingDAO;
    private LocalPersistenceDAO mLocalPersistenceDAO;
    private LocalApertureDAO mLocalApertureDAO;
    private LocalRoughnessDAO mLocalRoughnessDAO;
    private LocalInfillingDAO mLocalInfillingDAO;
    private LocalWeatheringDAO mLocalWeatheringDAO;
    private LocalGroundwaterDAO mLocalGroundwaterDAO;
    private LocalOrientationDiscontinuitiesDAO mLocalOrientationDAO;


    public RMRCalculationDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mLocalStrengthDAO = getDAOFactory().getLocalStrengthDAO();
        mLocalSpacingDAO = getDAOFactory().getLocalSpacingDAO();
        mLocalPersistenceDAO = getDAOFactory().getLocalPersistenceDAO();
        mLocalApertureDAO = getDAOFactory().getLocalApertureDAO();
        mLocalRoughnessDAO = getDAOFactory().getLocalRoughnessDAO();
        mLocalInfillingDAO = getDAOFactory().getLocalInfillingDAO();
        mLocalWeatheringDAO = getDAOFactory().getLocalWeatheringDAO();
        mLocalGroundwaterDAO = getDAOFactory().getLocalGroundwaterDAO();
        mLocalOrientationDAO = getDAOFactory().getLocalOrientationDiscontinuitiesDAO();
    }

    @Override
    protected List<RMR_Calculation> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<RMR_Calculation> list = new ArrayList<RMR_Calculation>();
        while (cursor.moveToNext()) {
            String strenghtCode = CursorUtils.getString(RMRCalculationTable.STRENGTHOFROCK_CODE_COLUMN, cursor);
            String rqdKey = CursorUtils.getString(RMRCalculationTable.RQD_RMR_CODE_COLUMN, cursor);
            String spacingCode = CursorUtils.getString(RMRCalculationTable.SPACING_CODE_COLUMN, cursor);
            String persistenceCode = CursorUtils.getString(RMRCalculationTable.PERSISTENCE_CODE_COLUMN, cursor);
            String apertureCode = CursorUtils.getString(RMRCalculationTable.APERTURE_CODE_COLUMN, cursor);
            String roughnessCode = CursorUtils.getString(RMRCalculationTable.ROUGHNESS_CODE_COLUMN, cursor);
            String infillingCode = CursorUtils.getString(RMRCalculationTable.INFILLING_CODE_COLUMN, cursor);
            String weatheringCode = CursorUtils.getString(RMRCalculationTable.WEATHERING_CODE_COLUMN, cursor);
            String groundwaterCode = CursorUtils.getString(RMRCalculationTable.GROUNDWATER_CODE_COLUMN, cursor);
            String orientationCode = CursorUtils.getString(RMRCalculationTable.ORIENTATION_CODE_COLUMN, cursor);

            //This seems to be persisted only to transfer to Dropbox but not needed in the deserialization/parsing process
            StrengthOfRock strenght = strenghtCode != null ? mLocalStrengthDAO.getStrengthByUniqueCode(strenghtCode) : null;
            RQD_RMR rqd = rqdKey != null ? RQD_RMR.findRQD_RMRByKey(rqdKey) : null;
            Spacing spacing = spacingCode != null ? mLocalSpacingDAO.getSpacingByUniqueCode(spacingCode) : null;
            Groundwater groundwater = groundwaterCode != null ? mLocalGroundwaterDAO.getGroundwaterByUniqueCode(groundwaterCode) : null;
            Persistence persistence = persistenceCode != null ? mLocalPersistenceDAO.getPersistenceByUniqueCode(persistenceCode) : null;
            Aperture aperture = apertureCode != null ? mLocalApertureDAO.getApertureByUniqueCode(apertureCode) : null;
            Roughness roughness = roughnessCode != null ? mLocalRoughnessDAO.getRoughnessByUniqueCode(roughnessCode) : null;
            Infilling infilling = infillingCode != null ? mLocalInfillingDAO.getInfillingByUniqueCode(infillingCode) : null;
            Weathering weathering = weatheringCode != null ? mLocalWeatheringDAO.getWeatheringByUniqueCode(weatheringCode) : null;
            OrientationDiscontinuities orientation = orientationCode != null ? mLocalOrientationDAO.getOrientationDiscontinuitiesByUniqueCode(orientationCode) : null;

            RMR_Calculation newInstance = new RMR_Calculation(strenght, rqd, spacing, persistence, aperture, roughness, infilling, weathering, groundwater, orientation);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public RMR_Calculation getRMRCalculation(String assessmentCode) throws DAOException {
        RMR_Calculation qCalculation = getPersistentEntityByCandidateKey(RMRCalculationTable.RMR_CALCULATION_DATABASE_TABLE, RMRCalculationTable.ASSESSMENT_CODE_COLUMN, assessmentCode);
        return qCalculation;

    }

    @Override
    public void saveRMRCalculation(String assessmentCode, RMR_Calculation newRMRCalculation) throws DAOException {
        savePersistentEntity(RMRCalculationTable.RMR_CALCULATION_DATABASE_TABLE, assessmentCode, newRMRCalculation);
    }

    @Override
    protected void savePersistentEntity(String tableName, RMR_Calculation newSkavaEntity) throws DAOException {
        savePersistentEntity(tableName, null, newSkavaEntity);
    }

    protected void savePersistentEntity(String tableName, String assessmentCode, RMR_Calculation rmrCalculation) throws DAOException {
        if (rmrCalculation != null) {
            //Save the related RMR Calculation
            String[] rmrCalculationNames = new String[]{
//                    RMRCalculationTable.GLOBAL_KEY_ID,
                    RMRCalculationTable.ASSESSMENT_CODE_COLUMN,
                    RMRCalculationTable.STRENGTHOFROCK_CODE_COLUMN,
                    RMRCalculationTable.RQD_RMR_CODE_COLUMN,
                    RMRCalculationTable.SPACING_CODE_COLUMN,
                    RMRCalculationTable.PERSISTENCE_CODE_COLUMN,
                    RMRCalculationTable.APERTURE_CODE_COLUMN,
                    RMRCalculationTable.ROUGHNESS_CODE_COLUMN,
                    RMRCalculationTable.INFILLING_CODE_COLUMN,
                    RMRCalculationTable.WEATHERING_CODE_COLUMN,
                    RMRCalculationTable.GROUNDWATER_CODE_COLUMN,
                    RMRCalculationTable.ORIENTATION_CODE_COLUMN,
                    RMRCalculationTable.RMR_COLUMN
            };

            Object[] rmrCalculationValues = new Object[]{
//                    rmrCalculation.get_id(),
                    assessmentCode,
                    rmrCalculation.getStrengthOfRock() != null ? rmrCalculation.getStrengthOfRock().getCode() : null,
                    rmrCalculation.getRqd() != null ? rmrCalculation.getRqd().getKey() : null,
                    rmrCalculation.getSpacing() != null ? rmrCalculation.getSpacing().getCode() : null,
                    rmrCalculation.getPersistence() != null ? rmrCalculation.getPersistence().getCode() : null,
                    rmrCalculation.getAperture() != null ? rmrCalculation.getAperture().getCode() : null,
                    rmrCalculation.getRoughness() != null ? rmrCalculation.getRoughness().getCode() : null,
                    rmrCalculation.getInfilling() != null ? rmrCalculation.getInfilling().getCode() : null,
                    rmrCalculation.getWeathering() != null ? rmrCalculation.getWeathering().getCode() : null,
                    rmrCalculation.getGroundwater() != null ? rmrCalculation.getGroundwater().getCode() : null,
                    rmrCalculation.getOrientationDiscontinuities() != null ? rmrCalculation.getOrientationDiscontinuities().getCode() : null,
                    rmrCalculation.getRMRResult() != null ? rmrCalculation.getRMRResult().getRMR(): null
            };
            Long rmrCalculationId = saveRecord(tableName, rmrCalculationNames, rmrCalculationValues);
            rmrCalculation.set_id(rmrCalculationId);
        }
    }


    @Override
    public boolean deleteRMRCalculation(String assessmentCode) {
        int numDeleted = deletePersistentEntitiesFilteredByColumn(RMRCalculationTable.RMR_CALCULATION_DATABASE_TABLE, RMRCalculationTable.ASSESSMENT_CODE_COLUMN, assessmentCode);
        return numDeleted != -1;
    }


    @Override
    public int deleteAllRMRCalculations() throws DAOException {
        return deleteAllPersistentEntities(RMRCalculationTable.RMR_CALCULATION_DATABASE_TABLE);
    }


}
