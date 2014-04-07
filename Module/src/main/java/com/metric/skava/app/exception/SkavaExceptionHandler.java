package com.metric.skava.app.exception;

import android.content.Context;

import java.lang.Thread.UncaughtExceptionHandler;

public class SkavaExceptionHandler implements UncaughtExceptionHandler 
{       
	private Context mContext;

	public SkavaExceptionHandler(Context context){
		this.mContext = context;
	}
	
    @Override
    public void uncaughtException(Thread thread, Throwable ex)
    {
        ex.printStackTrace();
        showExceptionMessage(thread.getName(), ex.getMessage());
    }
    
    private void showExceptionMessage(String method, String message)
    {
        // Create an instance of the dialog fragment and show it

    }

//        Log.d(SkavaConstants.LOG, " SkavaExceptionHandler >>  " + method  + " " + message);
//        AlertDialog.Builder messageBox = new AlertDialog.Builder(mContext);
//        messageBox.setTitle(method);
//        messageBox.setMessage(message);
//        messageBox.setCancelable(false);
//        messageBox.setNeutralButton("OK", null);
//        messageBox.show();
//    }
    
}