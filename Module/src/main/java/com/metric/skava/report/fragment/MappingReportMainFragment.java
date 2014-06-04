package com.metric.skava.report.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.PegNumberFormat;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.calculator.barton.helper.QToQualityMapper;
import com.metric.skava.calculator.barton.logic.QBartonCalculator;
import com.metric.skava.calculator.barton.logic.QBartonInput;
import com.metric.skava.calculator.barton.logic.QBartonOutput;
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.barton.model.RQD;
import com.metric.skava.calculator.barton.model.RockQuality;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.calculator.rmr.helper.RmrToQualityMapper;
import com.metric.skava.calculator.rmr.logic.RMRCalculator;
import com.metric.skava.calculator.rmr.logic.RMRInput;
import com.metric.skava.calculator.rmr.logic.RMROutput;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.calculator.rmr.model.RQD_RMR;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;
import com.metric.skava.discontinuities.model.DiscontinuityShape;
import com.metric.skava.discontinuities.model.DiscontinuityType;
import com.metric.skava.discontinuities.model.DiscontinuityWater;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportPatternType;
import com.metric.skava.instructions.model.SupportRecommendation;
import com.metric.skava.pictures.util.SkavaPictureFilesUtils;
import com.metric.skava.rockmass.model.FractureType;
import com.metric.skava.rocksupport.model.ESR;
import com.metric.skava.rocksupport.model.ExcavationFactors;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by metricboy on 2/16/14.
 */
public class MappingReportMainFragment extends SkavaFragment {

    public static final String ARG_BASKET_ID = "PARCELABLE_DATA_BASKET_ID";

