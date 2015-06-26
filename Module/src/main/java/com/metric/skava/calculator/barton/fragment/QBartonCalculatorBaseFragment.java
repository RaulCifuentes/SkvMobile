package com.metric.skava.calculator.barton.fragment;

import android.util.Log;
import android.widget.Toast;

import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.barton.logic.QBartonCalculator;
import com.metric.skava.calculator.barton.logic.QBartonInput;
import com.metric.skava.calculator.barton.logic.QBartonOutput;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.logic.RecomendationProvider;
import com.metric.skava.instructions.model.SupportRecommendation;

public class QBartonCalculatorBaseFragment extends SkavaFragment {

	public QBartonCalculatorBaseFragment() {
        super();
	}

    public void updateQResult(){

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
            QBartonOutput output;
            try {
                output = QBartonCalculator.calculate(input);
                getQCalculationContext().setQResult(output);
                // whenever the Q changes it is necessary to recalculate the support recommendation
                SupportRecommendation supportRecomendation = null;
                getCurrentAssessment().setRecomendation(supportRecomendation);
                RecomendationProvider provider = new RecomendationProvider(getSkavaContext());
                try {
                    supportRecomendation = provider.recomend(getCurrentAssessment());
                } catch (DAOException e) {
                    Log.e(SkavaConstants.LOG, e.getMessage());
                    Toast.makeText(getSkavaActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                getCurrentAssessment().setRecomendation(supportRecomendation);
                //
            } catch (Exception e) {
                Log.e(SkavaConstants.LOG, e.getMessage(), e);
                e.printStackTrace();
            }
        }


    }

}
