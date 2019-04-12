package com.rootsoftit.pensiontracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Pension implements Parcelable {

    @SerializedName("amount")
    private String amount;
    @SerializedName("policy_number")
    private String policyNumber;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("company_name")
    private String companyName;
    @SerializedName("policy_name")
    private String policyName;
    @SerializedName("policy_start")
    private String policyStart;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("id")
    private int id;
    @SerializedName("company_of_employment")
    private String companyOfEmployment;
    @SerializedName("others")
    private String others;

    public Pension() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyStart() {
        return policyStart;
    }

    public void setPolicyStart(String policyStart) {
        this.policyStart = policyStart;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyOfEmployment() {
        return companyOfEmployment;
    }

    public void setCompanyOfEmployment(String companyOfEmployment) {
        this.companyOfEmployment = companyOfEmployment;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.amount);
        dest.writeString(this.policyNumber);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.userId);
        dest.writeString(this.companyName);
        dest.writeString(this.policyName);
        dest.writeString(this.policyStart);
        dest.writeString(this.createdAt);
        dest.writeInt(this.id);
        dest.writeString(this.companyOfEmployment);
        dest.writeString(this.others);
    }

    protected Pension(Parcel in) {
        this.amount = in.readString();
        this.policyNumber = in.readString();
        this.updatedAt = in.readString();
        this.userId = in.readInt();
        this.companyName = in.readString();
        this.policyName = in.readString();
        this.policyStart = in.readString();
        this.createdAt = in.readString();
        this.id = in.readInt();
        this.companyOfEmployment = in.readString();
        this.others = in.readString();
    }

    public static final Creator<Pension> CREATOR = new Creator<Pension>() {
        @Override
        public Pension createFromParcel(Parcel source) {
            return new Pension(source);
        }

        @Override
        public Pension[] newArray(int size) {
            return new Pension[size];
        }
    };
}