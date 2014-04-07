package com.metric.skava.calculator.barton.logic;

import android.os.Parcel;
import android.os.Parcelable;

public class QBartonInput implements Parcelable {

	private Integer rqd;
	private Double jn;
	private Double jr;
	private Double ja;
	private Double jw;
	private Double srf;
	
	public QBartonInput() {	
	}

	public Integer getRqd() {
		return rqd;
	}

	public void setRqd(Integer rqd) {
		this.rqd = rqd;
	}

	public Double getJn() {
		return jn;
	}

	public void setJn(Double jn) {
		this.jn = jn;
	}

	public Double getJr() {
		return jr;
	}

	public void setJr(Double jr) {
		this.jr = jr;
	}

	public Double getJa() {
		return ja;
	}

	public void setJa(Double ja) {
		this.ja = ja;
	}

	public Double getJw() {
		return jw;
	}

	public void setJw(Double jw) {
		this.jw = jw;
	}

	public Double getSrf() {
		return srf;
	}

	public void setSrf(Double srf) {
		this.srf = srf;
	}

	public boolean isComplete(){
		return (rqd != null) && (jn != null) && (jr != null) && (ja != null) && (jw != null) && (srf != null); 
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	/** save object in parcel */
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(rqd);
		out.writeDouble(jn);
		out.writeDouble(jr);
		out.writeDouble(ja);
		out.writeDouble(jw);
		out.writeDouble(srf);
	}

	public static final Creator<QBartonInput> CREATOR = new Creator<QBartonInput>() {
		public QBartonInput createFromParcel(Parcel in) {
			return new QBartonInput(in);
		}

		public QBartonInput[] newArray(int size) {
			return new QBartonInput[size];
		}
	};

	/** recreate object from parcel */
	private QBartonInput(Parcel in) {
		rqd = in.readInt();
		jn = in.readDouble();
		jr = in.readDouble();
		ja = in.readDouble();
		jw = in.readDouble();
		srf = in.readDouble();
	}

	
	
	
	
}
