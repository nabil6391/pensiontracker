package com.rootsoftit.pensiontracker.crud;

import com.google.gson.annotations.SerializedName;
import com.rootsoftit.pensiontracker.data.Pension;

import java.util.List;

public class AllPensionResponse {

    @SerializedName("pensions")
    private List<Pension> pensions;

    @SerializedName("status")
    private boolean status;

    public List<Pension> getPensions() {
        return pensions;
    }

    public boolean isStatus() {
        return status;
    }
}