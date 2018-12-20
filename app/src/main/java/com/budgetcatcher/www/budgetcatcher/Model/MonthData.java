package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MonthData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<Month> months = null;

    /**
     * No args constructor for use in serialization
     */
    public MonthData() {
    }

    /**
     * @param status
     * @param months
     */
    public MonthData(String status, ArrayList<Month> months) {
        super();
        this.status = status;
        this.months = months;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Month> getMonths() {
        return months;
    }

    public void setMonths(ArrayList<Month> months) {
        this.months = months;
    }

}