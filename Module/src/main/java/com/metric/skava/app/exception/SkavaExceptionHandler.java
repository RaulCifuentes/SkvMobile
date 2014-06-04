package com.metric.skava.app.exception;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
        showExceptionMessage(thread.getName(), ex.getMessage());
    }

    private void showExceptionMessage(final String method, final String message) {
        // Create an instance of the dialog fragment and show it
        DialogFragment theDialog = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Exception on " + method);
                builder.setMessage(message).setPositiveButton("OK", null);
                // Create the AlertDialog object and return it
                return builder.create();
            }
        };
        // Showing Alert Message
        theDialog.show(mFragmentManager, "exceptionUserDialog");
    }


}