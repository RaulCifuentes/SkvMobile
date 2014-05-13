package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.LocalArchTypeDAO;
import com.metric.skava.data.dao.LocalBoltTypeDAO;
import com.metric.skava.data.dao.LocalCoverageDAO;
import com.metric.skava.data.dao.LocalMeshTypeDAO;
import com.metric.skava.data.dao.LocalRockQualityDAO;
import com.metric.skava.data.dao.LocalShotcreteTypeDAO;
import com.metric.skava.data.dao.LocalSupportPatternTypeDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.RemoteSupportRequirementDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRequirementDropboxTable;
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
 * Created by metricboy on 3/7/14.
 */
public class SupportRequirementDAODropboxImpl extends DropBoxBaseDAO implements RemoteSupportRequirementDAO {

    private SupportRequirementDropboxTable mSupportRequirementTable;
    private LocalTunnelDAO tunnelDAO;
    private LocalBoltTypeDAO boltTypeDAO;
    private LocalSupportPatternTypeDAO supportPatternTypeDAO;
    private LocalRockQualityDAO rockQualityDAO;
    private LocalShotcreteTypeDAO shotcreteTypeDAO;
    private LocalMeshTypeDAO meshTypeDAO;
    private LocalCoverageDAO coverageDAO;
    private LocalArchTypeDAO archTypeDAO;


    public SupportRequirementDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mSupportRequirementTable = new SupportRequirementDropboxTable(getDatastore());
        this.tunnelDAO = getDAOFactory().getLocalTunnelDAO();
        this.boltTypeDAO = getDAOFactory().getLocalBoltTypeDAO();
        this.supportPatternTypeDAO = getDAOFactory().getLocalSupportPatternTypeDAO();
        this.rockQualityDAO = getDAOFactory().getLocalRockQualityDAO();
        this.shotcreteTypeDAO = getDAOFactory().getLocalShotcreteTypeDAO();
        this.meshTypeDAO = getDAOFactory().getLocalMeshTypeDAO();
        this.coverageDAO = getDAOFactory().getLocalCoverageDAO();
        this.archTypeDAO = getDAOFactory().getLocalArchTypeDAO();

    }


    @Override
    public List<SupportRequirement> getAllSupportRequirements() throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            List<SupportRequirement> listSupportRequirements = new ArrayList<SupportRequirement>();
            List<DbxRecord> supporRequirementsList = mSupportRequirementTable.findAll();
            for (DbxRecord currentSupportRequirementRecord : supporRequirementsList) {
                String codigo = currentSupportRequirementRecord.getString("SupportId");
                String tunnelCode = currentSupportRequirementRecord.getString("TUNNEL_CODE");
                Double lowerQ = currentSupportRequirementRecord.getDouble("Q_LOWER_BOUNDARY");
                Double upperQ = currentSupportRequirementRecord.getDouble("Q_UPPER_BOUNDARY");
                String name = readString(currentSupportRequirementRecord, "NAME");
                String boltTypeCode = readString(currentSupportRequirementRecord, "BOLT_TYPE_CODE");
                Double boltDiameter = readDouble(currentSupportRequirementRecord, "BOLT_DIAMETER");
                Double boltLength = readDouble(currentSupportRequirementRecord, "BOLT_LENGTH");
                String wallPatternTypeCode = readString(currentSupportRequirementRecord, "PatternWallType");
                Double wallDx = readDouble(currentSupportRequirementRecord, "PatternWall_dx");
                Double wallDy = readDouble(currentSupportRequirementRecord, "PatternWall_dy");
                String roofPatternTypeCode = readString(currentSupportRequirementRecord, "PatternRoofType");
                Double roofDx = readDouble(currentSupportRequirementRecord, "PatternRoof_dx");
                Double roofDy = readDouble(currentSupportRequirementRecord, "PatternRoof_dy");
                String shotcreteTypeCode = readString(currentSupportRequirementRecord, "SHOTCRETE_TYPE");
                Double thickness = readDouble(currentSupportRequirementRecord, "THICKNESS");
                String meshTypeCode = readString(currentSupportRequirementRecord, "MESH_TYPE");
                String coverageCode = readString(currentSupportRequirementRecord, "COVERAGE");
                String archTypeCode = readString(currentSupportRequirementRecord, "ARCH_TYPE");
                Double separation = readDouble(currentSupportRequirementRecord, "SEPARATION");

                Tunnel tunnel = tunnelDAO.getTunnelByUniqueCode(tunnelCode);

                BoltType boltType = null;
                if (boltTypeCode != null) {
                    boltType = boltTypeDAO.getBoltTypeByCode(boltTypeCode);
                }

                SupportPattern roofPattern = null;
                if (roofPatternTypeCode != null) {
                    SupportPatternType roofPatternType = supportPatternTypeDAO.getSupportPatternTypeByCode(roofPatternTypeCode);
                    roofPattern = new SupportPattern(roofPatternType, roofDx, roofDy);
                }

                SupportPattern wallPattern = null;
                if (wallPatternTypeCode != null) {
                    SupportPatternType wallPatternType = supportPatternTypeDAO.getSupportPatternTypeByCode(wallPatternTypeCode);
                    wallPattern = new SupportPattern(wallPatternType, wallDx, wallDy);
                }

                ShotcreteType shotcreteType = null;
                if (shotcreteTypeCode != null) {
                    shotcreteType = shotcreteTypeDAO.getShotcreteTypeByCode(shotcreteTypeCode);
                }

                MeshType meshType = null;
                if (meshTypeCode != null) {
                    meshType = meshTypeDAO.getMeshTypeByCode(meshTypeCode);
                }

                Coverage coverage = null;
                if (coverageCode != null) {
                    coverage = coverageDAO.getCoverageByCode(coverageCode);
                }

                ArchType archType = null;
                if (archTypeCode != null) {
                    archType = archTypeDAO.getArchTypeByCode(archTypeCode);
                }
                SupportRequirement newSupportRequirement = new SupportRequirement(tunnel, codigo, name);
                newSupportRequirement.setqBartonLowerBoundary(lowerQ);
                newSupportRequirement.setqBartonUpperBoundary(upperQ);
                newSupportRequirement.setBoltType(boltType);
                newSupportRequirement.setDiameter(boltDiameter);
                newSupportRequirement.setLength(boltLength);
                newSupportRequirement.setRoofPattern(roofPattern);
                newSupportRequirement.setWallPattern(wallPattern);
                newSupportRequirement.setShotcreteType(shotcreteType);
                newSupportRequirement.setThickness(thickness);
                newSupportRequirement.setMeshType(meshType);
                newSupportRequirement.setCoverage(coverage);
                newSupportRequirement.setArchType(archType);
                newSupportRequirement.setSeparation(separation);

                listSupportRequirements.add(newSupportRequirement);
            }
            return listSupportRequirements;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }


}
