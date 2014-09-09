package com.metric.skava.app.exception;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.metric.skava.app.util.SkavaConstants;

import java.lang.Thread.UncaughtExceptionHandler;

public class SkavaExceptionHandler implements UncaughtExceptionHandler {

    private FragmentManager mFragmentManager;
    private Context mContext;

    public SkavaExceptionHandler(Context context, FragmentManager fragmentManager) {
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (ex != null){
            ex.printStackTrace();
            String detailMessage = ex.getMessage() !=null ? ex.getMessage(): ex.getCause().getMessage();
            Log.e(SkavaConstants.LOG, detailMessage);
            if (thread!=null){
                showExceptionMessage(thread.getName(), detailMessage);
            }
        }
    }

    private void showExceptionMessage(final String method, final String message) {
        // Create an instance of the dialog fragment and show it
        DialogFragment theDialog = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("I really sorry but an non expected error has ocurred." );
                final String alertMessage = "If following there's message description take a snapshot do a screen capture and send it to Skava Mobile App admin :: "   ;
                final String exceptionDetails = method + "\n" + message;
                builder.setMessage(alertMessage + "\n" +  exceptionDetails);
                // Create the AlertDialog object and return it
                return builder.create();

            }
        };
        // Showing Alert Message
        theDialog.show(mFragmentManager, "exceptionUserDialog");
        Toast.makeText(mContext, "Hola amigos: " + message, Toast.LENGTH_LONG).show();
    }


}