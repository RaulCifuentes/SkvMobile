package com.metric.skava.app.helper;

import android.app.AlertDialog;

import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.sync.model.SyncTask;

/**
 * Created by metricboy on 7/8/14.
 */
public class ImportAppAndUserDataModelTask extends ImportAppDataModelTask {


    public ImportAppAndUserDataModelTask(SkavaContext context, SkavaFragmentActivity activity) {
        super(context, activity);
    }

    protected void onPostExecute(Long result) {
        if (errorCondition == null) {
            mActivity.onPostExecuteImportAppData(true, result);
            //as the import of app data finished gracefully, it could now start with the import of user data
            SyncTask.Domain[] syncTarget = new SyncTask.Domain[]{SyncTask.Domain.ALL_USER_DATA_TABLES};
            ImportUserDataModelTask dynamicDataTask = new ImportUserDataModelTask(mContext, mActivity);
            dynamicDataTask.execute(syncTarget);
        } else {
            mActivity.onPostExecuteImportAppData(false, result);
            AlertDialog.Builder messageBox = new AlertDialog.Builder(mActivity);
            messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
            messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
            messageBox.setCancelable(false);
            messageBox.setNeutralButton("OK", null);
            messageBox.show();
        }
    }

}