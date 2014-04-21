package com.metric.skava.calculator.barton.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.metric.skava.BuildConfig;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.barton.logic.QBartonCalculator;
import com.metric.skava.calculator.barton.logic.QBartonInput;
import com.metric.skava.calculator.barton.logic.QBartonOutput;

public class Q_Calculation implements Parcelable {

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

	public Ja getJa() {
		return ja;
	}

	public void setJa(Ja ja) {
		this.ja = ja;
	}

	public Jn getJn() {
		return jn;
	}

	public void setJn(Jn jn) {
		this.jn = jn;
	}

	public Jr getJr() {
		return jr;
	}

	public void setJr(Jr jr) {
		this.jr = jr;
	}

	public Jw getJw() {
		return jw;
	}

	public void setJw(Jw jw) {
		this.jw = jw;
	}

	public RQD getRqd() {
		return rqd;
	}

	public void setRqd(RQD rqd) {
		this.rqd = rqd;
	}

	public SRF getSrf() {
		return sRF;
	}

	public void setSrf(SRF sRF) {
		this.sRF = sRF;
	}

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public QBartonOutput getQResult() {
        if (qResult == null) {
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
			Log.d(SkavaConstants.LOG, "Entering " + this. getClass().getSimpleName()
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
				Log.d(SkavaConstants.LOG, "Entering " + this. getClass().getSimpleName()
						+ " : createFromParcel ");
			}
			RQD rqd = (RQD) in.readValue(getClass().getClassLoader());
			Jn jn = (Jn) in.readValue(getClass().getClassLoader());
			Jr jr = (Jr) in.readValue(getClass().getClassLoader());
			Ja ja = (Ja) in.readValue(getClass().getClassLoader());
			Jw jw = (Jw) in.readValue(getClass().getClassLoader());
			SRF sRF = (SRF) in.readValue(getClass().getClassLoader());
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
		rqd = (RQD) in.readValue(getClass().getClassLoader());
		jn = (Jn) in.readValue(getClass().getClassLoader());
		jr = (Jr) in.readValue(getClass().getClassLoader());
		ja = (Ja) in.readValue(getClass().getClassLoader());
		jw = (Jw) in.readValue(getClass().getClassLoader());
		sRF = (SRF) in.readValue(getClass().getClassLoader());
	}

}
