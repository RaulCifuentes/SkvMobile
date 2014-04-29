package com.metric.skava.calculator.rmr.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.metric.skava.BuildConfig;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.rmr.logic.RMRCalculator;
import com.metric.skava.calculator.rmr.logic.RMRInput;
import com.metric.skava.calculator.rmr.logic.RMROutput;

public class RMR_Calculation implements Parcelable {

    private Long _id;
    private StrengthOfRock strengthOfRock;
    private RQD_RMR rqd;
    private Spacing spacing;
    //    private ConditionDiscontinuities conditionDiscontinuities;
    private Persistence persistence;
    private Aperture aperture;
    private Roughness roughness;
    private Infilling infilling;
    private Weathering weathering;
    private Groundwater groundwater;
    /**
     * Special case coz need to distinghuis among three types for rating values
     *
     * @param type  TUNNEL_MINES | FOUNDATIONS | SLOPES
     */
    private int mOrientationType;
    private OrientationDiscontinuities orientationDiscontinuities;

    private RMROutput rmrResult;


    //    public RMR_Calculation(StrengthOfRock strenght, RQD_RMR rqd, Spacing spacing, ConditionDiscontinuities condition, Persistence persistence, Aperture aperture, Roughness roughness, Infilling infilling, Weathering weathering, Groundwater groundwater, OrientationDiscontinuities orientation) {
    public RMR_Calculation(StrengthOfRock strenght, RQD_RMR rqd, Spacing spacing, Persistence persistence, Aperture aperture, Roughness roughness, Infilling infilling, Weathering weathering, Groundwater groundwater, OrientationDiscontinuities orientation) {
//    public RMR_Calculation(StrengthOfRock strenght, RQD rqd, Spacing spacing, Persistence persistence, Aperture aperture, Roughness roughness, Infilling infilling, Weathering weathering, Groundwater groundwater, OrientationDiscontinuities orientation) {
        super();
        this.strengthOfRock = strenght;
        this.rqd = rqd;
        this.spacing = spacing;
//        this.conditionDiscontinuities = condition;
        this.persistence = persistence;
        this.aperture = aperture;
        this.roughness = roughness;
        this.infilling = infilling;
        this.weathering = weathering;
        this.groundwater = groundwater;
        this.orientationDiscontinuities = orientation;
    }

    public Groundwater getGroundwater() {
        return groundwater;
    }

    public void setGroundwater(Groundwater groundwater) {
        this.groundwater = groundwater;
    }

    public StrengthOfRock getStrengthOfRock() {
        return strengthOfRock;
    }

    public void setStrengthOfRock(StrengthOfRock strengthOfRock) {
        this.strengthOfRock = strengthOfRock;
    }

    public Spacing getSpacing() {
        return spacing;
    }

    public void setSpacing(Spacing spacing) {
        this.spacing = spacing;
    }

//    public ConditionDiscontinuities getConditionDiscontinuities() {
//        return conditionDiscontinuities;
//    }
//
//    public void setConditionDiscontinuities(ConditionDiscontinuities conditionDiscontinuities) {
//        this.conditionDiscontinuities = conditionDiscontinuities;
//    }

    public int getOrientationType() {
        return mOrientationType;
    }

    public void setOrientationType(int mOrientationType) {
        this.mOrientationType = mOrientationType;
    }

    public OrientationDiscontinuities getOrientationDiscontinuities() {
        return orientationDiscontinuities;
    }

    public void setOrientationDiscontinuities(OrientationDiscontinuities orientationDiscontinuities) {
        this.orientationDiscontinuities = orientationDiscontinuities;
    }

    public RQD_RMR getRqd() {
        return rqd;
    }

    public void setRqd(RQD_RMR rqd) {
        this.rqd = rqd;
    }

    public Aperture getAperture() {
        return aperture;
    }

    public void setAperture(Aperture aperture) {
        this.aperture = aperture;
    }

    public Roughness getRoughness() {
        return roughness;
    }

    public void setRoughness(Roughness roughness) {
        this.roughness = roughness;
    }

    public Infilling getInfilling() {
        return infilling;
    }

    public void setInfilling(Infilling infilling) {
        this.infilling = infilling;
    }

    public Persistence getPersistence() {
        return persistence;
    }

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

    public Weathering getWeathering() {
        return weathering;
    }

