package com.metric.skava.calculator.model;


public abstract class MappedIndex  {

    public static String DESCRIPTION = "Description";

	private Double value;
	private String key;
    private String[][] categoriesAndValues;

    private int groupType;

//    public abstract String getGroupTypeName();

    protected MappedIndex(){
        categoriesAndValues = new String[2][3];
	}

	public MappedIndex(String key, Double value) {
		super();
		this.value = value;
		this.key = key;
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
    public String[][] getCategoriesAndValues() {
        return categoriesAndValues;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
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


    public String getShortDescription() {
        String description = getCategoriesAndValues()[1][0];
        int length = description.length();
        if (length > 25){
            return description.substring(0,25);
        }
        return description;
    }

	@Override
	public String toString() {
        return key + ".  " + getShortDescription();
    }

}
