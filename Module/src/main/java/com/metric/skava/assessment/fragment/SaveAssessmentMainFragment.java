package com.metric.skava.assessment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;

/**
 * Created by metricboy on 4/2/14.
 */
public class SaveAssessmentMainFragment extends SkavaFragment {

    private LocalAssessmentDAO mLocalAssessmentDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mLocalAssessmentDAO = DAOFactory.getInstance(getActivity()).getAssessmentDAO(DAOFactory.Flavour.SQLLITE);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.assessment_save_fragment, container, false);
        Button saveBtn = (Button) rootView.findViewById(R.id.save_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mLocalAssessmentDAO.saveDraft(getCurrentAssessment());
                } catch (DAOException e) {
                    Log.e(SkavaConstants.LOG, e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    throw new SkavaSystemException(e);
                }
            }
        });

        Button sendBtn = (Button) rootView.findViewById(R.id.save_and_send_button);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mLocalAssessmentDAO.send(getCurrentAssessment());
                } catch (DAOException e) {
                    Log.e(SkavaConstants.LOG, e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    throw new SkavaSystemException(e);
                }
            }
        });

        return rootView;
    }

}