    public void setWeathering(Weathering weathering) {
        this.weathering = weathering;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public RMROutput getRMRResult() {
        if (rmrResult == null) {
            RMRInput input = new RMRInput();
            input.setStrength(getStrengthOfRock().getValue());
            input.setRqd(Double.valueOf(getRqd().getValue()));
            input.setSpacing(getSpacing().getValue());
            input.setGroundwater(getGroundwater().getValue());
            input.setOrientation(getOrientationDiscontinuities().getValue());
//            if (getConditionDiscontinuities() != null){
//                //Dividir el valor resumido en cada una de las 5 categorias
//                input.setCondition(getConditionDiscontinuities().getValue());
////                input.setPersistence(equallyDistributed);
////                input.setAperture(equallyDistributed);
////                input.setRoughness(equallyDistributed);
////                input.setInfilling();
////                input.setWeathering();
//            } else {
            input.setPersistence(getPersistence().getValue());
            input.setAperture(getAperture().getValue());
            input.setRoughness(getRoughness().getValue());
            input.setInfilling(getInfilling().getValue());
            input.setWeathering(getWeathering().getValue());
//            }
            rmrResult = RMRCalculator.calculate(input);
        }
        return rmrResult;
    }

    public void setRMRResult(RMROutput rmrResult) {
        this.rmrResult = rmrResult;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering " + RMR_Calculation.class.getSimpleName()
                    + " : writeToParcel ");
        }
        out.writeValue(strengthOfRock);
        out.writeValue(rqd);
        out.writeValue(spacing);
        out.writeValue(persistence);
        out.writeValue(aperture);
        out.writeValue(roughness);
        out.writeValue(infilling);
        out.writeValue(weathering);
        out.writeValue(groundwater);
        out.writeValue(orientationDiscontinuities);

//        out.writeValue(conditionDiscontinuities);
    }

    // This is used to regenerate your object.
    // All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<RMR_Calculation> CREATOR = new Creator<RMR_Calculation>() {

        public RMR_Calculation createFromParcel(Parcel in) {
            if (BuildConfig.DEBUG) {
                Log.d(SkavaConstants.LOG, "Entering " + RMR_Calculation.class.getSimpleName()
                        + " : createFromParcel ");
            }
            StrengthOfRock strength = (StrengthOfRock) in.readValue(RMR_Calculation.class.getClassLoader());
            RQD_RMR rqd = (RQD_RMR) in.readValue(RMR_Calculation.class.getClassLoader());
            Spacing spacing = (Spacing) in.readValue(RMR_Calculation.class.getClassLoader());
//            ConditionDiscontinuities condition = (ConditionDiscontinuities) in.readValue(RMR_Calculation.class.getClassLoader());
            Persistence persistence = (Persistence) in.readValue(RMR_Calculation.class.getClassLoader());
            Aperture aperture = (Aperture) in.readValue(RMR_Calculation.class.getClassLoader());
            Roughness roughness = (Roughness) in.readValue(RMR_Calculation.class.getClassLoader());
            Infilling infilling = (Infilling) in.readValue(RMR_Calculation.class.getClassLoader());
            Weathering weathering = (Weathering) in.readValue(RMR_Calculation.class.getClassLoader());
            Groundwater groundwater = (Groundwater) in.readValue(RMR_Calculation.class.getClassLoader());
            OrientationDiscontinuities orientation = (OrientationDiscontinuities) in.readValue(RMR_Calculation.class.getClassLoader());

//            RMR_Calculation instance = new RMR_Calculation(strength, rqd, spacing, condition, persistence, aperture, roughness, infilling, weathering , groundwater, orientation);
            RMR_Calculation instance = new RMR_Calculation(strength, rqd, spacing, persistence, aperture, roughness, infilling, weathering, groundwater, orientation);
            return instance;
        }

        public RMR_Calculation[] newArray(int size) {
            return new RMR_Calculation[size];
        }

    };

    // Private constructor used when demarshalling (unserializing the custom
    // object)
    private RMR_Calculation(Parcel in) {
        strengthOfRock = (StrengthOfRock) in.readValue(RMR_Calculation.class.getClassLoader());
        rqd = (RQD_RMR) in.readValue(RMR_Calculation.class.getClassLoader());
        spacing = (Spacing) in.readValue(RMR_Calculation.class.getClassLoader());
//        conditionDiscontinuities = (ConditionDiscontinuities) in.readValue(RMR_Calculation.class.getClassLoader());
        persistence = (Persistence) in.readValue(RMR_Calculation.class.getClassLoader());
        aperture = (Aperture) in.readValue(RMR_Calculation.class.getClassLoader());
        roughness = (Roughness) in.readValue(RMR_Calculation.class.getClassLoader());
        infilling = (Infilling) in.readValue(RMR_Calculation.class.getClassLoader());
        weathering = (Weathering) in.readValue(RMR_Calculation.class.getClassLoader());
        groundwater = (Groundwater) in.readValue(RMR_Calculation.class.getClassLoader());
        orientationDiscontinuities = (OrientationDiscontinuities) in.readValue(RMR_Calculation.class.getClassLoader());
    }

}
