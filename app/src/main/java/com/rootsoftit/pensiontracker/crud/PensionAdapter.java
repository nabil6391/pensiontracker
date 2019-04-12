package com.rootsoftit.pensiontracker.crud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.data.Pension;

import java.util.ArrayList;
import java.util.List;

import static com.rootsoftit.pensiontracker.crud.AllPensionActivity.DELETE;
import static com.rootsoftit.pensiontracker.crud.AllPensionActivity.EDIT;
import static com.rootsoftit.pensiontracker.crud.AllPensionActivity.INFO;

public class PensionAdapter extends RecyclerView.Adapter<PensionAdapter.BannerItemHolder> {

    private final ActionListener actionListener;

    private List<Pension> pensions;
    private int mode;

    public PensionAdapter(ActionListener actionListener) {
        this.actionListener = actionListener;
        pensions = new ArrayList<>();
    }


    @NonNull
    @Override
    public BannerItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BannerItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pension, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final BannerItemHolder holder, int position) {
        Pension pension = pensions.get(position);
        holder.tvPolicyNumber.setText("Policy number: " + pension.getPolicyNumber());

        holder.tvCompany.setText("Company: " + pension.getCompanyName());
        holder.tvCompanyofEmployment.setText("Company of Employment: " + pension.getCompanyOfEmployment());

        switch (mode) {
            case INFO:
                holder.btnMoreInfo.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.GONE);
                break;
            case EDIT:
                holder.btnDelete.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnMoreInfo.setVisibility(View.GONE);
                break;
            case DELETE:
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnMoreInfo.setVisibility(View.GONE);
                break;

        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onDelete(pensions.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });
        holder.btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onMoreInfo(pensions.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onEdit(pensions.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return pensions.size();
    }


    public List<Pension> getPensions() {
        return pensions;
    }

    public void updateData(List<Pension> banners) {
        this.pensions = banners;
        notifyDataSetChanged();
    }

    public void setMode(int isDelete) {

        this.mode = isDelete;
    }


    public interface ActionListener {

        void onEdit(Pension Pension, int position);

        void onMoreInfo(Pension Pension, int position);

        void onDelete(Pension Pension, int position);
    }

    public class BannerItemHolder extends RecyclerView.ViewHolder {

        private TextView tvPolicyNumber;
        private TextView tvCompany;
        private TextView tvCompanyofEmployment;
        private MaterialButton btnMoreInfo;
        private MaterialButton btnDelete;
        private MaterialButton btnEdit;

        public BannerItemHolder(@NonNull View itemView) {
            super(itemView);


            tvPolicyNumber = (TextView) itemView.findViewById(R.id.tvPolicyNumber);
            tvCompany = (TextView) itemView.findViewById(R.id.tvCompany);
            tvCompanyofEmployment = (TextView) itemView.findViewById(R.id.tvCompanyofEmployment);
            btnMoreInfo = (MaterialButton) itemView.findViewById(R.id.btnMoreInfo);
            btnDelete = itemView.findViewById(R.id.btnDeleteInfo);
            btnEdit = itemView.findViewById(R.id.btnEdit);


        }
    }

}

