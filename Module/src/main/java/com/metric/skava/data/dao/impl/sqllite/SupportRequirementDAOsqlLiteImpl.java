package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalArchTypeDAO;
import com.metric.skava.data.dao.LocalBoltTypeDAO;
import com.metric.skava.data.dao.LocalCoverageDAO;
import com.metric.skava.data.dao.LocalMeshTypeDAO;
import com.metric.skava.data.dao.LocalShotcreteTypeDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.SupportRequirementDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.SupportRequirementTable;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.rockmass.model.RockMass;
import com.metric.skava.rocksupport.model.ESR;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class SupportRequirementDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<SupportRequirement> implements SupportRequirementDAO {

    public SupportRequirementDAOsqlLiteImpl(Context context) {
        super(context);
    }



    @Override
    protected List<SupportRequirement> assamblePersistentEntities(Cursor cursor) throws DAOException {

        List<SupportRequirement> list = new ArrayList<SupportRequirement>();

        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(SupportRequirementTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(SupportRequirementTable.NAME_COLUMN, cursor);
            String faceCode = CursorUtils.getString(SupportRequirementTable.TUNNEL_CODE_COLUMN, cursor);
            String boltTypeCode = CursorUtils.getString(SupportRequirementTable.BOLT_TYPE_CODE_COLUMN, cursor);
            Double boltDiameter = CursorUtils.getDouble(SupportRequirementTable.BOLT_DIAMETER_COLUMN, cursor);
            Double boltLength = CursorUtils.getDouble(SupportRequirementTable.BOLT_LENGTH_COLUMN, cursor);
            String shotcreteTypeCode = CursorUtils.getString(SupportRequirementTable.SHOTCRETE_TYPE_CODE_COLUMN, cursor);
            Double thickness = CursorUtils.getDouble(SupportRequirementTable.THICKNESS_COLUMN, cursor);
            String meshTypeCode = CursorUtils.getString(SupportRequirementTable.MESH_TYPE_CODE_COLUMN, cursor);
            String coverageCode = CursorUtils.getString(SupportRequirementTable.COVERAGE_CODE_COLUMN, cursor);
            String archTypeCode = CursorUtils.getString(SupportRequirementTable.ARCH_TYPE_CODE_COLUMN, cursor);
            Double separation = CursorUtils.getDouble(SupportRequirementTable.SEPARATION_COLUMN, cursor);


            DAOFactory daoFactory = DAOFactory.getInstance(getContext());

            LocalTunnelDAO localTunnelFaceDAO = daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE);
            Tunnel tunnel = localTunnelFaceDAO.getTunnelByCode(faceCode);

            LocalBoltTypeDAO localBoltTypeDAO = daoFactory.getBoltTypeDAO();
            BoltType boltType = localBoltTypeDAO.getBoltTypeByCode(boltTypeCode);

            LocalShotcreteTypeDAO shotcreteTypeDAO = daoFactory.getShotcreteTypeDAO();
            ShotcreteType shotcrete = shotcreteTypeDAO.getShotcreteTypeByCode(shotcreteTypeCode);

            LocalMeshTypeDAO localMeshTypeDAO = daoFactory.getMeshTypeDAO();
            MeshType meshType = localMeshTypeDAO.getMeshTypeByCode(meshTypeCode);

            LocalCoverageDAO localCoverageDAO = daoFactory.getCoverageDAO();
            Coverage coverage = localCoverageDAO.getCoverageByCode(coverageCode);

            LocalArchTypeDAO localArchTypeDAO = daoFactory.getArchTypeDAO();
            ArchType archType = localArchTypeDAO.getArchTypeByCode(archTypeCode);

            SupportRequirement newInstance = new SupportRequirement(code, name);
            newInstance.setTunnel(tunnel);
            newInstance.setBoltType(boltType);
            newInstance.setDiameter(boltDiameter);
            newInstance.setLength(boltLength);
            newInstance.setShotcreteType(shotcrete);
            newInstance.setThickness(thickness);
            newInstance.setMeshType(meshType);
            newInstance.setCoverage(coverage);
            newInstance.setArchType(archType);
            newInstance.setSeparation(separation);

            list.add(newInstance);


        }
        return list;
    }


    @Override
    public SupportRequirement getSupportRequirementByTunnel(Tunnel tunnel, RockMass.RockMassQualityType qualityType) throws DAOException {
        String [] names = new String[] {SupportRequirementTable.TUNNEL_CODE_COLUMN, SupportRequirementTable.ROCK_QUALITY_CODE_COLUMN};
        Object [] values = new Object[] {tunnel.getCode(), qualityType.name() };
        Cursor cursor = getRecordsFilteredByColumns(SupportRequirementTable.SUPPORT_DATABASE_TABLE, names, values, null);
        List<SupportRequirement> listSupportRequirements = assamblePersistentEntities(cursor);

        ESR tunnelESR = tunnel.getExcavationFactors().getEsr();
        Double tunnelSpan = tunnel.getExcavationFactors().getSpan();
        Double esrSpanRatio = tunnelSpan / tunnelESR.getValue();

        for (SupportRequirement currentSupportRequirement : listSupportRequirements) {
            Double lowerBound = currentSupportRequirement.getSpanOverESRLower();
            Double upperBound = currentSupportRequirement.getSpanOverESRUpper();
            if (lowerBound < esrSpanRatio && esrSpanRatio < upperBound) {
                return currentSupportRequirement;
            }
        }
        return null;
    }


    @Override
    public List<SupportRequirement> getAllSupportRequirements() throws DAOException {
        List<SupportRequirement> list = getAllPersistentEntities(SupportRequirementTable.SUPPORT_DATABASE_TABLE);
        return list;
    }


    @Override
    public void saveSupportRequirement(SupportRequirement newEntity) throws DAOException {
        savePersistentEntity(SupportRequirementTable.SUPPORT_DATABASE_TABLE, newEntity);
    }


    @Override
    protected void savePersistentEntity(String tableName, SupportRequirement newSkavaEntity) throws DAOException {

    }

    @Override
    public boolean deleteSupportRequirement(String code) {
        return false;
    }

    @Override
    public int deleteEmptySupportRequirements() {
        return 0;
    }


}
