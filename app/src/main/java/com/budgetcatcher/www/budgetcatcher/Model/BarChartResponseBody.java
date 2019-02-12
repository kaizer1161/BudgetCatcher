package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarChartResponseBody {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<BarChartData> barDataList = null;

    /**
     * No args constructor for use in serialization
     */
    public BarChartResponseBody() {
    }

    /**
     * @param status
     * @param barDataList
     */
    public BarChartResponseBody(String status, List<BarChartData> barDataList) {
        super();
        this.status = status;
        this.barDataList = barDataList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BarChartData> getBarDataList() {
        return barDataList;
    }

    public void setBarDataList(List<BarChartData> barDataList) {
        this.barDataList = barDataList;
    }

}