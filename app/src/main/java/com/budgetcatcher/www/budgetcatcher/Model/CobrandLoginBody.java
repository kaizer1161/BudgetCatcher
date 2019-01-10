package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CobrandLoginBody {

    @SerializedName("cobrand")
    @Expose
    private Cobrand cobrand;

    /**
     * No args constructor for use in serialization
     */
    public CobrandLoginBody() {
    }

    /**
     * @param cobrand
     */
    public CobrandLoginBody(Cobrand cobrand) {
        super();
        this.cobrand = cobrand;
    }

    public Cobrand getCobrand() {
        return cobrand;
    }

    public void setCobrand(Cobrand cobrand) {
        this.cobrand = cobrand;
    }

}