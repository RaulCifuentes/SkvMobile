//package com.metric.skava.sync.helper;
//
//import android.app.Activity;
//import android.content.Context;
//
//import com.dropbox.sync.android.DbxDatastore;
//import com.dropbox.sync.android.DbxDatastoreStatus;
//import com.metric.skava.data.dao.DAOFactory;
//import com.metric.skava.data.dao.exception.DAOException;
//import com.metric.skava.data.dao.impl.dropbox.datastore.DatastoreHelper;
//
///**
// * Created by metricboy on 4/3/14.
// */
//public class SyncAgent {
//
//    static SyncAgent instance;
//    private final DAOFactory daoFactory;
//    private final Context mContext;
//
//    public DatastoreHelper getDatastoreHelper() {
//        return datastoreHelper;
//    }
//
//
//    private DatastoreHelper datastoreHelper;
//
//    //calling getIntance for first time could potentially affect the normal flow execution launching a DropBox login activity
//    public static SyncAgent getInstance(Context appContext, Activity dropboxLinkAccountActivity) throws DAOException {
//        if (instance == null) {
//            instance = new SyncAgent(appContext, dropboxLinkAccountActivity);
//        }
//        return instance;
//    }
//
//    private SyncAgent(Context context, Activity dropboxLinkAccountActivity) throws DAOException {
//        mContext = context;
//        daoFactory = DAOFactory.getInstance(mContext);
//        //El datasoreHelper me esta incomodando
////        datastoreHelper = DatastoreHelper.getInstance(mContext, dropboxLinkAccountActivity);
//        //calling wake up could potentially affect the normal flow execution launching a DropBox login activity
////        datastoreHelper.wakeUp();
//    }
//
//    public boolean shouldUpdate() throws DAOException {
//        DbxDatastore datastore = datastoreHelper.getDatastore();
//        DbxDatastoreStatus syncStatus = datastore.getSyncStatus();
//        return syncStatus.hasIncoming || syncStatus.isDownloading;
//    }
//
//    public boolean isAlreadyLinked(){
//        return getDatastoreHelper().getAccountManager().hasLinkedAccount();
//    }
//
//    public void sleep() {
//        datastoreHelper.goSleep();
//    }
//
//
//
//
//
//}
//
