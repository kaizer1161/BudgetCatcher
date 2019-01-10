package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cobrand {

    @SerializedName("cobrandLogin")
    @Expose
    private String cobrandLogin;
    @SerializedName("cobrandPassword")
    @Expose
    private String cobrandPassword;
    @SerializedName("locale")
    @Expose
    private String locale;

    /**
     * No args constructor for use in serialization
     */
    public Cobrand() {
    }

    /**
     * @param locale
     * @param cobrandLogin
     * @param cobrandPassword
     */
    public Cobrand(String cobrandLogin, String cobrandPassword, String locale) {
        super();
        this.cobrandLogin = cobrandLogin;
        this.cobrandPassword = cobrandPassword;
        this.locale = locale;
    }

    public String getCobrandLogin() {
        return cobrandLogin;
    }

    public void setCobrandLogin(String cobrandLogin) {
        this.cobrandLogin = cobrandLogin;
    }

    public String getCobrandPassword() {
        return cobrandPassword;
    }

    public void setCobrandPassword(String cobrandPassword) {
        this.cobrandPassword = cobrandPassword;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
