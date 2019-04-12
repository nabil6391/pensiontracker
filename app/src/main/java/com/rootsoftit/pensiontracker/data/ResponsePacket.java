package com.rootsoftit.pensiontracker.data;

import com.google.gson.annotations.SerializedName;

public class ResponsePacket<T> {

    @SerializedName("pension")
    T content;
    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public T getContent() {
        return content;
    }
}
