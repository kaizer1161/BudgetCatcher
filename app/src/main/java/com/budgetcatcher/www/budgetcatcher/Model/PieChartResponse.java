package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PieChartResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<PieChartData> pieChartDataList = null;

    /**
     * No args constructor for use in serialization
     */
    public PieChartResponse() {
    }

    /**
     * @param status
     * @param data
     */
    public PieChartResponse(String status, List<PieChartData> data) {
        super();
        this.status = status;
        this.pieChartDataList = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PieChartData> getData() {
        return pieChartDataList;
    }

    public void setData(List<PieChartData> data) {
        this.pieChartDataList = data;
    }

}