package com.rootsoftit.pensiontracker.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.github.florent37.inlineactivityresult.InlineActivityResult;
import com.github.florent37.inlineactivityresult.Result;
import com.github.florent37.inlineactivityresult.callbacks.ActivityResultListener;
import com.github.florent37.runtimepermission.PermissionResult;
import com.github.florent37.runtimepermission.RuntimePermission;
import com.github.florent37.runtimepermission.callbacks.PermissionListener;
import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.crud.AddEditPensionActivity;
import com.rootsoftit.pensiontracker.crud.AllPensionActivity;
import com.rootsoftit.pensiontracker.crud.EditMyDetailsActivity;
import com.rootsoftit.pensiontracker.data.Api;
import com.rootsoftit.pensiontracker.data.PensionClient;
import com.rootsoftit.pensiontracker.data.ResponsePacket;
import com.rootsoftit.pensiontracker.data.ServerResponse;
import com.rootsoftit.pensiontracker.data.Session;
import com.rootsoftit.pensiontracker.data.User;
import com.rootsoftit.pensiontracker.data.UserResponse;
import com.rootsoftit.pensiontracker.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private TextView btnAddPension;
    private TextView btnEditPension;
    private TextView btnDeletePension;
    private TextView btnAllPension;
    private TextView btnEditMyDetails;
    private TextView tvBalance;
    private ImageView btnUploadImage;
    private TextView btnLogout;
    private TextView tvName;
    private ImageView ivIcon;
    private TextView btnPrivacyPolicy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_me, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();


        btnAddPension = view.findViewById(R.id.btnAddPension);
        btnEditPension = view.findViewById(R.id.btnEditPension);
        btnDeletePension = view.findViewById(R.id.btnDeletePension);
        btnAllPension = view.findViewById(R.id.btnAllPension);
        btnEditMyDetails = view.findViewById(R.id.btnEditMyDetails);
        tvBalance = view.findViewById(R.id.tvBalance);
        btnUploadImage = view.findViewById(R.id.btnUploadImage);
        ivIcon = view.findViewById(R.id.ivIcon);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnPrivacyPolicy = view.findViewById(R.id.btnPrivacyPolicy);


        tvName = view.findViewById(R.id.tvName);

        PensionClient client = Api.getInstance().getPensionClient();

        client.getUserDetails().enqueue(new ServerResponse<UserResponse>(getContext()) {
            @Override
            public void OnComplete(UserResponse response) {

                Session.setUser(response.getUser());
                tvBalance.setText("£"+response.getAmount());
            }

            @Override
            public void OnError(Error error) {

            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RuntimePermission.askPermission(ProfileFragment.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).ask(new PermissionListener() {
                    @Override
                    public void onAccepted(PermissionResult permissionResult, List<String> accepted) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        InlineActivityResult.startForResult(ProfileFragment.this, intent, new ActivityResultListener() {
                            @Override
                            public void onSuccess(Result result) {
                                if (result.getResultCode() == RESULT_OK && result.getData() != null
                                        && result.getData().getData() != null) {
                                    final Uri filePath = result.getData().getData();

                                    Picasso.get().load(filePath).resize(92, 92).centerCrop().into(ivIcon);

                                    MultipartBody.Part body = null;
                                    File profileFile = new File(getPath(filePath));
                                    Timber.d("start " + profileFile);
                                    if (profileFile != null) {
                                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), profileFile);
                                        body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
                                        PensionClient pensionClient = Api.getInstance().getPensionClient();

                                        pensionClient.uploadImage(body).enqueue(new ServerResponse<ResponsePacket<String>>(getContext()) {
                                            @Override
                                            public void OnComplete(ResponsePacket<String> response) {


                                            }

                                            @Override
                                            public void OnError(Error error) {

                                            }
                                        });
                                    }


                                }
                            }


                            @Override
                            public void onFailed(Result result) {
                            }
                        });
                    }

                    @Override
                    public void onDenied(PermissionResult permissionResult, List<String> denied, List<String> foreverDenied) {

                    }
                });

            }
        });

        User user = Session.getUser();

        tvName.setText(user.getName());
        if (user.getImage() != null) {
            Picasso.get().load("https://pension.hostfxbd.com/uploads/" + user.getId() + "/" + user.getImage()).resize(92, 92).centerCrop().placeholder(R.drawable.ic_profile).into(ivIcon);
        }

        btnAddPension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditPensionActivity.class);
                intent.putExtra(AddEditPensionActivity.MODE, false);
                startActivity(intent);
            }
        });

        btnEditPension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllPensionActivity.class);
                intent.putExtra(AddEditPensionActivity.MODE, AllPensionActivity.EDIT);
                startActivity(intent);
            }
        });

        btnAllPension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllPensionActivity.class);
                intent.putExtra(AddEditPensionActivity.MODE, AllPensionActivity.INFO);
                startActivity(intent);
            }
        });

        btnDeletePension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllPensionActivity.class);
                intent.putExtra(AddEditPensionActivity.MODE, AllPensionActivity.DELETE);
                startActivity(intent);
            }
        });

        btnEditMyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditMyDetailsActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.logOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btnPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Privacy policy")
                        .setMessage(R.string.privacy_policy)
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            }
        });

    }


    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

}
