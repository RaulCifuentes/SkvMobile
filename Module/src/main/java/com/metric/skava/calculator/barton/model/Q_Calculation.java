package com.metric.skava.calculator.barton.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.metric.skava.BuildConfig;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.barton.logic.QBartonCalculator;
import com.metric.skava.calculator.barton.logic.QBartonInput;
import com.metric.skava.calculator.barton.logic.QBartonOutput;

import java.util.Observable;

public class Q_Calculation extends Observable implements Parcelable {

    private Long _id;
	private RQD rqd;
	private Jn jn;
	private Jr jr;
	private Ja ja;
	private Jw jw;	
	private SRF sRF;

    private QBartonOutput qResult;

    public Q_Calculation(RQD rqd, Jn jn, Jr jr, Ja ja, Jw jw, SRF sRF) {
		super();
		this.rqd = rqd;
		this.jn = jn;
		this.jr = jr;
		this.ja = ja;
		this.jw = jw;
		this.sRF = sRF;
    }

    private void triggerObservers(Object data) {
        //This was an idea to listen for changes and update the color of the tab
//        setChanged();
//        notifyObservers(data);
    }

	public Ja getJa() {
		return ja;
	}

	public void setJa(Ja ja) {
        if (ja != null) {
            this.ja = ja;
            triggerObservers(ja);
        }
    }

	public Jn getJn() {
		return jn;
	}

	public void setJn(Jn jn) {
        if (jn != null) {
            this.jn = jn;
            triggerObservers(jn);
        }
	}

	public Jr getJr() {
		return jr;
	}

	public void setJr(Jr jr) {
		this.jr = jr;
        triggerObservers(jr);
	}

	public Jw getJw() {
		return jw;
	}

	public void setJw(Jw jw) {
		this.jw = jw;
        triggerObservers(jw);
	}

	public RQD getRqd() {
		return rqd;
	}

	public void setRqd(RQD rqd) {
        if (rqd != null) {
            this.rqd = rqd;
            triggerObservers(rqd);
        }
	}

	public SRF getSrf() {
		return sRF;
	}

	public void setSrf(SRF sRF) {
        if (sRF != null) {
            this.sRF = sRF;
            triggerObservers(sRF);
        }
	}

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public QBartonOutput getQResult() {
        if (qResult == null && isComplete()) {
            QBartonInput input = new QBartonInput();
            input.setRqd(getRqd().getValue());
            input.setJn(getJn().getValue());
            input.setJr(getJr().getValue());
            input.setJa(getJa().getValue());
            input.setJw(getJw().getValue());
            input.setSrf(getSrf().getValue());
            qResult = QBartonCalculator.calculate(input);
        }
        return qResult;
    }

    public void setQResult(QBartonOutput qResult) {
        this.qResult = qResult;
    }

    @Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		if (BuildConfig.DEBUG) {
			Log.d(SkavaConstants.LOG, "Entering " + Q_Calculation.class.getSimpleName()
					+ " : writeToParcel ");
		}
		out.writeValue(rqd);
		out.writeValue(jn);
		out.writeValue(jr);
		out.writeValue(ja);
		out.writeValue(jw);
		out.writeValue(sRF);
	}

	// This is used to regenerate your object.
	// All Parcelables must have a CREATOR that implements these two methods
	public static final Creator<Q_Calculation> CREATOR = new Creator<Q_Calculation>() {

		public Q_Calculation createFromParcel(Parcel in) {
			if (BuildConfig.DEBUG) {
				Log.d(SkavaConstants.LOG, "Entering " + Q_Calculation.class.getSimpleName()
						+ " : createFromParcel ");
			}
			RQD rqd = (RQD) in.readValue(Q_Calculation.class.getClassLoader());
			Jn jn = (Jn) in.readValue(Q_Calculation.class.getClassLoader());
			Jr jr = (Jr) in.readValue(Q_Calculation.class.getClassLoader());
			Ja ja = (Ja) in.readValue(Q_Calculation.class.getClassLoader());
			Jw jw = (Jw) in.readValue(Q_Calculation.class.getClassLoader());
			SRF sRF = (SRF) in.readValue(Q_Calculation.class.getClassLoader());
			Q_Calculation instance = new Q_Calculation(rqd, jn, jr, ja, jw, sRF);
			return instance;
		}

		public Q_Calculation[] newArray(int size) {
			return new Q_Calculation[size];
		}

	};

	// Private constructor used when demarshalling (unserializing the custom
	// object)
	private Q_Calculation(Parcel in) {
		rqd = (RQD) in.readValue(Q_Calculation.class.getClassLoader());
		jn = (Jn) in.readValue(Q_Calculation.class.getClassLoader());
		jr = (Jr) in.readValue(Q_Calculation.class.getClassLoader());
		ja = (Ja) in.readValue(Q_Calculation.class.getClassLoader());
		jw = (Jw) in.readValue(Q_Calculation.class.getClassLoader());
		sRF = (SRF) in.readValue(Q_Calculation.class.getClassLoader());
	}

    public boolean isComplete() {
        return (rqd != null) && (jn != null) && (jr != null) && (ja != null) && (jw != null) && (sRF != null);
    }
}
