package com.metric.skava.calculator.barton.fragment;

import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.data.dao.DAOFactory;

public class QBartonCalculatorBaseFragment extends SkavaFragment {

    protected DAOFactory daoFactory;

	public QBartonCalculatorBaseFragment() {
        super();
        daoFactory = getDAOFactory();
	}

//    public MappedIndexDataProvider getMappedIndexDataProvider() {
//        return getSkavaActivity().getMappedIndexDataProvider();
//    }


}
