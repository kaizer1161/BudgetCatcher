package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Week {

    @SerializedName("weekNumber")
    @Expose
    private String weekNumber;
    @SerializedName("week")
    @Expose
    private String week;
    @SerializedName("firstDayOfEveryWeek")
    @Expose
    private String firstDayOfEveryWeek;
    @SerializedName("lastDayOfEveryWeek")
    @Expose
    private String lastDayOfEveryWeek;

    /**
     * No args constructor for use in serialization
     */
    public Week() {
    }

    /**
     * @param weekNumber
     * @param firstDayOfEveryWeek
     * @param lastDayOfEveryWeek
     * @param week
     */
    public Week(String weekNumber, String week, String firstDayOfEveryWeek, String lastDayOfEveryWeek) {
        super();
        this.weekNumber = weekNumber;
        this.week = week;
        this.firstDayOfEveryWeek = firstDayOfEveryWeek;
        this.lastDayOfEveryWeek = lastDayOfEveryWeek;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(String weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getFirstDayOfEveryWeek() {
        return firstDayOfEveryWeek;
    }

    public void setFirstDayOfEveryWeek(String firstDayOfEveryWeek) {
        this.firstDayOfEveryWeek = firstDayOfEveryWeek;
    }

    public String getLastDayOfEveryWeek() {
        return lastDayOfEveryWeek;
    }

    public void setLastDayOfEveryWeek(String lastDayOfEveryWeek) {
        this.lastDayOfEveryWeek = lastDayOfEveryWeek;
    }

}
