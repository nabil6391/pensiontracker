package com.rootsoftit.pensiontracker.data;

 import com.google.gson.annotations.SerializedName;

public class UserResponse{

	@SerializedName("pension")
	private User pension;

	@SerializedName("status")
	private boolean status;


	@SerializedName("amount")
	private String amount;

	public User getUser(){
		return pension;
	}

	public boolean isStatus(){
		return status;
	}

	public String getAmount() {
		return amount;
	}
}