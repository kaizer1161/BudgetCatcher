package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YodleeCreateManualAccountBody {

    @SerializedName("account")
    @Expose
    private Account account;

    /**
     * No args constructor for use in serialization
     */
    public YodleeCreateManualAccountBody() {
    }

    /**
     * @param account
     */
    public YodleeCreateManualAccountBody(Account account) {
        super();
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public static class Account {

        @SerializedName("accountType")
        @Expose
        private String accountType;
        @SerializedName("accountName")
        @Expose
        private String accountName;
        /*@SerializedName("nickname")
        @Expose
        private String nickname;*/
        @SerializedName("accountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("balance")
        @Expose
        private Balance balance;
        /*@SerializedName("includeInNetWorth")
        @Expose
        private String includeInNetWorth;*/
        @SerializedName("memo")
        @Expose
        private String memo;

        /**
         * No args constructor for use in serialization
         */
        public Account() {
        }

        /**
         * @param balance
         * @param accountNumber
         * @param accountName
         * @param memo
         * @param accountType
         */
        public Account(String accountType, String accountName, /*String nickname,*/ String accountNumber, Balance balance, /*String includeInNetWorth,*/ String memo) {
            super();
            this.accountType = accountType;
            this.accountName = accountName;
            /*this.nickname = nickname;*/
            this.accountNumber = accountNumber;
            this.balance = balance;
            /*this.includeInNetWorth = includeInNetWorth;*/
            this.memo = memo;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        /*public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }*/

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public Balance getBalance() {
            return balance;
        }

        public void setBalance(Balance balance) {
            this.balance = balance;
        }

        /*public String getIncludeInNetWorth() {
            return includeInNetWorth;
        }

        public void setIncludeInNetWorth(String includeInNetWorth) {
            this.includeInNetWorth = includeInNetWorth;
        }*/

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

    }

    public static class Balance {

        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("currency")
        @Expose
        private String currency;

        /**
         * No args constructor for use in serialization
         */
        public Balance() {
        }

        /**
         * @param amount
         * @param currency
         */
        public Balance(String amount, String currency) {
            super();
            this.amount = amount;
            this.currency = currency;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

    }

}