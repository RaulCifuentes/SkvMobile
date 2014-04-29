package com.metric.skava.calculator.model;


import com.metric.skava.app.data.IdentifiableEntity;

public abstract class MappedIndex implements IdentifiableEntity {

    public static String DESCRIPTION = "Description";

    private String code;
    private String key;
    private Double value;
    private String shortDescription;
    private String description;


    private MappedIndex(){
        super();
	}

	public MappedIndex(String code, String key, String shortDescription, String description, Double value) {
        this();
        this.code = code;
        this.key = key;
        this.shortDescription = shortDescription;
        this.description = description;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

    /**
     * This is a 2-row 3-column structure to store as this
     * MappedIndex.DESCRIPTION | MappedIndexSubclass.KeyOne  | MappedIndexSubclass.KeyTwo
     *  DESCRIPTION Value      |     Value                   |     Value
     * @return
     */
//    public String[][] getCategoriesAndValues() {
//        return categoriesAndValues;
//    }

    public abstract String getGroupName();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }


//
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappedIndex other = (MappedIndex) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


	@Override
	public String toString() {
        return key + ".  " + getShortDescription();
    }

}
