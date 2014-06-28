package com.metric.skava.app.exception;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

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
        ex.printStackTrace();
        String detailMessage = ex.getMessage() !=null ? ex.getMessage(): ex.getCause().getMessage();
        showExceptionMessage(thread.getName(), detailMessage);
    }

    private void showExceptionMessage(final String method, final String message) {
        // Create an instance of the dialog fragment and show it
        DialogFragment theDialog = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("This is embarrasing but an non expected error has ocurred." );
                final String alertMessage = "It's a goog idea to take a snapshot of this message and send this to Skava Mobile App admin :: " ;
                final String exceptionDetails = method + "\n" + message;
                builder.setMessage(alertMessage).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"admin@skavamobile.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, alertMessage);
                        intent.putExtra(Intent.EXTRA_TEXT, exceptionDetails);
                        startActivity(Intent.createChooser(intent, "Send email..."));
                    }
                });
                // Create the AlertDialog object and return it
                return builder.create();
            }
        };
        // Showing Alert Message
        theDialog.show(mFragmentManager, "exceptionUserDialog");
    }


}