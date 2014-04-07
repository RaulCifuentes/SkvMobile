package com.metric.skava.app.data;

/**
 * Created by metricboy on 2/21/14.
 */
public class SkavaEntity implements IdentifiableEntity {

    private String code;
    private String name;

    public SkavaEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkavaEntity)) return false;

        SkavaEntity that = (SkavaEntity) o;

        if (!code.equals(that.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }
}
