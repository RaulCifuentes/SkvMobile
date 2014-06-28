package com.metric.skava.sync.model;

/**
 * Created by metricboy on 6/7/14.
 */
public class SyncTask {

    public enum Status {SUCCESS, FAIL}

    public enum Source {DROPBOX_LOCAL_DATASTORE, DROPBOX_REMOTE_DATASTORE, DROPBOX_LOCAL_FILESYSTEM, DROPBOX_REMOTE_FILESYSTEM}

    public enum Domain {
        ALL_APP_DATA_TABLES,
        ALL_USER_DATA_TABLES,
        PICTURES,
        ROLES,
        EXCAVATION_METHODS,
        EXCAVATION_SECTIONS,
        DISCONTINUITY_TYPES,
        DISCONTINUITY_RELEVANCES,
        INDEXES,
        GROUPS,
        SPACINGS,
        PERSISTENCES,
        APERTURES,
        DISCONTINUITY_SHAPES,
        ROUGHNESSES,
        INFILLINGS,
        WEATHERINGS,
        DISCONTINUITY_WATERS,
        STRENGTHS,
        GROUDWATERS,
        ORIENTATION,
        JN,
        JR,
        JA,
        JW,
        SRF,
        FRACTURE_TYPES,
        BOLT_TYPES,
        SHOTCRETE_TYPES,
        MESHTYPES,
        COVERAGES,
        ARCHTYPES,
        ESRS,
        SUPPORT_PATTERN_TYPES,
        CLIENTS,
        EXCAVATION_PROJECTS,
        TUNNELS,
        SUPPORT_REQUIREMENTS,
        TUNNEL_FACES,
        USERS,
        ROCK_QUALITIES,
        //AND NOW MY TABLES
        ASSESSMENTS,
        Q_CALCULATIONS,
        RMR_CALCULATIONS,
        SUPPORT_RECOMMENDATIONS,
    }

}
