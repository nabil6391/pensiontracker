package com.rootsoftit.pensiontracker.crud;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.data.Api;
import com.rootsoftit.pensiontracker.data.Pension;
import com.rootsoftit.pensiontracker.data.PensionClient;
import com.rootsoftit.pensiontracker.data.ServerResponse;

import java.util.List;

public class PensionListFragment extends Fragment {

    PensionClient pensionClient;
    SwipeRefreshLayout swipeRefreshLayout;
    private int mode;
    PensionAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pension_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        final RecyclerView rvList = view.findViewById(R.id.rvList);

        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);


        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        pensionClient = Api.getInstance().getPensionClient();


        adapter = new PensionAdapter(new PensionAdapter.ActionListener() {
            @Override
            public void onEdit(Pension pension, int position) {
                Intent intent = new Intent(getActivity(), AddEditPensionActivity.class);
                intent.putExtra(AddEditPensionActivity.MODE, true);
                intent.putExtra(PensionDetailsActivity.PENSION, pension);
                startActivity(intent);
            }

            @Override
            public void onMoreInfo(Pension pension, int position) {
                Intent intent = new Intent(getActivity(), PensionDetailsActivity.class);
                intent.putExtra(PensionDetailsActivity.PENSION, pension);
                startActivity(intent);
            }

            @Override
            public void onDelete(final Pension pension, int position) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Would you like to delete this pension")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pensionClient.deletePension(pension.getId()).enqueue(new ServerResponse<Pension>(getContext()) {
                                    @Override
                                    public void OnComplete(Pension response) {

                                        loadData();
//                        adapter.updateData();
                                    }

                                    @Override
                                    public void OnError(Error error) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });

        adapter.setMode(mode);
        rvList.setAdapter(adapter);

        loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        pensionClient.getAllPension().enqueue(new ServerResponse<AllPensionResponse>(getContext()) {
            @Override
            public void OnComplete(AllPensionResponse response) {
                List<Pension> content = response.getPensions();

                adapter.updateData(content);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void OnError(Error error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void setMode(int isDelete) {

        this.mode = isDelete;
    }
}
