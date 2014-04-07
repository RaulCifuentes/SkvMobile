package com.metric.skava.calculator.barton.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.barton.logic.QBartonCalculator;
import com.metric.skava.calculator.barton.logic.QBartonInput;
import com.metric.skava.calculator.barton.logic.QBartonOutput;
import com.metric.skava.calculator.barton.model.Q_Calculation;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class QResultsFragment extends QBartonCalculatorBaseFragment {


    public QResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot =
                inflater.inflate(
                        R.layout.calculator_qbarton_results_fragment, container,
                        false);

        Typeface iconTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Android-Dev-Icons-1.ttf");

        TextView icon = (TextView) viewRoot.findViewById(R.id.rqd_jn_label_icon);
        icon.setTypeface(iconTypeFace);
        icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        icon = (TextView) viewRoot.findViewById(R.id.jr_ja_label_icon);
        icon.setTypeface(iconTypeFace);
        icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        icon = (TextView) viewRoot.findViewById(R.id.jw_srf_label_icon);
        icon.setTypeface(iconTypeFace);
        icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        icon = (TextView) viewRoot.findViewById(R.id.qbarton_label_icon);
        icon.setTypeface(iconTypeFace);
        icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View resultView = view;

        TextView rdqOverJnTextView = (TextView) resultView
                .findViewById(R.id.qresults_rqdoverjn);
        TextView jrOverJaTextView = (TextView) resultView
                .findViewById(R.id.qresults_jroverja);
        TextView jwOverSrfTextView = (TextView) resultView
                .findViewById(R.id.qresults_jwoversrf);
        TextView qBartonTextView = (TextView) resultView
                .findViewById(R.id.qresults_qbarton);

        Q_Calculation QCalculation = getQCalculationContext();
        QBartonInput input = new QBartonInput();
        input.setRqd(QCalculation.getRqd().getValue());
        input.setJn(QCalculation.getJn().getValue());
        input.setJr(QCalculation.getJr().getValue());
        input.setJa(QCalculation.getJa().getValue());
        input.setJw(QCalculation.getJw().getValue());
        input.setSrf(QCalculation.getSrf().getValue());
        NumberFormat nf = DecimalFormat.getInstance();

        if (input.isComplete()) {
            QBartonOutput output;
            try {
                output = QBartonCalculator.calculate(input);
                getQCalculationContext().setQResult(output);
                rdqOverJnTextView.setText(nf.format(output.getRqdOverJn()));
                jrOverJaTextView
                        .setText(nf.format(output.getJrOverJa()));
                jwOverSrfTextView.setText(nf.format(output.getJwOverSRF()));
                qBartonTextView.setText(nf.format(output.getQBarton()));
            } catch (Exception e) {
                Log.e(SkavaConstants.LOG, e.getMessage(), e);
                e.printStackTrace();
            }
        }


    }

}
