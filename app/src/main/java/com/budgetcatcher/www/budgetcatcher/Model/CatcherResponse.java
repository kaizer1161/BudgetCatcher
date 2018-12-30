package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CatcherResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("incidentalsData")
    @Expose
    private ArrayList<Expenses> incidentalsData = null;
    @SerializedName("allowancesData")
    @Expose
    private ArrayList<Allowance> allowancesData = null;
    @SerializedName("billsData")
    @Expose
    private ArrayList<Bill> billsData = null;
    @SerializedName("incomesData")
    @Expose
    private ArrayList<Income> incomesData = null;
    @SerializedName("expenseData")
    @Expose
    private ExpenseData expenseData;

    /**
     * No args constructor for use in serialization
     */
    public CatcherResponse() {
    }

    /**
     * @param incidentalsData
     * @param incomesData
     * @param status
     * @param billsData
     * @param allowancesData
     * @param expenseData
     */
    public CatcherResponse(String status, ArrayList<Expenses> incidentalsData, ArrayList<Allowance> allowancesData, ArrayList<Bill> billsData, ArrayList<Income> incomesData, ExpenseData expenseData) {
        super();
        this.status = status;
        this.incidentalsData = incidentalsData;
        this.allowancesData = allowancesData;
        this.billsData = billsData;
        this.incomesData = incomesData;
        this.expenseData = expenseData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Expenses> getIncidentalsData() {
        return incidentalsData;
    }

    public void setIncidentalsData(ArrayList<Expenses> incidentalsData) {
        this.incidentalsData = incidentalsData;
    }

    public ArrayList<Allowance> getAllowancesData() {
        return allowancesData;
    }

    public void setAllowancesData(ArrayList<Allowance> allowancesData) {
        this.allowancesData = allowancesData;
    }

    public ArrayList<Bill> getBillsData() {
        return billsData;
    }

    public void setBillsData(ArrayList<Bill> billsData) {
        this.billsData = billsData;
    }

    public ArrayList<Income> getIncomesData() {
        return incomesData;
    }

    public void setIncomesData(ArrayList<Income> incomesData) {
        this.incomesData = incomesData;
    }

    public ExpenseData getExpenseData() {
        return expenseData;
    }

    public void setExpenseData(ExpenseData expenseData) {
        this.expenseData = expenseData;
    }

}


