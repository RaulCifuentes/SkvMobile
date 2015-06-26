package com.metric.skava.calculator.barton.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.barton.logic.QBartonCalculator;
import com.metric.skava.calculator.barton.logic.QBartonInput;
import com.metric.skava.calculator.barton.logic.QBartonOutput;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.logic.RecomendationProvider;
import com.metric.skava.instructions.model.SupportRecommendation;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class QResultsFragment extends QBartonCalculatorBaseFragment {

    boolean showResultsView;

    public QResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = null;
        showResultsView = true;

        Q_Calculation qCalculation = getQCalculationContext();

        QBartonInput input = new QBartonInput();
        if (qCalculation.getRqd() != null) {
            input.setRqd(qCalculation.getRqd().getValue());
        }
        if (qCalculation.getJn() != null) {
            input.setJn(qCalculation.getJn().getValue());
        }
        if (qCalculation.getJr() != null) {
            input.setJr(qCalculation.getJr().getValue());
        }
        if (qCalculation.getJa() != null) {
            input.setJa(qCalculation.getJa().getValue());
        }
        if (qCalculation.getJw() != null) {
            input.setJw(qCalculation.getJw().getValue());
        }
        if (qCalculation.getSrf() != null) {
            input.setSrf(qCalculation.getSrf().getValue());
        }
        if (qCalculation.getIsIntersection() != null){
            input.setIntersection(qCalculation.getIsIntersection().booleanValue());
        }
        if (input.isComplete()) {
            // Inflate the layout for this fragment
            viewRoot = inflater.inflate(R.layout.calculator_qbarton_results_fragment, container,
                    false);

            TextView rdqOverJnTextView = (TextView) viewRoot
                    .findViewById(R.id.qresults_rqdoverjn);
            TextView jrOverJaTextView = (TextView) viewRoot
                    .findViewById(R.id.qresults_jroverja);
            TextView jwOverSrfTextView = (TextView) viewRoot
                    .findViewById(R.id.qresults_jwoversrf);
            TextView qBartonTextView = (TextView) viewRoot
                    .findViewById(R.id.qresults_qbarton);

            NumberFormat nf = DecimalFormat.getInstance();
            QBartonOutput output;
            try {
                output = QBartonCalculator.calculate(input);
                getQCalculationContext().setQResult(output);

                // whenever the Q changes it is necessary to reclaculate the support recommendation
                SupportRecommendation supportRecomendation = null;
                RecomendationProvider provider = new RecomendationProvider(getSkavaContext());
                try {
                    supportRecomendation = provider.recomend(getCurrentAssessment());
                } catch (DAOException e) {
                    Log.e(SkavaConstants.LOG, e.getMessage());
                    Toast.makeText(getSkavaActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                getCurrentAssessment().setRecomendation(supportRecomendation);
                //

                rdqOverJnTextView.setText(nf.format(output.getRqdOverJn()));
                jrOverJaTextView
                        .setText(nf.format(output.getJrOverJa()));
                jwOverSrfTextView.setText(nf.format(output.getJwOverSRF()));
                qBartonTextView.setText(nf.format(output.getQBarton()));
            } catch (Exception e) {
                Log.e(SkavaConstants.LOG, e.getMessage(), e);
                e.printStackTrace();
            }

        } else {
            showResultsView = false;
            viewRoot = inflater.inflate(R.layout.calculator_qbarton_missing_input_fragment, container,
                    false);

            NumberFormat nf = DecimalFormat.getInstance();

            TextView rdqTextView = (TextView) viewRoot.findViewById(R.id.qresults_rqd);
            rdqTextView.setText(qCalculation.getRqd() != null ? nf.format(input.getRqd()) : "Not yet selected");
            TextView jnTextView = (TextView) viewRoot.findViewById(R.id.qresults_jn);
            jnTextView.setText(qCalculation.getJn() != null ? nf.format(input.getJn()) : "Not yet selected");
            TextView jrTextView = (TextView) viewRoot.findViewById(R.id.qresults_jr);
            jrTextView.setText(qCalculation.getJr() != null ? nf.format(input.getJr()) : "Not yet selected");
            TextView jaTextView = (TextView) viewRoot.findViewById(R.id.qresults_ja);
            jaTextView.setText(qCalculation.getJa() != null ? nf.format(input.getJa()) : "Not yet selected");
            TextView jwTextView = (TextView) viewRoot.findViewById(R.id.qresults_jw);
            jwTextView.setText(qCalculation.getJw() != null ? nf.format(input.getJw()) : "Not yet selected");
            TextView srfTextView = (TextView) viewRoot.findViewById(R.id.qresults_srf);
            srfTextView.setText(qCalculation.getSrf() != null ? nf.format(input.getSrf()) : "Not yet selected");
        }
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Typeface iconTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Android-Dev-Icons-1.ttf");
        //find out the view
        if (showResultsView){
            TextView icon = (TextView) view.findViewById(R.id.rqd_jn_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon = (TextView) view.findViewById(R.id.jr_ja_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon = (TextView) view.findViewById(R.id.jw_srf_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon = (TextView) view.findViewById(R.id.qbarton_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        } else {
            TextView icon = (TextView) view.findViewById(R.id.rqd_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon = (TextView) view.findViewById(R.id.jn_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon = (TextView) view.findViewById(R.id.jr_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon = (TextView) view.findViewById(R.id.ja_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon = (TextView) view.findViewById(R.id.jw_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            icon = (TextView) view.findViewById(R.id.srf_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }

    }

}
