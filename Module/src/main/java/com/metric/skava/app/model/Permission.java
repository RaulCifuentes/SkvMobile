package com.metric.skava.app.model;

import com.metric.skava.app.data.IdentifiableEntity;

/**
 * Created by metricboy on 3/26/14.
 */
public class Permission {

    public enum Action {READ, WRITE, ALL}

    public enum IdentifiableEntityType {PROJECT, TUNNEL, FACE};

    private String environment;
    private User who;
    private Action what;
    private IdentifiableEntityType where;
    private IdentifiableEntity whereExactly;


    public Permission(String environment, User who, Action what, IdentifiableEntityType where, IdentifiableEntity whereExactly ) {
        this.environment = environment;
        this.who = who;
        this.what = what;
        this.where = where;
        this.whereExactly = whereExactly;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public User getWho() {
        return who;
    }

    public void setWho(User who) {
        this.who = who;
    }

    public Action getWhat() {
        return what;
    }

    public void setWhat(Action what) {
        this.what = what;
    }

    public void setWhere(IdentifiableEntityType where) {
        this.where = where;
    }

    public IdentifiableEntityType getWhere() {
        return where;
    }

    public IdentifiableEntity getWhereExactly() {
        return whereExactly;
    }

    public void setWhereExactly(IdentifiableEntity whereExactly) {
        this.whereExactly = whereExactly;
    }
}
