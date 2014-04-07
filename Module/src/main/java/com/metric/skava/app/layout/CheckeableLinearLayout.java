package com.metric.skava.app.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by metricboy on 2/17/14.
 */
public class CheckeableLinearLayout extends LinearLayout implements Checkable {
    private Checkable _checkbox;

    public CheckeableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        // find checked text view
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; ++i) {
//            View v = getChildAt(i);
//            if (v instanceof CheckedTextView) {
//                _checkbox = (CheckedTextView)v;
//            }
//        }
//    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i += 1) {
            final View view = getChildAt(i);

            if (view != null && Checkable.class.isAssignableFrom(view.getClass())) {
                _checkbox = (Checkable) view;
            }
        }
    }


    @Override
    public boolean isChecked() {
        return _checkbox != null ? _checkbox.isChecked() : false;
    }

    @Override
    public void setChecked(boolean checked) {
        if (_checkbox != null) {
            _checkbox.setChecked(checked);
        }
    }

    @Override
    public void toggle() {
        if (_checkbox != null) {
            _checkbox.toggle();
        }
    }
}
