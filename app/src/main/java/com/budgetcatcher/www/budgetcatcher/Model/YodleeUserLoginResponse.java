package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YodleeUserLoginResponse {

    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     */
    public YodleeUserLoginResponse() {
    }

    /**
     * @param user
     */
    public YodleeUserLoginResponse(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("loginName")
        @Expose
        private String loginName;
        @SerializedName("name")
        @Expose
        private Name name;
        @SerializedName("roleType")
        @Expose
        private String roleType;
        @SerializedName("session")
        @Expose
        private Session session;
        @SerializedName("preferences")
        @Expose
        private Preferences preferences;

        /**
         * No args constructor for use in serialization
         */
        public User() {
        }

        /**
         * @param id
         * @param session
         * @param name
         * @param roleType
         * @param preferences
         * @param loginName
         */
        public User(Integer id, String loginName, Name name, String roleType, Session session, Preferences preferences) {
            super();
            this.id = id;
            this.loginName = loginName;
            this.name = name;
            this.roleType = roleType;
            this.session = session;
            this.preferences = preferences;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public String getRoleType() {
            return roleType;
        }

        public void setRoleType(String roleType) {
            this.roleType = roleType;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public Preferences getPreferences() {
            return preferences;
        }

        public void setPreferences(Preferences preferences) {
            this.preferences = preferences;
        }

        public class Session {

            @SerializedName("userSession")
            @Expose
            private String userSession;

            /**
             * No args constructor for use in serialization
             */
            public Session() {
            }

            /**
             * @param userSession
             */
            public Session(String userSession) {
                super();
                this.userSession = userSession;
            }

            public String getUserSession() {
                return userSession;
            }

            public void setUserSession(String userSession) {
                this.userSession = userSession;
            }

        }

        public class Name {

            @SerializedName("first")
            @Expose
            private String first;
            @SerializedName("last")
            @Expose
            private String last;

            /**
             * No args constructor for use in serialization
             */
            public Name() {
            }

            /**
             * @param last
             * @param first
             */
            public Name(String first, String last) {
                super();
                this.first = first;
                this.last = last;
            }

            public String getFirst() {
                return first;
            }

            public void setFirst(String first) {
                this.first = first;
            }

            public String getLast() {
                return last;
            }

            public void setLast(String last) {
                this.last = last;
            }

        }

    }

    public class Preferences {

        @SerializedName("currency")
        @Expose
        private String currency;
        @SerializedName("timeZone")
        @Expose
        private String timeZone;
        @SerializedName("dateFormat")
        @Expose
        private String dateFormat;
        @SerializedName("locale")
        @Expose
        private String locale;

        /**
         * No args constructor for use in serialization
         */
        public Preferences() {
        }

        /**
         * @param locale
         * @param dateFormat
         * @param timeZone
         * @param currency
         */
        public Preferences(String currency, String timeZone, String dateFormat, String locale) {
            super();
            this.currency = currency;
            this.timeZone = timeZone;
            this.dateFormat = dateFormat;
            this.locale = locale;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getDateFormat() {
            return dateFormat;
        }

        public void setDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

    }

}