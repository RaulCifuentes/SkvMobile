package com.metric.skava.data.dao.impl.dropbox.datastore;

/**
 * Created by metricboy on 3/6/14.
 */
public class DatastoreHelper {
    // **************** DS RAUL ***********
//    private static final String APP_SECRET = "pc2wwbqnblh0r53";
//    private static final String APP_KEY = "bzsvwf0odnoslis";

    // **************** DS FABIAN ***********
    public static final String APP_SECRET = "rjfwy6os8tbpgin";
    public static final String APP_KEY = "m375gz1gq6k8vv1";

    public static final String APP_DATASTORE_NAME = "skavatunnelapp";

    public static final int REQUEST_LINK_TO_DROPBOX = 510;

//    private static DatastoreHelper instance;
//
//    private Context mApplicationContext;
//    private SkavaContext mSkavaContext;
//
//    private DbxAccountManager mDbxAcctMgr;
//    private DbxAccount mAccount;
//    private DbxDatastoreManager mDatastoreManager;
//    private DbxDatastore mDatastore;

//    public static DatastoreHelper getInstance(Context applicationContext, SkavaContext skavaContext) {
//        if (instance == null) {
//            instance = new DatastoreHelper(applicationContext, skavaContext);
//        }
//        return instance;
//    }
//
//    //Abrir el acount manager que Dropbox ofrece para esta app
//    setUpAccountManager();
//    //Connect y tener la referencia al account
//    if (!mDbxAcctMgr.hasLinkedAccount()) {
//        myAccountManager.startLink(this, DatastoreHelper.REQUEST_LINK_TO_DROPBOX);
//    } else {
        //Con eso obtener la lista de Datastores

        //Abrir el datastore

//
//
//        private DatastoreHelper(Context applicationContext, SkavaContext skavaContext) {
//        this.mApplicationContext = applicationContext;
//        this.mSkavaContext = skavaContext;
//    }
//
//    private void initAccountManager(){
//        mDbxAcctMgr = DbxAccountManager.getInstance(mApplicationContext, DatastoreHelper.APP_KEY, DatastoreHelper.APP_SECRET);
//    }
//
//    private void initUpAccount(){
//        mDbxAcctMgr = DbxAccountManager.getInstance(mApplicationContext, DatastoreHelper.APP_KEY, DatastoreHelper.APP_SECRET);
//    }
//
//    private void initDatastoreManager() throws DbxException.Unauthorized {
//        mDatastoreManager = DbxDatastoreManager.forAccount(mAccount);
//    }
//
//
//    public DbxDatastore openDatastore(DbxAccount account, DbxDatastore datastore) throws DbxException {
//        if (mDatastore != null) {
//            if (mDatastore.isOpen()){
//                return mDatastore;
//            } else {
//                mDatastore = mDatastoreManager.openDatastore(APP_DATASTORE_NAME);
//            }
//        } else {
//            return false;
//        }
//    }
//
//    public DbxDatastore getDatastore() throws DAOException {
//        return mDatastore;
//    }
//
//    private DbxDatastore.SyncStatusListener mDatastoreListener = new DbxDatastore.SyncStatusListener() {
//        @Override
//        public void onDatastoreStatusChange(DbxDatastore ds) {
//            Log.d(SkavaConstants.LOG, "SYNC STATUS: " + ds.getSyncStatus().toString());
//            if (ds.getSyncStatus().hasIncoming) {
//                try {
//                    mDatastore.sync();
//                } catch (DbxException e) {
//                    Log.e(SkavaConstants.LOG, e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        }
//    };
//
//    /*
//    This method will assert a DropBox account is linked, so if not it will launch the DropBox login form
//    causing eventual delay affecting the normal flow o the app
//     */
//    public void wakeUp() throws DAOException {
//        try {
//
//            assertLinkedAccount(getAccountManager());
//            if (mAccount == null) {
//                mAccount = getAccountManager().getLinkedAccount();
//            }
//            if (mAccount != null) {
//                if (mDatastore == null || !mDatastore.isOpen()) {
////                    mDatastoreManager = DbxDatastoreManager.forAccount(mAccount);
//                    /// TODO use a non default datastore
////                mDatastore = mDatastoreManager.openDatastore(APP_DATASTORE_NAME);
//                    mDatastore = DbxDatastore.openDefault(mAccount);
//                    mDatastore.sync();
//                    mDatastore.addSyncStatusListener(mDatastoreListener);
//                }
//            } else {
//                throw new DAOException("Failed getting linked Dropbox user account !! ");
//            }
//        } catch (DbxException e) {
//            throw new DAOException(e);
//        }
//    }
//
//    public void goSleep() {
//        mDatastore.removeSyncStatusListener(mDatastoreListener);
//        mDatastore.close();
//    }
//
//
//    /**
//     * This is the method that onActivityResult handling REQUEST_LINK_TO_DROPBOX must call
//     *
//     * @param linkedAccount
//     */
//    public void setAccount(DbxAccount linkedAccount) {
//        this.mAccount = linkedAccount;
//    }
//
//    public DbxAccountManager getAccountManager() {
//        //        Commenting out just to prevent the use of old saved dropbox accounts
////        if (mDbxAcctMgr == null) {
//            mDbxAcctMgr = DbxAccountManager.getInstance(mContext.getApplicationContext(), APP_KEY, APP_SECRET);
////        }
//        return mDbxAcctMgr;
//    }
//
//    public void assertLinkedAccount(DbxAccountManager accountManager) {
//        //        Commenting out just to prevent the use of old saved dropbox accounts
//        if (accountManager.hasLinkedAccount()) {
//            mAccount = mDbxAcctMgr.getLinkedAccount();
//        } else {
//            accountManager.startLink(getLinkDropboxAccountActivity(), REQUEST_LINK_TO_DROPBOX);
//        }
//    }

}
