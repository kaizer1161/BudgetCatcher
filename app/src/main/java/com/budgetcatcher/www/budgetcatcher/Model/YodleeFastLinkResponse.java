package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class YodleeFastLinkResponse {

    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     */
    public YodleeFastLinkResponse() {
    }

    /**
     * @param user
     */
    public YodleeFastLinkResponse(User user) {
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

        @SerializedName("accessTokens")
        @Expose
        private List<AccessToken> accessTokens = null;

        /**
         * No args constructor for use in serialization
         */
        public User() {
        }

        /**
         * @param accessTokens
         */
        public User(List<AccessToken> accessTokens) {
            super();
            this.accessTokens = accessTokens;
        }

        public List<AccessToken> getAccessTokens() {
            return accessTokens;
        }

        public void setAccessTokens(List<AccessToken> accessTokens) {
            this.accessTokens = accessTokens;
        }

    }

    public class AccessToken {

        @SerializedName("appId")
        @Expose
        private String appId;
        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("url")
        @Expose
        private String url;

        /**
         * No args constructor for use in serialization
         */
        public AccessToken() {
        }

        /**
         * @param appId
         * @param value
         * @param url
         */
        public AccessToken(String appId, String value, String url) {
            super();
            this.appId = appId;
            this.value = value;
            this.url = url;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

}