    public MappingReportMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.mapping_report_main_fragment, container, false);

        Assessment currentAssessment = getSkavaContext().getAssessment();
        NumberFormat numberFormat = DecimalFormat.getInstance();
        NumberFormat pkFormat = new PegNumberFormat();

        ExcavationProject project = currentAssessment.getProject();
        if (project != null) {
            ((TextView) rootView.findViewById(R.id.report_proyecto_value)).setText(SkavaUtils.breakLongLine(project.getName(), 25));
        }


        Tunnel tunnel = currentAssessment.getTunnel();
        if (tunnel != null) {
            ((TextView) rootView.findViewById(R.id.report_tunel_value)).setText(SkavaUtils.breakLongLine(tunnel.getName(), 25));
        }

        TunnelFace face = currentAssessment.getFace();
        if (face != null) {
            ((TextView) rootView.findViewById(R.id.report_frente_value)).setText(SkavaUtils.breakLongLine(face.getName(), 25));
        }

        ExcavationSection section = currentAssessment.getSection();
        if (!SkavaUtils.isUndefined(section)) {
            ((TextView) rootView.findViewById(R.id.report_tipo_seccion_value)).setText(section.getName());
        }

        Calendar dateTime = currentAssessment.getDateTime();
        if (dateTime != null) {
            DateFormat dateFormater = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
            String dateAsString = dateFormater.format(dateTime.getTime());
            ((TextView) rootView.findViewById(R.id.report_fecha_value)).setText(dateAsString);
        }

        User geologist = currentAssessment.getGeologist();
        if (geologist != null) {
            ((TextView) rootView.findViewById(R.id.report_geologo_value)).setText(geologist.getName());
        }

        Double initPk = currentAssessment.getInitialPeg();
        Double lastPk = currentAssessment.getFinalPeg();
        if (initPk != null && lastPk != null) {
            ((TextView) rootView.findViewById(R.id.report_pk_values)).setText(pkFormat.format(initPk) + " - " + pkFormat.format(lastPk));
        }

        Double currentAdvance = currentAssessment.getCurrentAdvance();
        if (currentAdvance != null) {
            ((TextView) rootView.findViewById(R.id.report_avance_value)).setText(numberFormat.format(currentAdvance));
        }
        Double accumAdvance = currentAssessment.getAccummAdvance();
        if (accumAdvance != null) {
            ((TextView) rootView.findViewById(R.id.report_accum_avance_value)).setText(numberFormat.format(accumAdvance));
        }

        ExcavationMethod method = currentAssessment.getMethod();
        if (!SkavaUtils.isUndefined(method)) {
            ((TextView) rootView.findViewById(R.id.report_metodo_value)).setText(method.getName());
        }

        Short orientation = currentAssessment.getOrientation();
        if (orientation != null) {
            ((TextView) rootView.findViewById(R.id.report_orientacion_value)).setText(numberFormat.format(orientation));
        }

        Double slope = currentAssessment.getSlope();
        if (slope != null) {
            ((TextView) rootView.findViewById(R.id.report_pendiente_value)).setText(numberFormat.format(slope));
        }

        FractureType fractureType = currentAssessment.getFractureType();
        if (fractureType != null) {
            ((TextView) rootView.findViewById(R.id.report_tipo_fracturacion_value)).setText(fractureType.getName());
        }

        Double blockSize = currentAssessment.getBlockSize();
        if (blockSize != null) {
            ((TextView) rootView.findViewById(R.id.report_medida_bloques_value)).setText(numberFormat.format(blockSize));
        }

        Short numberOfJoints = currentAssessment.getNumberOfJoints();
        if (numberOfJoints != null) {
            ((TextView) rootView.findViewById(R.id.report_juntas_m3_value)).setText(numberFormat.format(numberOfJoints));
        }

        // Call a calculation to be sure the result into account any changes on the selection of RMR list options
        this.calculateRMR();
        RMR_Calculation rmrCalculation = currentAssessment.getRmrCalculation();

        StrengthOfRock strengthOfRock = rmrCalculation.getStrengthOfRock();
        if (strengthOfRock != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_ucs_value)).setText(strengthOfRock.getKey());
            ((TextView) rootView.findViewById(R.id.report_rmr_ucs_rating)).setText(numberFormat.format(strengthOfRock.getValue()));
        }

        Spacing spacing = rmrCalculation.getSpacing();
        if (spacing != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_espaciado_value)).setText(spacing.getKey());
            ((TextView) rootView.findViewById(R.id.report_rmr_espaciado_rating)).setText(numberFormat.format(spacing.getValue()));
        }

        Persistence persistence = rmrCalculation.getPersistence();
        if (persistence != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_persistencia_value)).setText(persistence.getKey());
            ((TextView) rootView.findViewById(R.id.report_rmr_persistencia_rating)).setText(numberFormat.format(persistence.getValue()));
        }

        Aperture aperture = rmrCalculation.getAperture();
        if (aperture != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_abertura_value)).setText(aperture.getKey());
            ((TextView) rootView.findViewById(R.id.report_rmr_abertura_rating)).setText(numberFormat.format(aperture.getValue()));
        }

        Roughness roughness = rmrCalculation.getRoughness();
        if (roughness != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_rugosidad_value)).setText(roughness.getKey());
            ((TextView) rootView.findViewById(R.id.report_rmr_rugosidad_rating)).setText(numberFormat.format(roughness.getValue()));
        }

        Infilling infilling = rmrCalculation.getInfilling();
        if (infilling != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_relleno_value)).setText(infilling.getKey());
            ((TextView) rootView.findViewById(R.id.report_rmr_relleno_rating)).setText(numberFormat.format(infilling.getValue()));
        }

        Weathering weathering = rmrCalculation.getWeathering();
        if (weathering != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_meteorizacion_value)).setText(weathering.getKey());
            ((TextView) rootView.findViewById(R.id.report_rmr_meteorizacion_rating)).setText(numberFormat.format(weathering.getValue()));
        }

        Groundwater groundwater = rmrCalculation.getGroundwater();
        if (groundwater != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_agua_value)).setText(groundwater.getKey());
            ((TextView) rootView.findViewById(R.id.report_rmr_agua_rating)).setText(numberFormat.format(groundwater.getValue()));
        }


        OrientationDiscontinuities orientationDiscontinuities = rmrCalculation.getOrientationDiscontinuities();
        if (orientationDiscontinuities != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_ajuste_value)).setText(orientationDiscontinuities.getGroup().toString() + " - " + orientationDiscontinuities.getKey());
            ((TextView) rootView.findViewById(R.id.report_rmr_ajuste_value_rating)).setText(numberFormat.format(orientationDiscontinuities.getValue()));
        }


        Double rmrValue = rmrCalculation.getRMRResult().getRMR();
        ((TextView) rootView.findViewById(R.id.report_rmr_rmr_value)).setText(numberFormat.format(rmrValue));


        RmrToQualityMapper rmrMapper = RmrToQualityMapper.getInstance(getSkavaContext());

        RockQuality quality = rmrMapper.mapRMRToRockMassQuality(rmrValue);
        if (quality != null) {
            ((TextView) rootView.findViewById(R.id.report_rmr_clase_value)).setText(quality.getClassification().toString());
            ((TextView) rootView.findViewById(R.id.report_rmr_calidad_value)).setText(quality.getName());
        }

        if (currentAssessment.getTunnel() != null) {
            ExcavationFactors excavationFactors = currentAssessment.getTunnel().getExcavationFactors();
            ESR esr = excavationFactors.getEsr();
            if (esr != null) {
                TextView textView = ((TextView) rootView.findViewById(R.id.report_q_esr_value));
                textView.setText(esr.getKey());
                textView = ((TextView) rootView.findViewById(R.id.report_q_esr_rating));
                textView.setText(numberFormat.format(esr.getValue()));
            }

            Double span = excavationFactors.getSpan();
            if (span != null) {
                TextView textView = ((TextView) rootView.findViewById(R.id.report_q_ancho_value));
                textView.setText("-");
                textView = ((TextView) rootView.findViewById(R.id.report_q_ancho_rating));
                textView.setText(numberFormat.format(span));
            }

            if (esr != null && span != null) {
                TextView textView = ((TextView) rootView.findViewById(R.id.report_q_a_esr_value));
                textView.setText(span.toString());
                textView.setText(esr.getKey());
                Double ratio = span / esr.getValue();
                textView = ((TextView) rootView.findViewById(R.id.report_q_a_esr_rating));
                textView.setText(numberFormat.format(ratio));
            }
        }

        // Call a calculation to be sure the result into account any changes on the selection of Q list options
        this.calculateQBarton();
        Q_Calculation qCalculation = currentAssessment.getQCalculation();

        RQD rqd = qCalculation.getRqd();
        if (rqd != null) {
            Object value;
            value = rqd.getJv();
            if (value == null) {
                value = rqd.getPieces();
            }
            if (value != null) {
                ((TextView) rootView.findViewById(R.id.report_q_rqd_value)).setText(value.toString());
            } else {
                ((TextView) rootView.findViewById(R.id.report_q_rqd_value)).setText("-");
            }
            ((TextView) rootView.findViewById(R.id.report_q_rqd_rating)).setText(numberFormat.format(rqd.getValue()));
        }

        Jn jn = qCalculation.getJn();
        if (jn != null) {
            TextView textView = ((TextView) rootView.findViewById(R.id.report_q_jn_value));
            textView.setText(jn.getKey());
            textView = ((TextView) rootView.findViewById(R.id.report_q_jn_rating));
            textView.setText(numberFormat.format(jn.getValue()));
        }

        Jr jr = qCalculation.getJr();
        if (jr != null) {
            ((TextView) rootView.findViewById(R.id.report_q_jr_value)).setText(jr.getGroupName() + " " + jr.getKey());
            ((TextView) rootView.findViewById(R.id.report_q_jr_rating)).setText(numberFormat.format(jr.getValue()));
        }

        Ja ja = qCalculation.getJa();
        if (ja != null) {
            ((TextView) rootView.findViewById(R.id.report_q_ja_value)).setText(ja.getGroupName() + " " + ja.getKey());
            ((TextView) rootView.findViewById(R.id.report_q_ja_rating)).setText(numberFormat.format(ja.getValue()));
        }

        Jw jw = qCalculation.getJw();
        if (jw != null) {
            ((TextView) rootView.findViewById(R.id.report_q_jw_value)).setText(jw.getKey());
            ((TextView) rootView.findViewById(R.id.report_q_jw_rating)).setText(numberFormat.format(jw.getValue()));
        }

        SRF srf = qCalculation.getSrf();
        if (srf != null) {
            ((TextView) rootView.findViewById(R.id.report_q_srf_value)).setText(srf.getGroupName() + " " + srf.getKey());
            ((TextView) rootView.findViewById(R.id.report_q_srf_rating)).setText(numberFormat.format(srf.getValue()));
        }

        Double qValue = qCalculation.getQResult().getQBarton();
        ((TextView) rootView.findViewById(R.id.report_q_q_value)).setText(numberFormat.format(qValue));

        QToQualityMapper qMapper = QToQualityMapper.getInstance(getSkavaContext());
        quality = qMapper.mapQToRockMassQuality(qValue);
        if (quality != null) {
            ((TextView) rootView.findViewById(R.id.report_q_calidad_value)).setText(quality.getName());
        }

        SupportRecommendation recomendation = currentAssessment.getRecomendation();
        if (recomendation != null) {
            SupportRequirement requirement = recomendation.getRequirementBase();
            if (SkavaUtils.isDefined(requirement)) {
                String name = requirement.getName();
                if (name != null && !name.isEmpty()) {
                    TextView titleTextView = (TextView) rootView.findViewById(R.id.report_soporte_title);
                    String currentTitle = titleTextView.getText().toString();
                    titleTextView.setText(currentTitle + ": " + name);
                }
            }
            BoltType boltType = recomendation.getBoltType();
            if (boltType != null) {
                ((TextView) rootView.findViewById(R.id.report_tipo_perno_value)).setText(boltType.getName());
            } else {
                ((TextView) rootView.findViewById(R.id.report_tipo_perno_value)).setText("-");
            }

            Double boltDiameter = recomendation.getBoltDiameter();
            if (boltDiameter != null) {
                ((TextView) rootView.findViewById(R.id.report_diametro_perno_value)).setText(numberFormat.format(boltDiameter));
            }  else {
                ((TextView) rootView.findViewById(R.id.report_diametro_perno_value)).setText("-");
            }

            Double boltLength = recomendation.getBoltLength();
            if (boltLength != null) {
                ((TextView) rootView.findViewById(R.id.report_largo_perno_value)).setText(numberFormat.format(boltLength));
            } else {
                ((TextView) rootView.findViewById(R.id.report_largo_perno_value)).setText("-");
            }

            ShotcreteType shotcreteType = recomendation.getShotcreteType();
            if (shotcreteType != null) {
                ((TextView) rootView.findViewById(R.id.report_tipo_shotcrete_value)).setText(shotcreteType.getName());
            } else {
                ((TextView) rootView.findViewById(R.id.report_tipo_shotcrete_value)).setText("-");
            }

            Coverage shotcreteCoverage = recomendation.getShotcreteCoverage();
            if (shotcreteCoverage != null) {
                ((TextView) rootView.findViewById(R.id.report_shotcrete_cobertura_value)).setText(shotcreteCoverage.getName());
            } else {
                ((TextView) rootView.findViewById(R.id.report_shotcrete_cobertura_value)).setText("-");
            }

            Double shotcreteThickness = recomendation.getThickness();
            if (shotcreteThickness != null) {
                ((TextView) rootView.findViewById(R.id.report_espesor_shotcrete_value)).setText(shotcreteThickness.toString());
            } else {
                ((TextView) rootView.findViewById(R.id.report_espesor_shotcrete_value)).setText("-");
            }

            MeshType meshType = recomendation.getMeshType();
            if (meshType != null) {
                ((TextView) rootView.findViewById(R.id.report_tipo_malla_value)).setText(meshType.getName());
            } else {
                ((TextView) rootView.findViewById(R.id.report_tipo_malla_value)).setText("-");
            }

            Coverage meshCoverage = recomendation.getMeshCoverage();
            if (meshCoverage != null) {
                ((TextView) rootView.findViewById(R.id.report_mesh_cobertura_value)).setText(meshCoverage.getName());
            } else {
                ((TextView) rootView.findViewById(R.id.report_mesh_cobertura_value)).setText("-");
            }

            ArchType archType = recomendation.getArchType();
            if (archType != null) {
                ((TextView) rootView.findViewById(R.id.report_tipo_marco_value)).setText(archType.getName());
            } else {
                ((TextView) rootView.findViewById(R.id.report_tipo_marco_value)).setText("-");
            }

            Double separation = recomendation.getSeparation();
            if (separation != null) {
                ((TextView) rootView.findViewById(R.id.report_separacion_value)).setText(numberFormat.format(separation));
            } else {
                ((TextView) rootView.findViewById(R.id.report_separacion_value)).setText("-");
            }

            String observations = recomendation.getObservations();
            if (observations != null) {
                ((TextView) rootView.findViewById(R.id.report_observaciones_soporte_value)).setText(observations);
            } else {
                ((TextView) rootView.findViewById(R.id.report_observaciones_soporte_value)).setText("-");
            }

            SupportPattern wallPattern = recomendation.getWallPattern();
            if (wallPattern != null) {
                Double dx = wallPattern.getDistanceX();
                Double dy = wallPattern.getDistanceY();
                TextView dxdyTextView = (TextView) rootView.findViewById(R.id.report_wall_pattern_dxdy);
                String dxdy = "";
                if (dx != null && dy != null) {
                    dxdy = dx.toString() + " x " + dy.toString();
                } else if (dx != null) {
                    dxdy = dx.toString();
                } else if (dy != null) {
                    dxdy = dy.toString();
                }
                dxdyTextView.setText(dxdy);

                SupportPatternType patternType = wallPattern.getType();
                TextView patternTypeTextView = (TextView) rootView.findViewById(R.id.report_wall_pattern_type);
                if (patternType != null) {
                    patternTypeTextView.setText(patternType.getName());
                } else {
                    patternTypeTextView.setText("-");
                }
            }

            SupportPattern roofPattern = recomendation.getRoofPattern();
            if (roofPattern != null) {
                Double dx = roofPattern.getDistanceX();
                Double dy = roofPattern.getDistanceY();
                TextView dxdyTextView = (TextView) rootView.findViewById(R.id.report_roof_pattern_dxdy);
                String dxdy = "";
                if (dx != null && dy != null) {
                    dxdy = dx.toString() + " x " + dy.toString();
                } else if (dx != null) {
                    dxdy = dx.toString();
                } else if (dy != null) {
                    dxdy = dy.toString();
                }
                dxdyTextView.setText(dxdy);

                SupportPatternType patternType = roofPattern.getType();
                TextView patternTypeTextView = (TextView) rootView.findViewById(R.id.report_roof_pattern_type);
                if (patternType != null) {
                    patternTypeTextView.setText(patternType.getName());
                } else {
                    patternTypeTextView.setText("-");
                }
            }
        }

        List<DiscontinuityFamily> discontinuitiesSystem = currentAssessment.getDiscontinuitySystem();

        if (discontinuitiesSystem != null) {
            Iterator<DiscontinuityFamily> iter = discontinuitiesSystem.iterator();
            for (int i = 1; iter.hasNext(); i++) {

                DiscontinuityFamily currentDiscFamily = iter.next();

                //add text view (columns) programatically
                TableRow numberRow = (TableRow) rootView.findViewById(R.id.report_disc_number_row);
                TextView textViewNumber = new TextView(getActivity());
                textViewNumber.setText(String.valueOf(i));
                textViewNumber.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewNumber.setBackgroundResource(R.drawable.text_box);
                textViewNumber.setGravity(Gravity.CENTER);
                numberRow.addView(textViewNumber);


                TableRow typeRow = (TableRow) rootView.findViewById(R.id.report_disc_type_row);
                TextView textViewType = new TextView(getActivity());
                textViewType.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewType.setBackgroundResource(R.drawable.text_box);
                textViewType.setGravity(Gravity.CENTER);
                typeRow.addView(textViewType);

                if (currentDiscFamily != null) {
                    DiscontinuityType type = currentDiscFamily.getType();
                    if (SkavaUtils.isDefined(type)) {
                        textViewType.setText(type.getName());
                    } else {
                        textViewType.setText("-");
                    }
                } else {
                    textViewType.setText("-");
                }

                TableRow relevanceRow = (TableRow) rootView.findViewById(R.id.report_disc_relevance_row);
                TextView textViewRelevance = new TextView(getActivity());
                textViewRelevance.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewRelevance.setBackgroundResource(R.drawable.text_box);
                textViewRelevance.setGravity(Gravity.CENTER);
                relevanceRow.addView(textViewRelevance);
                if (currentDiscFamily != null) {
                    DiscontinuityRelevance relevance = currentDiscFamily.getRelevance();
                    if (SkavaUtils.isDefined(relevance)) {
                        textViewRelevance.setText(relevance.toString());
                    } else {
                        textViewRelevance.setText("-");
                    }
                } else {
                    textViewRelevance.setText("-");
                }


                TableRow dipDirRow = (TableRow) rootView.findViewById(R.id.report_disc_dip_dir_row);
                TextView textViewDipDir = new TextView(getActivity());
                textViewDipDir.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewDipDir.setBackgroundResource(R.drawable.text_box);
                textViewDipDir.setGravity(Gravity.CENTER);
                dipDirRow.addView(textViewDipDir);
                if (currentDiscFamily != null) {
                    Short dipDir = currentDiscFamily.getDipDirDegrees();
                    if (dipDir != null) {
                        textViewDipDir.setText(dipDir.toString());
                    } else {
                        textViewDipDir.setText("-");
                    }
                } else {
                    textViewDipDir.setText("-");
                }

                TableRow dipRow = (TableRow) rootView.findViewById(R.id.report_disc_dip_row);
                TextView textViewDip = new TextView(getActivity());
                textViewDip.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewDip.setBackgroundResource(R.drawable.text_box);
                textViewDip.setGravity(Gravity.CENTER);
                dipRow.addView(textViewDip);
                if (currentDiscFamily != null) {
                    Short dip = currentDiscFamily.getDipDegrees();
                    if (dip != null) {
                        textViewDip.setText(dip.toString());
                    } else {
                        textViewDip.setText("-");
                    }
                } else {
                    textViewDip.setText("-");
                }

                TableRow spacingRow = (TableRow) rootView.findViewById(R.id.report_disc_spacing_row);
                TextView textViewSpacing = new TextView(getActivity());
                textViewSpacing.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewSpacing.setBackgroundResource(R.drawable.text_box);
                textViewSpacing.setGravity(Gravity.CENTER);
                spacingRow.addView(textViewSpacing);
                if (currentDiscFamily != null) {
                    Spacing discFamilySpacing = currentDiscFamily.getSpacing();
                    if (SkavaUtils.isDefined(discFamilySpacing)) {
                        textViewSpacing.setText(discFamilySpacing.getShortDescription());
                    } else {
                        textViewSpacing.setText("-");
                    }
                } else {
                    textViewSpacing.setText("-");
                }

                TableRow persistenceRow = (TableRow) rootView.findViewById(R.id.report_disc_persistence_row);
                TextView textViewPersistence = new TextView(getActivity());
                textViewPersistence.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewPersistence.setBackgroundResource(R.drawable.text_box);
                textViewPersistence.setGravity(Gravity.CENTER);
                persistenceRow.addView(textViewPersistence);
                if (currentDiscFamily != null) {
                    Persistence discFamilyPersistence = currentDiscFamily.getPersistence();
                    if (SkavaUtils.isDefined(discFamilyPersistence)) {
                        textViewPersistence.setText(discFamilyPersistence.getShortDescription());
                    } else {
                        textViewPersistence.setText("-");
                    }
                } else {
                    textViewPersistence.setText("-");
                }

                TableRow apertureRow = (TableRow) rootView.findViewById(R.id.report_disc_aperture_row);
                TextView textViewAperture = new TextView(getActivity());
                textViewAperture.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewAperture.setBackgroundResource(R.drawable.text_box);
                textViewAperture.setGravity(Gravity.CENTER);
                apertureRow.addView(textViewAperture);
                if (currentDiscFamily != null) {
                    Aperture discFamilyAperture = currentDiscFamily.getAperture();
                    if (SkavaUtils.isDefined(discFamilyAperture)) {
                        textViewAperture.setText(discFamilyAperture.getShortDescription());
                    } else {
                        textViewAperture.setText("-");
                    }
                } else {
                    textViewAperture.setText("-");
                }

                TableRow shapeRow = (TableRow) rootView.findViewById(R.id.report_disc_shape_row);
                TextView textViewShape = new TextView(getActivity());
                textViewShape.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewShape.setBackgroundResource(R.drawable.text_box);
                textViewShape.setGravity(Gravity.CENTER);
                shapeRow.addView(textViewShape);
                if (currentDiscFamily != null) {
                    DiscontinuityShape shape = currentDiscFamily.getShape();
                    if (SkavaUtils.isDefined(shape)) {
                        textViewShape.setText(shape.getName());
                    } else {
                        textViewShape.setText("-");
                    }
                } else {
                    textViewShape.setText("-");
                }

                TableRow conditionRow = (TableRow) rootView.findViewById(R.id.report_disc_condition_row);
                TextView textViewCondition = new TextView(getActivity());
                textViewCondition.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewCondition.setBackgroundResource(R.drawable.text_box);
                textViewCondition.setGravity(Gravity.CENTER);
                conditionRow.addView(textViewCondition);
                if (currentDiscFamily != null) {
                    Roughness discFamilyRoughness = currentDiscFamily.getRoughness();
                    if (SkavaUtils.isDefined(discFamilyRoughness)) {
                        textViewCondition.setText(discFamilyRoughness.getShortDescription());
                    } else {
                        textViewCondition.setText("-");
                    }
                } else {
                    textViewCondition.setText("-");
                }

                TableRow infillingRow = (TableRow) rootView.findViewById(R.id.report_disc_infilling_row);
                TextView textViewInfilling = new TextView(getActivity());
                textViewInfilling.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewInfilling.setBackgroundResource(R.drawable.text_box);
                textViewInfilling.setGravity(Gravity.CENTER);
                infillingRow.addView(textViewInfilling);
                if (currentDiscFamily != null) {
                    Infilling discFamilyInfilling = currentDiscFamily.getInfilling();
                    if (SkavaUtils.isDefined(discFamilyInfilling)) {
                        textViewInfilling.setText(discFamilyInfilling.getShortDescription());
                    } else {
                        textViewInfilling.setText("-");
                    }
                } else {
                    textViewInfilling.setText("-");
                }

                TableRow weatheringRow = (TableRow) rootView.findViewById(R.id.report_disc_weathering_row);
                TextView textViewWeathering = new TextView(getActivity());
                textViewWeathering.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewWeathering.setBackgroundResource(R.drawable.text_box);
                textViewWeathering.setGravity(Gravity.CENTER);
                weatheringRow.addView(textViewWeathering);
                if (currentDiscFamily != null) {
                    Weathering discFamilyWeathering = currentDiscFamily.getWeathering();
                    if (SkavaUtils.isDefined(discFamilyWeathering)) {
                        textViewWeathering.setText(discFamilyWeathering.getShortDescription());
                    } else {
                        textViewWeathering.setText("-");
                    }
                } else {
                    textViewWeathering.setText("-");
                }


                TableRow jrRow = (TableRow) rootView.findViewById(R.id.report_disc_jr_row);
                TextView textViewJr = new TextView(getActivity());
                textViewJr.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewJr.setBackgroundResource(R.drawable.text_box);
                textViewJr.setGravity(Gravity.CENTER);
                jrRow.addView(textViewJr);
                if (currentDiscFamily != null) {
                    Jr discFamilyJr = currentDiscFamily.getJr();
                    if (SkavaUtils.isDefined(discFamilyJr)) {
                        textViewJr.setText(discFamilyJr.getShortDescription());
                    } else {
                        textViewJr.setText("-");
                    }
                } else {
                    textViewJr.setText("-");
                }

                TableRow jaRow = (TableRow) rootView.findViewById(R.id.report_disc_ja_row);
                TextView textViewJa = new TextView(getActivity());
                textViewJa.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewJa.setBackgroundResource(R.drawable.text_box);
                textViewJa.setGravity(Gravity.CENTER);
                jaRow.addView(textViewJa);
                if (currentDiscFamily != null) {
                    Ja discFamilyJa = currentDiscFamily.getJa();
                    if (SkavaUtils.isDefined(discFamilyJa)) {
                        textViewJa.setText(discFamilyJa.getShortDescription());
                    } else {
                        textViewJa.setText("-");
                    }
                } else {
                    textViewJa.setText("-");
                }


                TableRow dipDirWater = (TableRow) rootView.findViewById(R.id.report_disc_water_row);
                TextView textViewWater = new TextView(getActivity());
                textViewWater.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textViewWater.setBackgroundResource(R.drawable.text_box);
                textViewWater.setGravity(Gravity.CENTER);
                dipDirWater.addView(textViewWater);
                if (currentDiscFamily != null) {
                    DiscontinuityWater water = currentDiscFamily.getWater();
                    if (SkavaUtils.isDefined(water)) {
                        textViewWater.setText(water.getName());
                    } else {
                        textViewWater.setText("-");
                    }
                } else {
                    textViewWater.setText("-");
                }

            }
        }

        List<Uri> pictureList = currentAssessment.getPictureUriList();
        SkavaPictureFilesUtils pictureFilesUtils = new SkavaPictureFilesUtils(this.getActivity());
        if (pictureList != null && !pictureList.isEmpty()) {
            int pictureListSize = pictureList.size();

            Uri faceUri = pictureList.get(0);
            if (faceUri != null) {
                Bitmap bitmap = pictureFilesUtils.getSampledBitmapFromFile(faceUri, 800, 600);
                if (bitmap != null) {
                    ((ImageView) rootView.findViewById(R.id.summary_report_face_pic)).setImageBitmap(bitmap);
                }
            }

            if (pictureListSize > 1) {
                Uri leftUri = pictureList.get(1);
                if (leftUri != null) {
                    Bitmap bitmap = pictureFilesUtils.getSampledBitmapFromFile(leftUri, 400, 300);
                    if (bitmap != null) {
                        ((ImageView) rootView.findViewById(R.id.summary_report_left_wall_pic)).setImageBitmap(bitmap);
                    }
                }
            }
            if (pictureListSize > 2) {
                Uri rightUri = pictureList.get(2);
                if (rightUri != null) {
                    Bitmap bitmap = pictureFilesUtils.getSampledBitmapFromFile(rightUri, 400, 300);
                    if (bitmap != null) {
                        ((ImageView) rootView.findViewById(R.id.summary_report_right_wall_pic)).setImageBitmap(bitmap);
                    }
                }
            }
            if (pictureListSize > 3) {
                Uri roofUri = pictureList.get(3);
                if (roofUri != null) {
                    Bitmap bitmap = pictureFilesUtils.getSampledBitmapFromFile(roofUri, 400, 300);
                    if (bitmap != null) {
                        ((ImageView) rootView.findViewById(R.id.summary_report_roof_pic)).setImageBitmap(bitmap);
                    }
                }
            }
        }

        String outcrop = currentAssessment.getOutcropDescription();
        if (outcrop != null) {
            ((TextView) rootView.findViewById(R.id.summary_report_outcrop_description_value)).setText(outcrop);
        }

        String rockSampleIdentification = currentAssessment.getRockSampleIdentification();
        if (rockSampleIdentification != null) {
            ((TextView) rootView.findViewById(R.id.summary_report_rock_sample_identification_value)).setText(rockSampleIdentification);
        }

        return rootView;
    }

    private void calculateQBarton() {
        Q_Calculation qCalculation = getQCalculationContext();
        QBartonInput input = new QBartonInput();
        input.setRqd(qCalculation.getRqd().getValue());
        input.setJn(qCalculation.getJn().getValue());
        input.setJr(qCalculation.getJr().getValue());
        input.setJa(qCalculation.getJa().getValue());
        input.setJw(qCalculation.getJw().getValue());
        input.setSrf(qCalculation.getSrf().getValue());
        QBartonOutput output = QBartonCalculator.calculate(input);
        getQCalculationContext().setQResult(output);
    }

    private void calculateRMR() {
        RMR_Calculation RMRCalculation = getRMRCalculationContext();
        RMRInput input = new RMRInput();
        input.setStrength(RMRCalculation.getStrengthOfRock().getValue());
        if (RMRCalculation.getRqd() == null) {
            //Pull RQD from QBartonCalculation
            RQD rqd = getQCalculationContext().getRqd();
            RQD_RMR rqdRMR = RQD_RMR.findWrapper(rqd);
            RMRCalculation.setRqd(rqdRMR);
        }
        input.setRqd(Double.valueOf(RMRCalculation.getRqd().getValue()));
        input.setSpacing(RMRCalculation.getSpacing().getValue());
        input.setRoughness(RMRCalculation.getRoughness().getValue());
        input.setAperture(RMRCalculation.getAperture().getValue());
        input.setInfilling(RMRCalculation.getInfilling().getValue());
        input.setWeathering(RMRCalculation.getWeathering().getValue());
        input.setPersistence(RMRCalculation.getPersistence().getValue());
        input.setGroundwater(RMRCalculation.getGroundwater().getValue());
        input.setOrientation(RMRCalculation.getOrientationDiscontinuities().getValue());
        RMROutput output = RMRCalculator.calculate(input);
        getRMRCalculationContext().setRMRResult(output);
    }
}
