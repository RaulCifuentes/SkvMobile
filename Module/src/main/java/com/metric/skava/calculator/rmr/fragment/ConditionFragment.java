package com.metric.skava.calculator.rmr.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.ViewUtils;
import com.metric.skava.calculator.adapter.MultiColumnMappedIndexArrayAdapter;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

//public class ConditionFragment extends RMRCalculatorBaseFragment implements RadioGroup.OnCheckedChangeListener {
public class ConditionFragment extends RMRCalculatorBaseFragment {

    private View generalHeaderView;
    private View persistenceHeaderView;
    private View apertureHeaderView;
    private View roghnessHeaderView;
    private View infillingHeaderView;
    private View weatheringHeaderView;

    private View mSummarizedConditionViewContainer;
    private View mDetailedConditionViewContainer;

    private ListView summarizedListview;
    private ListView persistenceListView;
    private ListView roughnessListView;
    private ListView apertureListView;
    private ListView infillingListView;
    private ListView weatheringListView;

//    private ConditionDiscontinuities selectedConditions;
    private Persistence selectedPersistence;
    private Aperture selectedAperture;
    private Roughness selectedRoughness;
    private Infilling selectedInfilling;
    private Weathering selectedWeathering;
    private RadioGroup radioGroup;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        generalHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        persistenceHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        infillingHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        roghnessHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        apertureHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        weatheringHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);

//        selectedConditions = getRMRCalculationContext().getConditionDiscontinuities();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_rmr_condition_discontinuities_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.title_section_condition));

//        setupGeneralConditionListView();
        setupPersistenceListView();
        setupApertureListView();
        setupRoughnessListView();
        setupInfillingListView();
        setupWeatheringListView();

//        mSummarizedConditionViewContainer = view.findViewById(R.id.listview_summarized_conditions);
//        mSummarizedConditionViewContainer.setVisibility(View.GONE);
        mDetailedConditionViewContainer = view.findViewById(R.id.listview_detailed_conditions);
//        mDetailedConditionViewContainer.setVisibility(View.GONE);
        mDetailedConditionViewContainer.setVisibility(View.VISIBLE);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
//        radioGroup.setOnCheckedChangeListener(this);

        //selects the tunnels as default if theres no previous info
//        if (selectedConditions != null) {
//            radioGroup.check(R.id.radioButtonSummarized);
//        } else {
//            radioGroup.check(R.id.radioButtonDetailed);
//        }

    }

