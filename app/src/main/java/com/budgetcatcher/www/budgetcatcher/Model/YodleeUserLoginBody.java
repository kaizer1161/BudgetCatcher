package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YodleeUserLoginBody {

    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     */
    public YodleeUserLoginBody() {
    }

    /**
     * @param user
     */
    public YodleeUserLoginBody(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {

        @SerializedName("loginName")
        @Expose
        private String loginName;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("locale")
        @Expose
        private String locale;

        /**
         * No args constructor for use in serialization
         */
        public User() {
        }

        /**
         * @param locale
         * @param password
         * @param loginName
         */
        public User(String loginName, String password, String locale) {
            super();
            this.loginName = loginName;
            this.password = password;
            this.locale = locale;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

    }

}