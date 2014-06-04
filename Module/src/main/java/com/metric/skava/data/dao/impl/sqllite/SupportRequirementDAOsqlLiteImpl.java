package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalArchTypeDAO;
import com.metric.skava.data.dao.LocalBoltTypeDAO;
import com.metric.skava.data.dao.LocalCoverageDAO;
import com.metric.skava.data.dao.LocalMeshTypeDAO;
import com.metric.skava.data.dao.LocalShotcreteTypeDAO;
import com.metric.skava.data.dao.LocalSupportPatternTypeDAO;
import com.metric.skava.data.dao.LocalSupportRequirementDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.SupportRequirementTable;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportPatternType;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class SupportRequirementDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<SupportRequirement> implements LocalSupportRequirementDAO {

    public SupportRequirementDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<SupportRequirement> assemblePersistentEntities(Cursor cursor) throws DAOException {

        List<SupportRequirement> list = new ArrayList<SupportRequirement>();

        while (cursor.moveToNext()) {
            String tunnelCode = CursorUtils.getString(SupportRequirementTable.TUNNEL_CODE_COLUMN, cursor);
            String code = CursorUtils.getString(SupportRequirementTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(SupportRequirementTable.NAME_COLUMN, cursor);
            Double qLower = CursorUtils.getDouble(SupportRequirementTable.Q_LOWER_BOUND_COLUMN, cursor);
            Double qUpper = CursorUtils.getDouble(SupportRequirementTable.Q_UPPER_BOUND_COLUMN, cursor);
            String boltTypeCode = CursorUtils.getString(SupportRequirementTable.BOLT_TYPE_CODE_COLUMN, cursor);
            Double boltDiameter = CursorUtils.getDouble(SupportRequirementTable.BOLT_DIAMETER_COLUMN, cursor);
            Double boltLength = CursorUtils.getDouble(SupportRequirementTable.BOLT_LENGTH_COLUMN, cursor);
            String roofPatternTypeCode = CursorUtils.getString(SupportRequirementTable.ROOF_PATTERN_TYPE_CODE_COLUMN, cursor);
            Double roofPatternXDistance = CursorUtils.getDouble(SupportRequirementTable.ROOF_PATTERN_DX_COLUMN, cursor);
            Double roofPatternYDistance = CursorUtils.getDouble(SupportRequirementTable.ROOF_PATTERN_DY_COLUMN, cursor);
            String wallPatternTypeCode = CursorUtils.getString(SupportRequirementTable.WALL_PATTERN_TYPE_CODE_COLUMN, cursor);
            Double wallPatternXDistance = CursorUtils.getDouble(SupportRequirementTable.WALL_PATTERN_DX_COLUMN, cursor);
            Double wallPatternYDistance = CursorUtils.getDouble(SupportRequirementTable.WALL_PATTERN_DY_COLUMN, cursor);
            String shotcreteTypeCode = CursorUtils.getString(SupportRequirementTable.SHOTCRETE_TYPE_CODE_COLUMN, cursor);
            String shotcreteCoverageCode = CursorUtils.getString(SupportRequirementTable.SHOTCRETE_COVERAGE_CODE_COLUMN, cursor);
            Double thickness = CursorUtils.getDouble(SupportRequirementTable.THICKNESS_COLUMN, cursor);
            String meshTypeCode = CursorUtils.getString(SupportRequirementTable.MESH_TYPE_CODE_COLUMN, cursor);
            String meshCoverageCode = CursorUtils.getString(SupportRequirementTable.MESH_COVERAGE_CODE_COLUMN, cursor);
            String archTypeCode = CursorUtils.getString(SupportRequirementTable.ARCH_TYPE_CODE_COLUMN, cursor);
            Double separation = CursorUtils.getDouble(SupportRequirementTable.SEPARATION_COLUMN, cursor);

            DAOFactory daoFactory = getDAOFactory();

            LocalTunnelDAO localTunnelFaceDAO = daoFactory.getLocalTunnelDAO();
            Tunnel tunnel = localTunnelFaceDAO.getTunnelByUniqueCode(tunnelCode);

            LocalBoltTypeDAO localBoltTypeDAO = daoFactory.getLocalBoltTypeDAO();
            BoltType boltType = localBoltTypeDAO.getBoltTypeByCode(boltTypeCode);

            LocalSupportPatternTypeDAO supportPatternTypeDAO = daoFactory.getLocalSupportPatternTypeDAO();
            SupportPatternType supportPatternType = supportPatternTypeDAO.getSupportPatternTypeByUniqueCode(roofPatternTypeCode);
            SupportPattern roofPattern = new SupportPattern(supportPatternType, roofPatternXDistance, roofPatternYDistance);

            supportPatternType = supportPatternTypeDAO.getSupportPatternTypeByUniqueCode(wallPatternTypeCode);
            SupportPattern wallPattern = new SupportPattern(supportPatternType, wallPatternXDistance, wallPatternYDistance);

            LocalShotcreteTypeDAO shotcreteTypeDAO = daoFactory.getLocalShotcreteTypeDAO();
            ShotcreteType shotcrete = shotcreteTypeDAO.getShotcreteTypeByCode(shotcreteTypeCode);

            LocalMeshTypeDAO localMeshTypeDAO = daoFactory.getLocalMeshTypeDAO();
            MeshType meshType = localMeshTypeDAO.getMeshTypeByCode(meshTypeCode);

            LocalCoverageDAO localCoverageDAO = daoFactory.getLocalCoverageDAO();
            Coverage shotcreteCoverage = localCoverageDAO.getCoverageByCode(shotcreteCoverageCode);
            Coverage meshCoverage = localCoverageDAO.getCoverageByCode(meshCoverageCode);

            LocalArchTypeDAO localArchTypeDAO = daoFactory.getLocalArchTypeDAO();
            ArchType archType = localArchTypeDAO.getArchTypeByCode(archTypeCode);

            SupportRequirement newInstance = new SupportRequirement(tunnel, code, name);
            newInstance.setTunnel(tunnel);
            newInstance.setqBartonLowerBoundary(qLower);
            newInstance.setqBartonUpperBoundary(qUpper);
            newInstance.setBoltType(boltType);
            newInstance.setDiameter(boltDiameter);
            newInstance.setLength(boltLength);
            newInstance.setRoofPattern(roofPattern);
            newInstance.setWallPattern(wallPattern);
            newInstance.setShotcreteType(shotcrete);
            newInstance.setShotcreteCoverage(shotcreteCoverage);
            newInstance.setThickness(thickness);
            newInstance.setMeshType(meshType);
            newInstance.setMeshCoverage(meshCoverage);
            newInstance.setArchType(archType);
            newInstance.setSeparation(separation);

            list.add(newInstance);
        }
        return list;
    }


    @Override
    public SupportRequirement findSupportRequirement(Tunnel tunnel, Double qBarton) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(SupportRequirementTable.SUPPORT_REQUIREMENT_DATABASE_TABLE, SupportRequirementTable.TUNNEL_CODE_COLUMN, tunnel.getCode(), SupportRequirementTable.Q_LOWER_BOUND_COLUMN);
        List<SupportRequirement> listSupportRequirements = assemblePersistentEntities(cursor);

        for (SupportRequirement currentSupportRequirement : listSupportRequirements) {
            Double lowerBound = currentSupportRequirement.getqBartonLowerBoundary();
            Double upperBound = currentSupportRequirement.getqBartonUpperBoundary();
            if (lowerBound <= qBarton && qBarton < upperBound) {
                return currentSupportRequirement;
            }
        }
        return null;
    }


    @Override
    public List<SupportRequirement> getAllSupportRequirements(Tunnel tunnel) throws DAOException {
        List<SupportRequirement> list = getAllPersistentEntities(SupportRequirementTable.SUPPORT_REQUIREMENT_DATABASE_TABLE);
        return list;
    }

    @Override
    public SupportRequirement getSupportRequirement(String code) throws DAOException {
        return getIdentifiableEntityByCode(SupportRequirementTable.SUPPORT_REQUIREMENT_DATABASE_TABLE, code);
    }


    @Override
    public void saveSupportRequirement(SupportRequirement newEntity) throws DAOException {
        savePersistentEntity(SupportRequirementTable.SUPPORT_REQUIREMENT_DATABASE_TABLE, newEntity);
    }


    @Override
    protected void savePersistentEntity(String tableName, SupportRequirement newSkavaEntity) throws DAOException {
        String[] columns = new String[]{
                SupportRequirementTable.TUNNEL_CODE_COLUMN,
                SupportRequirementTable.CODE_COLUMN,
                SupportRequirementTable.NAME_COLUMN,
                SupportRequirementTable.Q_LOWER_BOUND_COLUMN,
                SupportRequirementTable.Q_UPPER_BOUND_COLUMN,
                SupportRequirementTable.BOLT_TYPE_CODE_COLUMN,
                SupportRequirementTable.BOLT_DIAMETER_COLUMN,
                SupportRequirementTable.BOLT_LENGTH_COLUMN,
                SupportRequirementTable.ROOF_PATTERN_TYPE_CODE_COLUMN,
                SupportRequirementTable.ROOF_PATTERN_DX_COLUMN,
                SupportRequirementTable.ROOF_PATTERN_DY_COLUMN,
                SupportRequirementTable.WALL_PATTERN_TYPE_CODE_COLUMN,
                SupportRequirementTable.WALL_PATTERN_DX_COLUMN,
                SupportRequirementTable.WALL_PATTERN_DY_COLUMN,
                SupportRequirementTable.SHOTCRETE_TYPE_CODE_COLUMN,
                SupportRequirementTable.SHOTCRETE_COVERAGE_CODE_COLUMN,
                SupportRequirementTable.THICKNESS_COLUMN,
                SupportRequirementTable.MESH_TYPE_CODE_COLUMN,
                SupportRequirementTable.MESH_COVERAGE_CODE_COLUMN,
                SupportRequirementTable.ARCH_TYPE_CODE_COLUMN,
                SupportRequirementTable.SEPARATION_COLUMN
        };
        //Currently the relation is mantained by the Tunnel side of the relations so ...
        Object[] values = new Object[]{
                newSkavaEntity.getTunnel().getCode(),
                newSkavaEntity.getCode(),
                newSkavaEntity.getName(),
                newSkavaEntity.getqBartonLowerBoundary(),
                newSkavaEntity.getqBartonUpperBoundary(),
                SkavaUtils.isDefined(newSkavaEntity.getBoltType()) ? newSkavaEntity.getBoltType().getCode() : null,
                newSkavaEntity.getDiameter(),
                newSkavaEntity.getLength(),
                newSkavaEntity.getRoofPattern() != null ? newSkavaEntity.getRoofPattern().getType().getCode() : null,
                newSkavaEntity.getRoofPattern() != null ? newSkavaEntity.getRoofPattern().getDistanceX() : null,
                newSkavaEntity.getRoofPattern() != null ? newSkavaEntity.getRoofPattern().getDistanceY() : null,
                newSkavaEntity.getWallPattern() != null ? newSkavaEntity.getWallPattern().getType().getCode() : null,
                newSkavaEntity.getWallPattern() != null ? newSkavaEntity.getWallPattern().getDistanceX() : null,
                newSkavaEntity.getWallPattern() != null ? newSkavaEntity.getWallPattern().getDistanceY() : null,
                SkavaUtils.isDefined(newSkavaEntity.getShotcreteType()) ? newSkavaEntity.getShotcreteType().getCode() : null,
                SkavaUtils.isDefined(newSkavaEntity.getShotcreteCoverage()) ? newSkavaEntity.getShotcreteCoverage().getCode() : null,
                newSkavaEntity.getThickness(),
                SkavaUtils.isDefined(newSkavaEntity.getMeshType()) ? newSkavaEntity.getMeshType().getCode() : null,
                SkavaUtils.isDefined(newSkavaEntity.getMeshCoverage()) ? newSkavaEntity.getMeshCoverage().getCode() : null,
                SkavaUtils.isDefined(newSkavaEntity.getArchType()) ? newSkavaEntity.getArchType().getCode() : null,
                newSkavaEntity.getSeparation()};
        saveRecord(tableName, columns, values);
    }

    @Override
    public boolean deleteSupportRequirements(Tunnel tunnel) {
        int numDeleted = deletePersistentEntitiesFilteredByColumn(SupportRequirementTable.SUPPORT_REQUIREMENT_DATABASE_TABLE, SupportRequirementTable.TUNNEL_CODE_COLUMN, tunnel.getCode());
        return numDeleted != -1;
    }

    @Override
    public boolean deleteSupportRequirement(String code) {
        return deleteIdentifiableEntity(SupportRequirementTable.SUPPORT_REQUIREMENT_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllSupportRequirements() {
        return deleteAllPersistentEntities(SupportRequirementTable.SUPPORT_REQUIREMENT_DATABASE_TABLE);
    }


}
