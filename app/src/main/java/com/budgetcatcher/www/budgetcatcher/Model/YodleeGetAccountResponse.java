package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YodleeGetAccountResponse {

    @SerializedName("account")
    @Expose
    private List<Account> account = null;

    /**
     * No args constructor for use in serialization
     */
    public YodleeGetAccountResponse() {
    }

    /**
     * @param account
     */
    public YodleeGetAccountResponse(List<Account> account) {
        super();
        this.account = account;
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    public class Account {

        @SerializedName("includeInNetWorth")
        @Expose
        private Boolean includeInNetWorth;
        @SerializedName("accountName")
        @Expose
        private String accountName;
        @SerializedName("accountType")
        @Expose
        private String accountType;
        @SerializedName("isManual")
        @Expose
        private Boolean isManual;
        @SerializedName("memo")
        @Expose
        private String memo;
        @SerializedName("accountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("accountStatus")
        @Expose
        private String accountStatus;
        @SerializedName("lastUpdated")
        @Expose
        private String lastUpdated;
        @SerializedName("isAsset")
        @Expose
        private Boolean isAsset;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("balance")
        @Expose
        private Balance balance;
        @SerializedName("aggregationSource")
        @Expose
        private String aggregationSource;
        @SerializedName("providerId")
        @Expose
        private String providerId;
        @SerializedName("providerAccountId")
        @Expose
        private Integer providerAccountId;
        @SerializedName("CONTAINER")
        @Expose
        private String cONTAINER;
        @SerializedName("nickname")
        @Expose
        private String nickname;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("userClassification")
        @Expose
        private String userClassification;
        @SerializedName("providerName")
        @Expose
        private String providerName;
        @SerializedName("availableCash")
        @Expose
        private AvailableCash availableCash;
        @SerializedName("availableCredit")
        @Expose
        private AvailableCredit availableCredit;
        @SerializedName("totalCashLimit")
        @Expose
        private TotalCashLimit totalCashLimit;
        @SerializedName("totalCreditLine")
        @Expose
        private TotalCreditLine totalCreditLine;
        @SerializedName("currentBalance")
        @Expose
        private CurrentBalance currentBalance;
        @SerializedName("displayedName")
        @Expose
        private String displayedName;
        @SerializedName("availableBalance")
        @Expose
        private AvailableBalance availableBalance;

        /**
         * No args constructor for use in serialization
         */
        public Account() {
        }

        /**
         * @param availableCash
         * @param memo
         * @param nickname
         * @param isManual
         * @param isAsset
         * @param lastUpdated
         * @param currentBalance
         * @param availableBalance
         * @param displayedName
         * @param totalCashLimit
         * @param id
         * @param userClassification
         * @param balance
         * @param accountName
         * @param accountNumber
         * @param aggregationSource
         * @param providerName
         * @param cONTAINER
         * @param accountStatus
         * @param accountType
         * @param totalCreditLine
         * @param providerId
         * @param includeInNetWorth
         * @param providerAccountId
         * @param createdDate
         * @param availableCredit
         */
        public Account(Boolean includeInNetWorth, String accountName, String accountType, Boolean isManual, String memo, String accountNumber, String accountStatus, String lastUpdated, Boolean isAsset, String createdDate, Balance balance, String aggregationSource, String providerId, Integer providerAccountId, String cONTAINER, String nickname, Integer id, String userClassification, String providerName, AvailableCash availableCash, AvailableCredit availableCredit, TotalCashLimit totalCashLimit, TotalCreditLine totalCreditLine, CurrentBalance currentBalance, String displayedName, AvailableBalance availableBalance) {
            super();
            this.includeInNetWorth = includeInNetWorth;
            this.accountName = accountName;
            this.accountType = accountType;
            this.isManual = isManual;
            this.memo = memo;
            this.accountNumber = accountNumber;
            this.accountStatus = accountStatus;
            this.lastUpdated = lastUpdated;
            this.isAsset = isAsset;
            this.createdDate = createdDate;
            this.balance = balance;
            this.aggregationSource = aggregationSource;
            this.providerId = providerId;
            this.providerAccountId = providerAccountId;
            this.cONTAINER = cONTAINER;
            this.nickname = nickname;
            this.id = id;
            this.userClassification = userClassification;
            this.providerName = providerName;
            this.availableCash = availableCash;
            this.availableCredit = availableCredit;
            this.totalCashLimit = totalCashLimit;
            this.totalCreditLine = totalCreditLine;
            this.currentBalance = currentBalance;
            this.displayedName = displayedName;
            this.availableBalance = availableBalance;
        }

        public Boolean getIncludeInNetWorth() {
            return includeInNetWorth;
        }

        public void setIncludeInNetWorth(Boolean includeInNetWorth) {
            this.includeInNetWorth = includeInNetWorth;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public Boolean getIsManual() {
            return isManual;
        }

        public void setIsManual(Boolean isManual) {
            this.isManual = isManual;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountStatus() {
            return accountStatus;
        }

        public void setAccountStatus(String accountStatus) {
            this.accountStatus = accountStatus;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public Boolean getIsAsset() {
            return isAsset;
        }

        public void setIsAsset(Boolean isAsset) {
            this.isAsset = isAsset;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Balance getBalance() {
            return balance;
        }

        public void setBalance(Balance balance) {
            this.balance = balance;
        }

        public String getAggregationSource() {
            return aggregationSource;
        }

        public void setAggregationSource(String aggregationSource) {
            this.aggregationSource = aggregationSource;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public Integer getProviderAccountId() {
            return providerAccountId;
        }

        public void setProviderAccountId(Integer providerAccountId) {
            this.providerAccountId = providerAccountId;
        }

        public String getCONTAINER() {
            return cONTAINER;
        }

        public void setCONTAINER(String cONTAINER) {
            this.cONTAINER = cONTAINER;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserClassification() {
            return userClassification;
        }

        public void setUserClassification(String userClassification) {
            this.userClassification = userClassification;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public AvailableCash getAvailableCash() {
            return availableCash;
        }

        public void setAvailableCash(AvailableCash availableCash) {
            this.availableCash = availableCash;
        }

        public AvailableCredit getAvailableCredit() {
            return availableCredit;
        }

        public void setAvailableCredit(AvailableCredit availableCredit) {
            this.availableCredit = availableCredit;
        }

        public TotalCashLimit getTotalCashLimit() {
            return totalCashLimit;
        }

        public void setTotalCashLimit(TotalCashLimit totalCashLimit) {
            this.totalCashLimit = totalCashLimit;
        }

        public TotalCreditLine getTotalCreditLine() {
            return totalCreditLine;
        }

        public void setTotalCreditLine(TotalCreditLine totalCreditLine) {
            this.totalCreditLine = totalCreditLine;
        }

        public CurrentBalance getCurrentBalance() {
            return currentBalance;
        }

        public void setCurrentBalance(CurrentBalance currentBalance) {
            this.currentBalance = currentBalance;
        }

        public String getDisplayedName() {
            return displayedName;
        }

        public void setDisplayedName(String displayedName) {
            this.displayedName = displayedName;
        }

        public AvailableBalance getAvailableBalance() {
            return availableBalance;
        }

        public void setAvailableBalance(AvailableBalance availableBalance) {
            this.availableBalance = availableBalance;
        }

    }

    public class AvailableBalance {

        @SerializedName("amount")
        @Expose
        private Float amount;
        @SerializedName("currency")
        @Expose
        private String currency;

        /**
         * No args constructor for use in serialization
         */
        public AvailableBalance() {
        }

        /**
         * @param amount
         * @param currency
         */
        public AvailableBalance(Float amount, String currency) {
            super();
            this.amount = amount;
            this.currency = currency;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

    }

    public class AvailableCash {

        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("currency")
        @Expose
        private String currency;

        /**
         * No args constructor for use in serialization
         */
        public AvailableCash() {
        }

        /**
         * @param amount
         * @param currency
         */
        public AvailableCash(Integer amount, String currency) {
            super();
            this.amount = amount;
            this.currency = currency;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

    }

    public class AvailableCredit {

        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("currency")
        @Expose
        private String currency;

        /**
         * No args constructor for use in serialization
         */
        public AvailableCredit() {
        }

        /**
         * @param amount
         * @param currency
         */
        public AvailableCredit(Integer amount, String currency) {
            super();
            this.amount = amount;
            this.currency = currency;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

    }

    public class Balance {

        @SerializedName("amount")
        @Expose
        private Float amount;
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
        public Balance(Float amount, String currency) {
            super();
            this.amount = amount;
            this.currency = currency;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

    }

    public class CurrentBalance {

        @SerializedName("amount")
        @Expose
        private Float amount;
        @SerializedName("currency")
        @Expose
        private String currency;

        /**
         * No args constructor for use in serialization
         */
        public CurrentBalance() {
        }

        /**
         * @param amount
         * @param currency
         */
        public CurrentBalance(Float amount, String currency) {
            super();
            this.amount = amount;
            this.currency = currency;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

    }

    public class TotalCashLimit {

        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("currency")
        @Expose
        private String currency;

        /**
         * No args constructor for use in serialization
         */
        public TotalCashLimit() {
        }

        /**
         * @param amount
         * @param currency
         */
        public TotalCashLimit(Integer amount, String currency) {
            super();
            this.amount = amount;
            this.currency = currency;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

    }

    public class TotalCreditLine {

        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("currency")
        @Expose
        private String currency;

        /**
         * No args constructor for use in serialization
         */
        public TotalCreditLine() {
        }

        /**
         * @param amount
         * @param currency
         */
        public TotalCreditLine(Integer amount, String currency) {
            super();
            this.amount = amount;
            this.currency = currency;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
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