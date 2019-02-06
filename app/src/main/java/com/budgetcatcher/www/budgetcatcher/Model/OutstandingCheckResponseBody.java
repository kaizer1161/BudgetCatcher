package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OutstandingCheckResponseBody {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<OutstandingChecks> outstandingChecksList = null;
    @SerializedName("total")
    @Expose
    private String total;

    /**
     * No args constructor for use in serialization
     */
    public OutstandingCheckResponseBody() {
    }

    /**
     * @param total
     * @param status
     * @param outstandingChecksList
     */
    public OutstandingCheckResponseBody(String status, ArrayList<OutstandingChecks> outstandingChecksList, String total) {
        super();
        this.status = status;
        this.outstandingChecksList = outstandingChecksList;
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<OutstandingChecks> getOutstandingChecks() {
        return outstandingChecksList;
    }

    public void setOutstandingChecks(ArrayList<OutstandingChecks> outstandingChecksList) {
        this.outstandingChecksList = outstandingChecksList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}