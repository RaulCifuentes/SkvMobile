package com.metric.skava.data.dao.impl.dropbox.datastore;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastoreManager;
import com.dropbox.sync.android.DbxException;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.exception.DAOException;

/**
 * Created by metricboy on 3/6/14.
 */
public class DatastoreHelper {

    private static final String APP_SECRET = "pc2wwbqnblh0r53";

    private static final String APP_KEY = "bzsvwf0odnoslis";

    private static final String APP_DATASTORE_NAME = "skavatunnelapp";

    public static final int REQUEST_LINK_TO_DROPBOX = 510;

    private static DatastoreHelper instance;

    private Context mContext;
    private DbxAccountManager mDbxAcctMgr;
    private DbxDatastore mDatastore;
    private DbxAccount mAccount;

    public Activity getLinkDropboxAccountActivity() {
        return mLinkDropboxAccountActivity;
    }

    public void setLinkDropboxAccountActivity(Activity mLinkDropboxAccountActivity) {
        this.mLinkDropboxAccountActivity = mLinkDropboxAccountActivity;
    }

    private Activity mLinkDropboxAccountActivity;


    private DbxDatastoreManager mDatastoreManager;

    public static DatastoreHelper getInstance(Context context, Activity dropboxLinkAccountActivity) {
        if (instance == null) {
            instance = new DatastoreHelper(context);
            instance.setLinkDropboxAccountActivity(dropboxLinkAccountActivity);
        }
        return instance;
    }

    private DatastoreHelper(Context context) {
        this.mContext = context;
    }

    public DbxDatastore getDatastore() throws DAOException {
        if (mDatastore != null && mDatastore.isOpen()) {
            return mDatastore;
        } else {
            wakeUp();
        }
        return mDatastore;
    }

    private DbxDatastore.SyncStatusListener mDatastoreListener = new DbxDatastore.SyncStatusListener() {
        @Override
        public void onDatastoreStatusChange(DbxDatastore ds) {
            Log.d(SkavaConstants.LOG, "SYNC STATUS: " + ds.getSyncStatus().toString());
            if (ds.getSyncStatus().hasIncoming) {
                try {
                    mDatastore.sync();
                } catch (DbxException e) {
                    Log.e(SkavaConstants.LOG, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    };


    public void wakeUp() throws DAOException {
        try {
            assertLinkedAccount(getAccountManager());
            if (mAccount == null) {
                mAccount = getAccountManager().getLinkedAccount();
                if (mAccount != null) {
                    if (mDatastore == null || !mDatastore.isOpen()) {
                        mDatastoreManager = DbxDatastoreManager.forAccount(mAccount);
                        /// TODO use a non default datastore
//                mDatastore = mDatastoreManager.openDatastore(APP_DATASTORE_NAME);
                        mDatastore = DbxDatastore.openDefault(mAccount);
                        mDatastore.sync();
                        mDatastore.addSyncStatusListener(mDatastoreListener);
                    }
                } else {
                    throw new DAOException("Failed getting linked Dropbox user account !! ");
                }

            }
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }

    public void goSleep() {
        mDatastore.removeSyncStatusListener(mDatastoreListener);
        mDatastore.close();
    }


    /**
     * This is the method that onActivityResult handling REQUEST_LINK_TO_DROPBOX must call
     *
     * @param linkedAccount
     */
    public void setAccount(DbxAccount linkedAccount) {
        this.mAccount = linkedAccount;
    }

    private DbxAccountManager getAccountManager() {
        if (mDbxAcctMgr == null) {
            mDbxAcctMgr = DbxAccountManager.getInstance(mContext.getApplicationContext(), APP_KEY, APP_SECRET);
        }
        return mDbxAcctMgr;
    }

    private void assertLinkedAccount(DbxAccountManager accountManager) {
        if (accountManager.hasLinkedAccount()) {
            mAccount = mDbxAcctMgr.getLinkedAccount();
        } else {
            accountManager.startLink(getLinkDropboxAccountActivity(), REQUEST_LINK_TO_DROPBOX);
        }
    }

}
