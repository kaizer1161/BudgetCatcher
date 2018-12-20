package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Month {

    @SerializedName("monthNumber")
    @Expose
    private String monthNumber;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("firstDayOfMonth")
    @Expose
    private String firstDayOfMonth;
    @SerializedName("lastDayOfMonth")
    @Expose
    private String lastDayOfMonth;

    /**
     * No args constructor for use in serialization
     */
    public Month() {
    }

    /**
     * @param lastDayOfMonth
     * @param month
     * @param firstDayOfMonth
     * @param year
     * @param monthNumber
     */
    public Month(String monthNumber, String month, String year, String firstDayOfMonth, String lastDayOfMonth) {
        super();
        this.monthNumber = monthNumber;
        this.month = month;
        this.year = year;
        this.firstDayOfMonth = firstDayOfMonth;
        this.lastDayOfMonth = lastDayOfMonth;
    }

    public String getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    public void setFirstDayOfMonth(String firstDayOfMonth) {
        this.firstDayOfMonth = firstDayOfMonth;
    }

    public String getLastDayOfMonth() {
        return lastDayOfMonth;
    }

    public void setLastDayOfMonth(String lastDayOfMonth) {
        this.lastDayOfMonth = lastDayOfMonth;
    }

}
