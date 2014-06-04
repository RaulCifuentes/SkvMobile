package com.metric.skava.home.helper;

import android.util.Log;

import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.sync.exception.SyncDataFailedException;
import com.metric.skava.sync.helper.SyncHelper;

/**
 * Created by metricboy on 5/30/14.
 */
public class ImportDataHelper {

    public final SyncHelper syncHelper;

    public ImportDataHelper(SyncHelper syncHelper) {
        this.syncHelper = syncHelper;
    }

    public SyncHelper getSyncHelper() {
        return syncHelper;
    }


    public Long importRoles() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadRoles();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }


    public Long importMethods() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadExcavationMethods();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importSections() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadExcavationSections();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }


    public Long importDiscontinuityTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadDiscontinuityTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importRelevances() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadDiscontinuityRelevances();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importIndexes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadIndexes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importGroups() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadGroups();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importSpacings() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadSpacings();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importPersistences() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadPersistences();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importApertures() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadApertures();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importShapes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadShapes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importRoughnesses() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadRoughnesses();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importInfillings() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadInfillings();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importWeatherings() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadWeatherings();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importWaters() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadWaters();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importStrengths() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadStrengths();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importGroundwaters() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadGroundwaters();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importOrientations() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadOrientations();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importJns() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadJns();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importJrs() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadJrs();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importJas() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadJas();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importJws() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadJws();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importSRFs() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadSRFs();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importFractureTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadFractureTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importBoltTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadBoltTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importShotcreteTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadShotcreteTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importMeshTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadMeshTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importCoverages() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadCoverages();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importArchTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadArchTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importESRs() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadESRs();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importSupportPatternTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadSupportPatternTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importRockQualities() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadRockQualities();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    //************ USER RELATED DATA **************** /
    public Long importClients() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadClients();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importProjects() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadProjects();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }


    public Long importTunnels() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadTunnels();

        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importSupportRequirements() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadSupportRequirements();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }


    public Long importTunnelFaces() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadTunnelFaces();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    public Long importUsers() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadUsers();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }


}
