package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeekData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<Week> weeks = null;

    /**
     * No args constructor for use in serialization
     */
    public WeekData() {
    }

    /**
     * @param status
     * @param weeks
     */
    public WeekData(String status, ArrayList<Week> weeks) {
        super();
        this.status = status;
        this.weeks = weeks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(ArrayList<Week> weeks) {
        this.weeks = weeks;
    }

}