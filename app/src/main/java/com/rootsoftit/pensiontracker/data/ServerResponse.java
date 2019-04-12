package com.rootsoftit.pensiontracker.data;

import android.content.Context;
import android.content.Intent;

import com.rootsoftit.pensiontracker.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ServerResponse<T> implements Callback<T> {

    private final Context context;

    public ServerResponse(Context context) {

        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful() && response.body() != null) {
            OnComplete(response.body());
        } else {
            if (response.code() == 401) { //unauthorised
                Session.logOut();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                OnError(new Error("ServerError"));
            }

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
//        if (!DeviceInfo.IsInternetConnected(context))
//            OnError(Error("ClientError","No Internet connection!"))
//        else
        OnError(new Error("ClientError"));
    }


    public abstract void OnComplete(T response);

    public abstract void OnError(Error error);
}

