package com.metric.skava.assessment.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.assessment.dialog.adapter.AssessmentListAdapter;
import com.metric.skava.data.dao.RemoteAssessmentDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.AssessmentDAODropboxImpl;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public class ExistingAssessmentDialog extends DialogFragment implements DialogInterface.OnClickListener {

    // Use this instance of the interface to deliver action events
    private LoadAssessmentDialogListener mCallbackListener;

    private Context mContext;

    private RemoteAssessmentDAO mRemoteAssessmentDAO;

    private AssessmentListAdapter mAssessmentAdapter;


    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface LoadAssessmentDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }


    public ExistingAssessmentDialog(Context mContext) {
        this.mContext = mContext;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        // Verify that the host activity implements the callback interface
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            mCallbackListener = (LoadAssessmentDialogListener) activity;
//        } catch (ClassCastException e) {
//            // The activity doesn't implement the interface, throw exception
//            throw new ClassCastException(activity.toString()
//                    + " must implement LoadAssessmentDialogListener");
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.mRemoteAssessmentDAO = new AssessmentDAODropboxImpl(mContext, null);
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
        this.mAssessmentAdapter = new AssessmentListAdapter(mContext);
        updateAssessmentList();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Select a previous mapping");
//        if (mAssessmentAdapter == null) {
//            mAssessmentAdapter = new AssessmentListAdapter(mContext);
//        }
//        updateAssessmentList();
        builder.setCancelable(false);
        builder.setAdapter(mAssessmentAdapter, this);
//        builder.setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                mCallbackListener.onDialogPositiveClick(ExistingAssessmentDialog.this);
//            }
//        });
//        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.dismiss();
//            }
//        });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }


    public void updateAssessmentList() {
        List<Assessment> updatedList = null;
        try {
            updatedList = mRemoteAssessmentDAO.getAllAssessments();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
        if (mAssessmentAdapter != null) {
            mAssessmentAdapter.setAssessmentList(updatedList);
            mAssessmentAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        //hanle the click on the to make this the current assessment
        Assessment selected = mAssessmentAdapter.getItem(which);
        //Build an Assessment from the AssessmentDTO and asssociates
        //with the SkavaContext
//        ExcavationProject project = selected.getProject();
//        Tunnel tunnel = selected.getTunnel();
        Toast.makeText(getActivity(), selected.toString(), Toast.LENGTH_LONG).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SkavaConstants.REQUEST_LINK_TO_DROPBOX) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getActivity(), "Existing AssessmentDialog :: onActivityResult", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Link to Dropbox failed.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
