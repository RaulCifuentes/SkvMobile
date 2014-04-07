package com.metric.skava.calculator.barton.fragment;

import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.calculator.data.MappedIndexDataProvider;
import com.metric.skava.data.dao.DAOFactory;

public class QBartonCalculatorBaseFragment extends SkavaFragment {

    protected DAOFactory daoFactory;

	public QBartonCalculatorBaseFragment() {
        super();
        daoFactory = DAOFactory.getInstance(getActivity());
	}

    public MappedIndexDataProvider getMappedIndexDataProvider() {
        return getSkavaActivity().getMappedIndexDataProvider();
    }


}
