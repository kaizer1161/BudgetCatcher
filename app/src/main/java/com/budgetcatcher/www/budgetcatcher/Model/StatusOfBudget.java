package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusOfBudget {

    @SerializedName("budgetStatus")
    @Expose
    private Integer budgetStatus;

    /**
     * No args constructor for use in serialization
     */
    public StatusOfBudget() {
    }

    /**
     * @param budgetStatus
     */
    public StatusOfBudget(Integer budgetStatus) {
        super();
        this.budgetStatus = budgetStatus;
    }

    public Integer getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(Integer budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

}