//    //summarized table
//    private void setupGeneralConditionListView() {
//        //general global condition
//        summarizedListview = (ListView) getView().findViewById(R.id.listview_summarized_conditions);
//
////        final List<ConditionDiscontinuities> listConditions = getMappedIndexDataProvider().getAllConditions();
//        final List<ConditionDiscontinuities> listConditions;
//        try {
//            listConditions = daoFactory.getLocalConditionDiscontinuitiesDAO().getAllConditions();
//        } catch (DAOException e){
//            Log.e(SkavaConstants.LOG, e.getMessage());
//            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
//            throw new SkavaSystemException(e);
//        }
//
//        final MultiColumnMappedIndexArrayAdapter<ConditionDiscontinuities> adapter = new MultiColumnMappedIndexArrayAdapter<ConditionDiscontinuities>(getSkavaActivity(),
//                R.layout.calculator_two_column_list_view_row_checked_radio, listConditions);
//
//        TextView firstTextView = (TextView) generalHeaderView.findViewById(R.id.first_column_text_view);
//        TextView secondTextView = (TextView) generalHeaderView.findViewById(R.id.second_column_text_view);
//        secondTextView.setText(ConditionDiscontinuities.DESCRIPTION);
//
//        summarizedListview.addHeaderView(generalHeaderView, null, false);
//        final int numberOfHeaders = summarizedListview.getHeaderViewsCount();
//        summarizedListview.setAdapter(adapter);
//
//        selectedConditions = getRMRCalculationContext().getConditionDiscontinuities();
//        if (selectedConditions != null) {
//            int posIndex = adapter.getPosition(selectedConditions);
//            if (posIndex != -1) {
//                posIndex += numberOfHeaders;
//                summarizedListview.setItemChecked(posIndex, true);
//                summarizedListview.setSelection(posIndex);
//            }
//        }
//
//        summarizedListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                selectedConditions = (ConditionDiscontinuities) parent.getItemAtPosition(position);
//                getRMRCalculationContext().setConditionDiscontinuities(selectedConditions);
//                //transform this into a separated version
//                radioGroup.check(R.id.radioButtonDetailed);
//                if (selectedConditions != null) {
//
//
//                    Integer summarizedKey = selectedConditions.getValue().intValue();
//                    int firstIndex = 1;
//                    switch (summarizedKey) {
//                        //Find the persistence object through a DAO
//                        case 30:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex);
//                            break;
//
//                        case 25:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 1);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 1);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 1);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 1);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 1);
//                            break;
//
//                        case 20:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 2);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 2);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 2);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 2);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 2);
//                            break;
//
//                        case 10:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 3);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 3);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 3);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 3);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 3);
//                            break;
//
//                        case 0:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 4);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 4);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 4);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 4);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 4);
//                            break;
//
//                    }
//                    getRMRCalculationContext().setWeathering(selectedWeathering);
//                    setupWeatheringListView();
//                    getRMRCalculationContext().setPersistence(selectedPersistence);
//                    setupPersistenceListView();
//                    getRMRCalculationContext().setAperture(selectedAperture);
//                    setupApertureListView();
//                    getRMRCalculationContext().setRoughness(selectedRoughness);
//                    setupRoughnessListView();
//                    getRMRCalculationContext().setInfilling(selectedInfilling);
//                    setupInfillingListView();
//                    getRMRCalculationContext().setConditionDiscontinuities(null);
//                    setupGeneralConditionListView();
//                }
//                mSummarizedConditionViewContainer.setVisibility(View.GONE);
//                summarizedListview.clearChoices();
//                selectedConditions = null;
//                mDetailedConditionViewContainer.setVisibility(View.VISIBLE);
//
//            }
//
//        });
//
//        //This method is necessary only to use a ListView inside a ScrollView
//        ViewUtils.adjustListViewHeightBasedOnChildren(summarizedListview);
//    }

    private void setupPersistenceListView() {
        persistenceListView = (ListView) getView().findViewById(R.id.listviewPersistence);
//        final List<Persistence> listConditions = getMappedIndexDataProvider().getAllPersistences();
        final List<Persistence> listPersistences;
        try {
            listPersistences = getDAOFactory().getLocalPersistenceDAO().getAllPersistences();
        } catch (DAOException e){
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        final MultiColumnMappedIndexArrayAdapter<Persistence> adapter = new MultiColumnMappedIndexArrayAdapter<Persistence>(getSkavaActivity(),
                R.layout.calculator_two_column_list_view_row_checked_radio, listPersistences);

        TextView firstTextView = (TextView) persistenceHeaderView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) persistenceHeaderView.findViewById(R.id.second_column_text_view);
        secondTextView.setText("Length (Persistence)");

        persistenceListView.addHeaderView(persistenceHeaderView, null, false);
        final int numberOfHeaders = persistenceListView.getHeaderViewsCount();
        persistenceListView.setAdapter(adapter);

        selectedPersistence = getRMRCalculationContext().getPersistence();
        if (selectedPersistence != null) {
            int posIndex = adapter.getPosition(selectedPersistence);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                persistenceListView.setItemChecked(posIndex, true);
                persistenceListView.setSelection(posIndex);
            }
        }

        persistenceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedPersistence = (Persistence) parent.getItemAtPosition(position);
                getRMRCalculationContext().setPersistence(selectedPersistence);
            }

        });
        //This method is necessary only to use a ListView inside a ScrollView
        ViewUtils.adjustListViewHeightBasedOnChildren(persistenceListView);
    }


    private void setupApertureListView() {
        apertureListView = (ListView) getView().findViewById(R.id.listviewAperture);
//        final List<Aperture> listConditions = getMappedIndexDataProvider().getAllApertures();
        final List<Aperture> listApertures;
        try {
            listApertures = getDAOFactory().getLocalApertureDAO().getAllApertures();
        } catch (DAOException e){
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        final MultiColumnMappedIndexArrayAdapter<Aperture> adapter = new MultiColumnMappedIndexArrayAdapter<Aperture>(getSkavaActivity(),
                R.layout.calculator_two_column_list_view_row_checked_radio, listApertures);

//        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) apertureHeaderView.findViewById(R.id.second_column_text_view);
        secondTextView.setText("Separation (Aperture)");

        apertureListView.addHeaderView(apertureHeaderView, null, false);
        final int numberOfHeaders = apertureListView.getHeaderViewsCount();
        apertureListView.setAdapter(adapter);

        selectedAperture = getRMRCalculationContext().getAperture();
        if (selectedAperture != null) {
            int posIndex = adapter.getPosition(selectedAperture);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                apertureListView.setItemChecked(posIndex, true);
                apertureListView.setSelection(posIndex);
            }
        }

        apertureListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedAperture = (Aperture) parent.getItemAtPosition(position);
                getRMRCalculationContext().setAperture(selectedAperture);
            }

        });

        //This method is necessary only to use a ListView inside a ScrollView
        ViewUtils.adjustListViewHeightBasedOnChildren(apertureListView);

    }


    private void setupRoughnessListView() {
        roughnessListView = (ListView) getView().findViewById(R.id.listviewRoughtness);
//        final List<Roughness> listRoughness = getMappedIndexDataProvider().getAllRoughness();
        final List<Roughness> listRoughness;
        try {
            listRoughness = getDAOFactory().getLocalRoughnessDAO().getAllRoughnesses();
        } catch (DAOException e){
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        final MultiColumnMappedIndexArrayAdapter<Roughness> adapter = new MultiColumnMappedIndexArrayAdapter<Roughness>(getSkavaActivity(),
                R.layout.calculator_two_column_list_view_row_checked_radio, listRoughness);

        TextView firstTextView = (TextView) roghnessHeaderView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) roghnessHeaderView.findViewById(R.id.second_column_text_view);
        secondTextView.setText("Roughness");

        roughnessListView.addHeaderView(roghnessHeaderView, null, false);
        final int numberOfHeaders = roughnessListView.getHeaderViewsCount();
        roughnessListView.setAdapter(adapter);

        selectedRoughness = getRMRCalculationContext().getRoughness();
        if (selectedRoughness != null) {
            int posIndex = adapter.getPosition(selectedRoughness);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                roughnessListView.setItemChecked(posIndex, true);
                roughnessListView.setSelection(posIndex);
            }
        }

        roughnessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedRoughness = (Roughness) parent.getItemAtPosition(position);
                getRMRCalculationContext().setRoughness(selectedRoughness);
            }

        });

        //This method is necessary only to use a ListView inside a ScrollView
        ViewUtils.adjustListViewHeightBasedOnChildren(roughnessListView);
    }

    private void setupInfillingListView() {
        infillingListView = (ListView) getView().findViewById(R.id.listviewInfilling);
//        final List<Infilling> listInfillings = getMappedIndexDataProvider().getAllInfillings();
        final List<Infilling> listInfillings;
        try {
            listInfillings = getDAOFactory().getLocalInfillingDAO().getAllInfillings();
        } catch (DAOException e){
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        final MultiColumnMappedIndexArrayAdapter<Infilling> adapter = new MultiColumnMappedIndexArrayAdapter<Infilling>(getSkavaActivity(),
                R.layout.calculator_two_column_list_view_row_checked_radio, listInfillings);

        TextView firstTextView = (TextView) infillingHeaderView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) infillingHeaderView.findViewById(R.id.second_column_text_view);
        secondTextView.setText("Infilling (Gouge)");

        infillingListView.addHeaderView(infillingHeaderView, null, false);
        final int numberOfHeaders = infillingListView.getHeaderViewsCount();
        infillingListView.setAdapter(adapter);

        selectedInfilling = getRMRCalculationContext().getInfilling();
        if (selectedInfilling != null) {
            int posIndex = adapter.getPosition(selectedInfilling);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                infillingListView.setItemChecked(posIndex, true);
                infillingListView.setSelection(posIndex);
            }
        }

        infillingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedInfilling = (Infilling) parent.getItemAtPosition(position);
                getRMRCalculationContext().setInfilling(selectedInfilling);
            }

        });

        //This method is necessary only to use a ListView inside a ScrollView
        ViewUtils.adjustListViewHeightBasedOnChildren(infillingListView);
    }

    private void setupWeatheringListView() {
        weatheringListView = (ListView) getView().findViewById(R.id.listviewWeathering);
//        final List<Weathering> listWeatherings = getMappedIndexDataProvider().getAllWeatherings();
        final List<Weathering> listWeatherings;
        try {
            listWeatherings = getDAOFactory().getLocalWeatheringDAO().getAllWeatherings();
        } catch (DAOException e){
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        final MultiColumnMappedIndexArrayAdapter<Weathering> adapter = new MultiColumnMappedIndexArrayAdapter<Weathering>(getSkavaActivity(),
                R.layout.calculator_two_column_list_view_row_checked_radio, listWeatherings);

        TextView firstTextView = (TextView) weatheringHeaderView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) weatheringHeaderView.findViewById(R.id.second_column_text_view);
        secondTextView.setText("Weathering");

        weatheringListView.addHeaderView(weatheringHeaderView, null, false);
        final int numberOfHeaders = weatheringListView.getHeaderViewsCount();
        weatheringListView.setAdapter(adapter);

        selectedWeathering = getRMRCalculationContext().getWeathering();
        if (selectedWeathering != null) {
            int posIndex = adapter.getPosition(selectedWeathering);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                weatheringListView.setItemChecked(posIndex, true);
                weatheringListView.setSelection(posIndex);
            }
        }

        weatheringListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedWeathering = (Weathering) parent.getItemAtPosition(position);
                getRMRCalculationContext().setWeathering(selectedWeathering);
            }

        });

        //This method is necessary only to use a ListView inside a ScrollView
        ViewUtils.adjustListViewHeightBasedOnChildren(weatheringListView);
    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch (checkedId) {
//            case R.id.radioButtonSummarized:
//                mSummarizedConditionViewContainer.setVisibility(View.VISIBLE);
//                selectedConditions = (ConditionDiscontinuities) summarizedListview.getItemAtPosition(1);
//                getRMRCalculationContext().setConditionDiscontinuities(selectedConditions);
//                persistenceListView.clearChoices();
//                apertureListView.clearChoices();
//                roughnessListView.clearChoices();
//                infillingListView.clearChoices();
//                weatheringListView.clearChoices();
//                mDetailedConditionViewContainer.setVisibility(View.GONE);
//                break;
//            case R.id.radioButtonDetailed:
//                mSummarizedConditionViewContainer.setVisibility(View.GONE);
//                getRMRCalculationContext().setConditionDiscontinuities(null);
//                mDetailedConditionViewContainer.setVisibility(View.VISIBLE);
//                if (selectedConditions != null) {
//                    Integer summarizedKey = selectedConditions.getValue().intValue();
//                    int firstIndex = 1;
//                    switch (summarizedKey) {
//                        //Find the persistence object through a DAO
//                        case 30:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex);
//                            break;
//
//                        case 25:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 1);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 1);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 1);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 1);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 1);
//                            break;
//
//                        case 20:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 2);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 2);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 2);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 2);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 2);
//                            break;
//
//                        case 10:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 3);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 3);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 3);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 3);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 3);
//                            break;
//
//                        case 0:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 4);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 4);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 4);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 4);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 4);
//                            break;
//
//                    }
//                    getRMRCalculationContext().setWeathering(selectedWeathering);
//                    setupWeatheringListView();
//                    getRMRCalculationContext().setPersistence(selectedPersistence);
//                    setupPersistenceListView();
//                    getRMRCalculationContext().setAperture(selectedAperture);
//                    setupApertureListView();
//                    getRMRCalculationContext().setRoughness(selectedRoughness);
//                    setupRoughnessListView();
//                    getRMRCalculationContext().setInfilling(selectedInfilling);
//                    setupInfillingListView();
//                    getRMRCalculationContext().setConditionDiscontinuities(null);
//                    setupGeneralConditionListView();
//                }
//                mSummarizedConditionViewContainer.setVisibility(View.GONE);
//                summarizedListview.clearChoices();
//                selectedConditions = null;
//                mDetailedConditionViewContainer.setVisibility(View.VISIBLE);
//                break;
//        }
//    }
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch (checkedId) {
//            case R.id.radioButtonSummarized:
//                mSummarizedConditionViewContainer.setVisibility(View.VISIBLE);
//                selectedConditions = (ConditionDiscontinuities) summarizedListview.getItemAtPosition(1);
//                getRMRCalculationContext().setConditionDiscontinuities(selectedConditions);
//                persistenceListView.clearChoices();
//                apertureListView.clearChoices();
//                roughnessListView.clearChoices();
//                infillingListView.clearChoices();
//                weatheringListView.clearChoices();
//                mDetailedConditionViewContainer.setVisibility(View.GONE);
//                break;
//            case R.id.radioButtonDetailed:
//                mSummarizedConditionViewContainer.setVisibility(View.GONE);
//                getRMRCalculationContext().setConditionDiscontinuities(null);
//                mDetailedConditionViewContainer.setVisibility(View.VISIBLE);
//                if (selectedConditions != null) {
//                    Integer summarizedKey = selectedConditions.getValue().intValue();
//                    int firstIndex = 1;
//                    switch (summarizedKey) {
//                        //Find the persistence object through a DAO
//                        case 30:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex);
//                            break;
//
//                        case 25:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 1);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 1);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 1);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 1);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 1);
//                            break;
//
//                        case 20:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 2);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 2);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 2);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 2);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 2);
//                            break;
//
//                        case 10:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 3);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 3);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 3);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 3);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 3);
//                            break;
//
//                        case 0:
//                            selectedPersistence = (Persistence) persistenceListView.getAdapter().getItem(firstIndex + 4);
//                            selectedAperture = (Aperture) apertureListView.getAdapter().getItem(firstIndex + 4);
//                            selectedRoughness = (Roughness) roughnessListView.getAdapter().getItem(firstIndex + 4);
//                            selectedInfilling = (Infilling) infillingListView.getAdapter().getItem(firstIndex + 4);
//                            selectedWeathering = (Weathering) weatheringListView.getAdapter().getItem(firstIndex + 4);
//                            break;
//
//                    }
//                    getRMRCalculationContext().setWeathering(selectedWeathering);
//                    setupWeatheringListView();
//                    getRMRCalculationContext().setPersistence(selectedPersistence);
//                    setupPersistenceListView();
//                    getRMRCalculationContext().setAperture(selectedAperture);
//                    setupApertureListView();
//                    getRMRCalculationContext().setRoughness(selectedRoughness);
//                    setupRoughnessListView();
//                    getRMRCalculationContext().setInfilling(selectedInfilling);
//                    setupInfillingListView();
//                    getRMRCalculationContext().setConditionDiscontinuities(null);
//                    setupGeneralConditionListView();
//                }
//                mSummarizedConditionViewContainer.setVisibility(View.GONE);
//                summarizedListview.clearChoices();
//                selectedConditions = null;
//                mDetailedConditionViewContainer.setVisibility(View.VISIBLE);
//                break;
//        }
//    }

}
