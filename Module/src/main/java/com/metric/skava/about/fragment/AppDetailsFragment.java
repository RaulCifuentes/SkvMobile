package com.metric.skava.about.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.sync.model.SyncStatus;

public class AppDetailsFragment extends SkavaFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_app_details_fragment, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) view.findViewById(R.id.textView00);
        textView.setText("General Status");

        SyncStatus metadata = getSkavaContext().getAppDataSyncMetadata();
        textView = (TextView) view.findViewById(R.id.textView01);
//        String dateAsString = DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, metadata.getLastExecution());
        textView.setText("Last App Data Sync :: " + (metadata.isSuccess() ? "Succeed" : "Failed") + " on " + metadata.getLastExecution());

        metadata = getSkavaContext().getUserDataSyncMetadata();
        TextView textView2 = (TextView) view.findViewById(R.id.textView02);
        textView2.setText("User Data Sync :: " + (metadata.isSuccess()?"Succeed" :"Failed") +  " on " + metadata.getLastExecution());

        textView = (TextView) view.findViewById(R.id.textView03);
        textView.setText("Settings");

        SkavaApplication skavaApplication = (SkavaApplication) getSkavaActivity().getApplication();
        boolean unlink = skavaApplication.isUnlinkPrefered();
        TextView textView3 = (TextView) view.findViewById(R.id.textView04);
        textView3.setText("Unlink from Dropbox on app close :: " + unlink);

        String target = getSkavaContext().getTargetEnvironment();
        TextView textView4 = (TextView) view.findViewById(R.id.textView05);

        if (target.equalsIgnoreCase(SkavaConstants.DEV_KEY)) {
            target = "Development";
        } else if (target.equalsIgnoreCase(SkavaConstants.QA_KEY)) {
            target = "Quality Assurance";
        } else if (target.equalsIgnoreCase(SkavaConstants.PROD_KEY)) {
            target = "Production";
        }

        textView4.setText("Target environment:: " + target);

//        boolean reloadAppData = skavaApplication.isReloadAppDataPrefered();
        TextView textView5 = (TextView) view.findViewById(R.id.textView06);
//        textView5.setText("Dropbox reload app data :: " + reloadAppData);

//        boolean reloadUserData = skavaApplication.isReloadUserDataPrefered();
        TextView textView6 = (TextView) view.findViewById(R.id.textView07);
//        textView6.setText("Dropbox reload user data :: " + reloadUserData);


    }
}
