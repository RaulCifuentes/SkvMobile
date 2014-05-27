package com.metric.skava.sync.activity;

import android.os.Bundle;
import android.view.Menu;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.sync.fragment.SyncMainFragment;

public class SyncMainActivity extends SkavaFragmentActivity {

    public String FRAGMENT_TAG = "SYNC_FRAGMENT_TAG";

    private SyncMainFragment syncFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SyncMainFragment(), FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sync_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_settings:
//                return true;
//            case R.id.action_sync_clients:
//                syncClients();
//                break;
//            case R.id.action_sync_all:
//                syncAll();
//                break;
//        }
//        try {
//            syncFragment.refreshListViews();
//        } catch (DAOException daoe) {
//            daoe.printStackTrace();
//            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
//            Log.e(SkavaConstants.LOG, daoe.getMessage());
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//
//    private void syncClients() {
//        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//        try {
//            /**Update the excavation methods data*/
//            RemoteClientDAO dropBoxClientDAO = getDAOFactory().getRemoteClientDAO(DAOFactory.Flavour.DROPBOX);
//            LocalClientDAO sqlLiteLocalClientDAO = getDAOFactory().getLocalClientDAO();
//            //Read from DropBox
//            List<Client> downloadedClients = dropBoxClientDAO.getAllClients();
//            for (Client downloadedClient : downloadedClients) {
//                //Write into the SQLLite
//                sqlLiteLocalClientDAO.saveClient(downloadedClient);
//            }
//        } catch (DAOException daoe) {
//            daoe.printStackTrace();
//            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
//            Log.e(SkavaConstants.LOG, daoe.getMessage());
//        }
//    }
//
//
//
//    private void syncAssessments() {
//        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//        try {
//            /**Upload the assessments pending to upload*/
//            Assessment curAssessment = getCurrentAssessment();
//            LocalAssessmentDAO assessmentDAO = getDAOFactory().getLocalAssessmentDAO();
//            assessmentDAO.saveAssessment(curAssessment);
//        } catch (DAOException daoe) {
//            daoe.printStackTrace();
//            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
//            Log.e(SkavaConstants.LOG, daoe.getMessage());
//        }
//    }
//
//
//    private void syncAll() {
//        syncClients();
//    }
//


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == SkavaConstants.REQUEST_LINK_TO_DROPBOX) {
//            if (resultCode == RESULT_OK) {
////                DatastoreHelper datastoreHelper = DatastoreHelper.getInstance(this);
////                //read info from the intent and set the account I guess
////                datastoreHelper.setAccount(null);
//                //just to see if comes here
//                Toast.makeText(this, "AssessmentStageListActivity :: onActivityResult", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this, "Link to Dropbox failed.", Toast.LENGTH_LONG).show();
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

}
