package com.metric.skava.calculator.rmr.fragment;

import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.calculator.data.MappedIndexDataProvider;
import com.metric.skava.data.dao.DAOFactory;

public class RMRCalculatorBaseFragment extends SkavaFragment {

    protected DAOFactory daoFactory;


    public RMRCalculatorBaseFragment() {
		super();
        daoFactory = getDAOFactory();
	}

    public MappedIndexDataProvider getMappedIndexDataProvider(){
        return getSkavaActivity().getMappedIndexDataProvider();
    }


}
