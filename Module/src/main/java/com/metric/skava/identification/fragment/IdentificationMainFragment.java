package com.metric.skava.identification.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.adapter.SkavaEntityAdapter;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.validator.TextValidator;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.identification.helper.UserDataHelper;
import com.metric.skava.identification.model.UserDataDomain;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by metricboy on 2/21/14.
 */
public class IdentificationMainFragment extends SkavaFragment implements
        DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private EditText initialPegEditText;
    private EditText finalPegEditText;
    private EditText slopeTextEdit;
    private EditText orientationEditText;
    private TextView advanceTextView;

    private Spinner projectSpinner;
    private int projectSpinnerLastPosition;

    private Spinner tunnelSpinner;
    private int tunnelSpinnerLastPosition;

    private Spinner sectionSpinner;
    private int sectionSpinnerLastPosition;

    private Spinner faceSpinner;
    private int faceSpinnerLastPosition;

    private Spinner methodSpinner;
    private int methodSpinnerLastPosition;

    private DAOFactory daoFactory;
    private NumberFormat numberFormatter;
    private Typeface iconTypeFace;

    private SkavaEntityAdapter projectAdapter;
    private ExcavationProject selectedProject;
    private List<ExcavationProject> projectList;

    private SkavaEntityAdapter tunnelAdapter;
    private Tunnel selectedTunnel;
    private List<Tunnel> tunnelList;

    private SkavaEntityAdapter faceAdapter;
    private TunnelFace selectedFace;
    private List<TunnelFace> faceList;

    private SkavaEntityAdapter sectionAdapter;
    private ExcavationSection selectedSection;
    private List<ExcavationSection> sectionList;

    private User geologist;

    private SkavaEntityAdapter methodAdapter;
    private ExcavationMethod selectedMethod;
    private List<ExcavationMethod> methodList;

    private Double finalPeg;
    private Double initialPeg;
    private Date selectedDate;
    private Double advance;


    private UserDataDomain mUserDataDomain;

    //********** Callback interface: This is an idea to force the Identification phase before any other stage could be used
    private IdentificationCompletedListener mCallback;

    public interface IdentificationCompletedListener {
        public void onIdentificationCompleted();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (IdentificationCompletedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IdentificationCompletedListener");
        }
    }
    //********** Change our minds. Not used now. Waiting to see if this is required


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iconTypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Android-Dev-Icons-1.ttf");

        methodSpinnerLastPosition = -1;
        sectionSpinnerLastPosition = -1;
        faceSpinnerLastPosition = -1;
        tunnelSpinnerLastPosition = -1;
        projectSpinnerLastPosition = -1;

        numberFormatter = DecimalFormat.getNumberInstance();

        daoFactory = DAOFactory.getInstance(getActivity());

        geologist = getSkavaContext().getLoggedUser();
        getCurrentAssessment().setGeologist(geologist);

        try {
            UserDataHelper dataDomainHelper = new UserDataHelper(getActivity());
            mUserDataDomain = dataDomainHelper.buildUserDataDomain(geologist);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        projectList = mUserDataDomain.getProjects();
        projectList.add(new ExcavationProject("HINT", "Select one project ..."));

        projectAdapter = new SkavaEntityAdapter<ExcavationProject>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, projectList);
        // Specify the layout to use when the list of choices appears
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        LocalExcavationSectionDAO sectionDAO = null;
        try {
            sectionDAO = daoFactory.getLocalExcavationSectionDAO(DAOFactory.Flavour.SQLLITE);
            sectionList = sectionDAO.getAllExcavationSections();
            sectionList.add(new ExcavationSection("HINT", "Select one section ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
//            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        sectionAdapter = new SkavaEntityAdapter<ExcavationSection>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, sectionList);
        // Specify the layout to use when the list of choices appears
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        LocalExcavationMethodDAO localExcavationMethodDAO = null;
        try {
            localExcavationMethodDAO = daoFactory.getLocalExcavationMethodDAO(DAOFactory.Flavour.SQLLITE);
            methodList = localExcavationMethodDAO.getAllExcavationMethods();
            methodList.add(new ExcavationMethod("HINT", "Select one method ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        methodAdapter = new SkavaEntityAdapter<ExcavationMethod>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, methodList);
        // Specify the layout to use when the list of choices appears
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        selectedDate = getCurrentAssessment().getDate();
        selectedFace = getCurrentAssessment().getFace();
        selectedTunnel = getCurrentAssessment().getTunnel();
        selectedProject = getCurrentAssessment().getProject();
        selectedSection = getCurrentAssessment().getSection();
        selectedMethod = getCurrentAssessment().getMethod();
        initialPeg = getSkavaContext().getAssessment().getInitialPeg();
        finalPeg = getSkavaContext().getAssessment().getFinalPeg();
        advance = getSkavaContext().getAssessment().getCurrentAdvance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.identification_main_fragment, container, false);

        String internalCode = getCurrentAssessment().getInternalCode();
        if (internalCode != null) {
            ((TextView) rootView.findViewById(R.id.mapping_gral_info_code_value)).setText(internalCode);
        }


        // ***************** PROJECT ******************
        projectSpinner = (Spinner) rootView.findViewById(R.id.mapping_gral_info_project_spinner);
        projectSpinner.setAdapter(projectAdapter);
        //if this is the first time the spinner get configured set selection to the hint
        //this is necessary in order to onItemSelected proper functioning
        if (projectSpinnerLastPosition == -1) {
            projectSpinnerLastPosition = projectSpinner.getAdapter().getCount() - 1;
        }
        projectSpinner.setOnItemSelectedListener(this);
        //first time selectedProject will be null, after that we can extract it from the selectedProject
        //The user may have not complete the face selection and leaves the fragment so
        //ExcavationProject project = getCurrentAssessment().getProject() wont work here
        //That's why is better to ese the selectedProject variable instead
        if (selectedProject != null) {
            projectSpinner.setSelection(projectAdapter.getPosition(selectedProject));
        } else {
            projectSpinner.setSelection(projectAdapter.getCount() - 1); //display hint
        }

        // ***************** TUNNEL ******************
        tunnelSpinner = (Spinner) rootView.findViewById(R.id.mapping_gral_info_tunnel_spinner);
        //tunnel adapter remains null until a project is selected
        //tunnelSpinner.setAdapter(tunnelAdapter);
        tunnelSpinner.setOnItemSelectedListener(this);
        if (selectedTunnel != null) {
            tunnelSpinner.setSelection(tunnelSpinnerLastPosition);
//            tunnelAdapter = prepareTunnelAdapter(selectedTunnel.getProject());
//            tunnelSpinner.setSelection(tunnelAdapter.getPosition(selectedTunnel));
        }

        // ***************** TUNNEL FACE ******************
        faceSpinner = (Spinner) rootView.findViewById(R.id.mapping_gral_info_face_spinner);
        //face adapter remains null until a project is selected
        //faceSpinner.setAdapter(faceAdapter);
        faceSpinner.setOnItemSelectedListener(this);
        if (selectedFace != null) {
            faceSpinner.setSelection(faceSpinnerLastPosition);
//            faceAdapter = prepareFaceAdapter(selectedFace.getTunnel());
//            faceSpinner.setSelection(faceAdapter.getPosition(selectedFace)); //display hint
        }

        // ***************** EXCAVATION SECTION ******************
        sectionSpinner = (Spinner) rootView.findViewById(R.id.mapping_gral_info_section_spinner);
        sectionSpinner.setAdapter(sectionAdapter);
        sectionSpinner.setOnItemSelectedListener(this);
        ExcavationSection section = getCurrentAssessment().getSection();
        if (section != null) {
            if (sectionAdapter != null) {
                int currentSectionPosition = sectionAdapter.getPosition(section);
                sectionSpinner.setSelection(currentSectionPosition);
            }
        } else {
            sectionSpinner.setSelection(sectionAdapter.getCount() - 1); //display hint
        }

        // ***************** DATE ******************

        TextView icon = (TextView) rootView.findViewById(R.id.mapping_gral_date_button);
        icon.setTypeface(iconTypeFace);
        icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        if (selectedDate != null) {
            ((TextView) rootView.findViewById(R.id.mapping_gral_info_date_value)).setText(DateFormat.getDateInstance().format(selectedDate));
        } else {
            java.lang.String todayDate = DateFormat.getDateInstance().format(new Date());
            ((TextView) rootView.findViewById(R.id.mapping_gral_info_date_value)).setText(todayDate);
        }
        rootView.findViewById(R.id.mapping_gral_date_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new DialogFragment() {
                            @Override
                            public Dialog onCreateDialog(
                                    Bundle savedInstanceState) {
                                final Calendar c = Calendar.getInstance();
                                int year = c.get(Calendar.YEAR);
                                int month = c.get(Calendar.MONTH);
                                int day = c.get(Calendar.DAY_OF_MONTH);

                                // Create a new instance of DatePickerDialog and
                                // return it
                                return new DatePickerDialog(getActivity(),
                                        IdentificationMainFragment.this,
                                        year, month, day);
                            }
                        };
                        newFragment.show(getActivity().getSupportFragmentManager(),
                                "datePicker");
                    }
                });


        // ***************** GEOLOGIST ******************
        TextView geologistTextView = (TextView) rootView.findViewById(R.id.mapping_gral_info_geologist_value);
        geologistTextView.setText(geologist.getName());

        // ***************** PK ******************
        initialPegEditText = (EditText) rootView.findViewById(R.id.mapping_gral_info_initial_pk_value);
        initialPegEditText.setRawInputType(Configuration.KEYBOARD_12KEY);

        if (initialPeg != null) {
            initialPegEditText.setText(initialPeg.toString());
        }
        initialPegEditText.addTextChangedListener(new TextValidator(initialPegEditText) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                Double enteredValue = 0d;
                try {
                    //TODO format and parse Peg values
                    text = text.replace("+", "");
                    text = text.replace(",", "");
                    enteredValue = Double.parseDouble(text);
                    if (enteredValue >= 0 && enteredValue < 9999999) {
                        initialPeg = enteredValue;
                    } else {
                        initialPegEditText.setError("Initial Peg must be a number!");
                    }
                } catch (NumberFormatException e) {
                    initialPegEditText.setError("Initial Peg must be a number!");
                }

            }
        });

        initialPegEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT) {
                    finalPegEditText.requestFocus();
                    return false;
                }
                return false;
            }
        });

        initialPegEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //do job here owhen Edittext lose focus
                    if (initialPeg != null) {
                        getCurrentAssessment().setInitialPeg(initialPeg);
                    }
                }
            }
        });

        advanceTextView = (TextView) rootView.findViewById(R.id.mapping_gral_info_actual_advance_value);
        if(advance!=null){
            advanceTextView.setText(numberFormatter.format(advance));
        }

        finalPegEditText = (EditText) rootView.findViewById(R.id.mapping_gral_info_final_pk_value);
        finalPegEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        if (finalPeg != null) {
            finalPegEditText.setText(finalPeg.toString());
        }
        finalPegEditText.addTextChangedListener(new TextValidator(finalPegEditText) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                Double enteredValue = 0d;
                try {
                    text = text.replace("+", "");
                    text = text.replace(",", "");
                    enteredValue = Double.parseDouble(text);
                    if (enteredValue >= 0 && enteredValue < 9999999) {
                        finalPeg = enteredValue;
                    } else {
                        finalPegEditText.setError("Final Peg must be a number!");
                    }
                } catch (NumberFormatException e) {
                    finalPegEditText.setError("Final Peg must be a number!");
                }
            }
        });

        finalPegEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT) {
                    methodSpinner.requestFocus();
                    return false;
                }
                return false;
            }
        });

        finalPegEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //do job here owhen Edittext lose focus
                    getCurrentAssessment().setFinalPeg(finalPeg);
                    if (finalPeg != null && initialPeg != null) {
                        advance = finalPeg - initialPeg;
                    }
                    if (advance != null) {
                        getCurrentAssessment().setCurrentAdvance(advance);
                        advanceTextView.setText(numberFormatter.format(advance));
                    }
                }
            }
        });

        // ************* EXCAVATION METHOD ****************

        methodSpinner = (Spinner) rootView.findViewById(R.id.mapping_gral_info_method_spinner);
        methodSpinner.setAdapter(methodAdapter);
        methodSpinner.setOnItemSelectedListener(this);
        ExcavationMethod method = getCurrentAssessment().getMethod();
        if (method != null) {
            methodSpinner.setSelection(methodAdapter.getPosition(method)); //display hint
        } else {
            methodSpinner.setSelection(methodAdapter.getCount() - 1); //display hint
        }


        orientationEditText = (EditText) rootView.findViewById(R.id.mapping_gral_info_orientation_value);
        orientationEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        Short orientation = getCurrentAssessment().getOrientation();
        if (orientation != null) {
            orientationEditText.setText(numberFormatter.format(orientation));
        }

        orientationEditText.addTextChangedListener(new TextValidator(orientationEditText) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                Short enteredValue = 0;
                try {
                    enteredValue = Short.parseShort(text);
                    if (enteredValue > 0 && enteredValue < 360) {
                        getSkavaContext().getAssessment().setOrientation(enteredValue);
                    } else {
                        orientationEditText.setError("Orientation must be between 0 and 360!");
                    }
                } catch (NumberFormatException e) {
                    orientationEditText.setError("Orientation must be between 0 and 360!");
                }
            }
        });


        slopeTextEdit = (EditText) rootView.findViewById(R.id.mapping_gral_info_slope_value);
        slopeTextEdit.setRawInputType(Configuration.KEYBOARD_12KEY);
        Double slope = getCurrentAssessment().getSlope();
        if (slope != null) {
            slopeTextEdit.setText(numberFormatter.format(slope));
        }

        slopeTextEdit.addTextChangedListener(new TextValidator(slopeTextEdit) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                Double enteredValue = 0d;
                try {
                    enteredValue = Double.parseDouble(text);
                    if (enteredValue > -100 && enteredValue < 100) {
                        getSkavaContext().getAssessment().setSlope(enteredValue);
                    } else {
                        slopeTextEdit.setError("Slope must be a number between 0 and 90!");
                    }
                } catch (NumberFormatException e) {
                    slopeTextEdit.setError("Slope must be between 0 and 100!");
                }
            }
        });

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        Date selectedDate = new Date(datePicker.getCalendarView().getDate());
        java.lang.String formattedDate = DateFormat.getDateInstance().format(selectedDate);
        ((TextView) getView().findViewById(R.id.mapping_gral_info_date_value))
                .setText(formattedDate);
        getSkavaContext().getAssessment().setDate(selectedDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == projectSpinner) {
            if (position != projectSpinnerLastPosition) {
                selectedProject = (ExcavationProject) parent.getItemAtPosition(position);
                tunnelAdapter = prepareTunnelAdapter(selectedProject);
                tunnelSpinner.setAdapter(tunnelAdapter);
                if (selectedTunnel != null) {
                    if (tunnelAdapter.getPosition(selectedTunnel) != -1) {
                        tunnelSpinner.setSelection(tunnelAdapter.getPosition(selectedTunnel));
                    } else {
                        tunnelSpinner.setSelection(tunnelAdapter.getCount() - 1);
                    }
                } else {
                    tunnelSpinner.setSelection(tunnelAdapter.getCount() - 1);
                }
                projectSpinnerLastPosition = position;
            }
        }


        if (parent == tunnelSpinner) {
            if (position != tunnelSpinnerLastPosition) {
                selectedTunnel = (Tunnel) parent.getItemAtPosition(position);
                faceAdapter = prepareFaceAdapter(selectedTunnel);
                faceSpinner.setAdapter(faceAdapter);

                if (selectedFace != null) {
                    if (faceAdapter.getPosition(selectedFace) != -1) {
                        faceSpinner.setSelection(faceAdapter.getPosition(selectedFace));
                    } else {
                        faceSpinner.setSelection(faceAdapter.getCount() - 1);
                    }
                } else {
                    faceSpinner.setSelection(faceAdapter.getCount() - 1);
                }
                tunnelSpinnerLastPosition = position;
            }
        }

        if (parent == faceSpinner) {
            if (position != faceSpinnerLastPosition) {
                selectedFace = (TunnelFace) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().setFace(selectedFace);
                faceSpinnerLastPosition = position;
                //use the orientation from the face as initial value
                orientationEditText.setText(selectedFace.getOrientation().toString());
                //use the slope from the face as initial value
                slopeTextEdit.setText(selectedFace.getSlope().toString());
            }
        }

        if (parent == sectionSpinner) {
//            if (sectionSpinnerLastPosition == -1){
//                sectionSpinner.setSelection(sectionAdapter.getCount());
//                return;
//            }
            if (position != sectionSpinner.getAdapter().getCount() && position != sectionSpinnerLastPosition) {
                selectedSection = (ExcavationSection) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().setSection(selectedSection);
                sectionSpinnerLastPosition = position;
            }
        }

        if (parent == methodSpinner) {
//            if (methodSpinnerLastPosition == -1){
//                methodSpinner.setSelection(methodAdapter.getCount());
//                return;
//            }
            if (position != methodSpinner.getAdapter().getCount() && position != methodSpinnerLastPosition) {
                selectedMethod = (ExcavationMethod) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().setMethod(selectedMethod);
                methodSpinnerLastPosition = position;
            }
        }
    }

    private SkavaEntityAdapter prepareTunnelAdapter(ExcavationProject project) {

        tunnelList = mUserDataDomain.getTunnels(project);
        tunnelList.add(new Tunnel("HINT", "Select a tunnel ..."));
        tunnelAdapter = new SkavaEntityAdapter<Tunnel>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, tunnelList);
        // Specify the layout to use when the list of choices appears
        tunnelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (tunnelSpinnerLastPosition == -1) {
            tunnelSpinnerLastPosition = tunnelAdapter.getCount() - 1;
        }
        return tunnelAdapter;
    }

    private SkavaEntityAdapter prepareFaceAdapter(Tunnel tunnel) {
        faceList = mUserDataDomain.getFaces(tunnel);
        faceList.add(new TunnelFace("HINT", "Select a face ..."));
        faceAdapter = new SkavaEntityAdapter<TunnelFace>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, faceList);
        // Specify the layout to use when the list of choices appears
        faceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (faceSpinnerLastPosition == -1) {
            faceSpinnerLastPosition = faceAdapter.getCount() - 1;
        }
        return faceAdapter;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.identification_main, menu);
    }


}
