package com.metric.skava.app.widget;//package com.metric.skava.app.widget;
//
//import java.util.Calendar;
//
//import android.app.DatePickerDialog;
//import android.app.DatePickerDialog.OnDateSetListener;
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
//
//public class CustomDatePickerFragment extends DialogFragment {
//
//	private Context mContext;
//	private OnDateSetListener mDateSetListener;
//
//	public CustomDatePickerFragment() {
//		super();
//	}
//	
//	public CustomDatePickerFragment(Context mContext, OnDateSetListener mDateSetListener) {
//		this();
//		this.mContext = mContext;
//		this.mDateSetListener = mDateSetListener;
//	}
//
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		// Use the current date as the default date in the picker
//		final Calendar c = Calendar.getInstance();
//		int year = c.get(Calendar.YEAR);
//		int month = c.get(Calendar.MONTH);
//		int day = c.get(Calendar.DAY_OF_MONTH);
//
//		// Create a new instance of DatePickerDialog and return it
//		return new DatePickerDialog(mContext, mDateSetListener, year, month,
//				day);
//	}
//
//}