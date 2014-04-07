package com.metric.skava.app.exception.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by metricboy on 3/5/14.
 */
public class ExceptionHandlerDialog extends DialogFragment  {

    // Use this instance of the interface to deliver action events
    private LoadAssessmentDialogListener mCallbackListener;

    private Context mContext;

    private String where;
    private String message;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface LoadAssessmentDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }


    public ExceptionHandlerDialog() {
        super();
    }


    public ExceptionHandlerDialog(Context mContext) {
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
//        try {
////            this.assessmentDAO = new AssessmentDAODropboxImpl(mContext);
//
//        } catch (DAOException daoe) {
//            daoe.printStackTrace();
//            Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG).show();
//            Log.e(SkavaConstants.LOG, daoe.getMessage());
//        }
////        this.mAssessmentAdapter = new AssessmentListAdapter(mContext);
//        updateAssessmentList();
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
//        builder.setTitle("Select a previous mapping");
//        if (mAssessmentAdapter == null) {
//            mAssessmentAdapter = new AssessmentListAdapter(mContext);
//        }

        dialogBuilder.setTitle(getWhere());
        dialogBuilder.setMessage(getMessage());
        dialogBuilder.setCancelable(false);
        dialogBuilder.setNeutralButton("OK", null);

//        updateAssessmentList();
//        builder.setCancelable(false);
//        builder.setAdapter(mAssessmentAdapter, this);
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
        AlertDialog alertDialog = dialogBuilder.create();
        return alertDialog;
    }


//    public void updateAssessmentList() {
//        List<Assessment> updatedList = null;
//        try {
//            updatedList = assessmentDAO.getAllAssessments();
//        } catch (DAOException daoe) {
//            daoe.printStackTrace();
//            Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG).show();
//            Log.e(SkavaConstants.LOG, daoe.getMessage());
//        }
//        if (mAssessmentAdapter != null) {
//            mAssessmentAdapter.setAssessmentList(updatedList);
//            mAssessmentAdapter.notifyDataSetChanged();
//        }
//    }


//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//        //hanle the click on the to make this the current assessment
//        //Assessment selected = mAssessmentAdapter.getItem(which);
//        //Build an Assessment from the AssessmentDTO and asssociates
//        //with the SkavaContext
////        ExcavationProject project = selected.getProject();
////        Tunnel tunnel = selected.getTunnel();
//        Toast.makeText(getActivity(), selected.toString(), Toast.LENGTH_LONG).show();
//
//    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == DatastoreHelper.REQUEST_LINK_TO_DROPBOX) {
//            if (resultCode == Activity.RESULT_OK) {
//                Toast.makeText(getActivity(), "Existing AssessmentDialog :: onActivityResult", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getActivity(), "Link to Dropbox failed.", Toast.LENGTH_LONG).show();
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

}
