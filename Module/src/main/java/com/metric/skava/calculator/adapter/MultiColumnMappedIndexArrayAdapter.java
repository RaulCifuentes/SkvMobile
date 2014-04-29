package com.metric.skava.calculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.calculator.model.MappedIndex;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by metricboy on 2/17/14.
 */
public class MultiColumnMappedIndexArrayAdapter<T extends MappedIndex> extends ArrayAdapter<T> {

    private final Context context;
    private final List<T> values;
    private int layoutResourceId;

    public MultiColumnMappedIndexArrayAdapter(Context context, int rowsLayoutResourceId, List<T> objects) {
        super(context, rowsLayoutResourceId, objects);
        this.context = context;
        this.values = objects;
        this.layoutResourceId = rowsLayoutResourceId;
    }

    @Override
    public T getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View wholeRowView = convertView;
        if (wholeRowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            wholeRowView = inflater.inflate(layoutResourceId, parent, false);
        }

        //This are the expeted control for the list style
        CheckedTextView radioText = (CheckedTextView) wholeRowView.findViewById(R.id.checked_first_column_text_view);
        TextView secondColumnText = (TextView) wholeRowView.findViewById(R.id.second_column_text_view);
        TextView thirdColumnText = (TextView) wholeRowView.findViewById(R.id.third_column_text_view);
        TextView fourthColumnText = (TextView) wholeRowView.findViewById(R.id.fourth_column_text_view);

        //This is for the spinner case
        TextView defaultSpinnerTextView = (TextView) wholeRowView.findViewById(android.R.id.text1);

        MappedIndex whatArrrives = values.get(position);
//        String[][] mapa = whatArrrives.getCategoriesAndValues();
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        if (whatArrrives != null) {
            if (radioText != null) {
                radioText.setText(whatArrrives.getKey() + " (" + nf.format(whatArrrives.getValue()) + ")");
            }
            if (secondColumnText != null) {
//                secondColumnText.setText(mapa[1][0]);
                secondColumnText.setText(whatArrrives.getDescription());
            }
            if (thirdColumnText != null) {
//                thirdColumnText.setText(mapa[1][1]);
                thirdColumnText.setText(whatArrrives.getGroupName());
            }
            if (fourthColumnText != null) {
//                fourthColumnText.setText(mapa[1][2]);
                fourthColumnText.setText(whatArrrives.getShortDescription());
            }
            if (defaultSpinnerTextView != null){
//                defaultSpinnerTextView.setText(mapa[1][0]);
                defaultSpinnerTextView.setText(whatArrrives.getShortDescription());
            }

        }

        return wholeRowView;
    }

}
