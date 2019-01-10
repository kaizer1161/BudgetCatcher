package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CobrandLoginResponse {

    @SerializedName("cobrandId")
    @Expose
    private Integer cobrandId;
    @SerializedName("applicationId")
    @Expose
    private String applicationId;
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("session")
    @Expose
    private Session session;

    /**
     * No args constructor for use in serialization
     */
    public CobrandLoginResponse() {
    }

    /**
     * @param session
     * @param locale
     * @param applicationId
     * @param cobrandId
     */
    public CobrandLoginResponse(Integer cobrandId, String applicationId, String locale, Session session) {
        super();
        this.cobrandId = cobrandId;
        this.applicationId = applicationId;
        this.locale = locale;
        this.session = session;
    }

    public Integer getCobrandId() {
        return cobrandId;
    }

    public void setCobrandId(Integer cobrandId) {
        this.cobrandId = cobrandId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
