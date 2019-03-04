package com.example.nguyenngoclinh.shopi.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.model.Bill;

import java.util.List;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.ViewHolder> {
    private Context context;
    private List<Bill> listBill;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(view);
    }

    public AdapterBill(Context context, List<Bill> listBill){
        this.context = context;
        this.listBill = listBill;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Bill bill = listBill.get(position);

        holder.textViewNameProductBill.setText(bill.getNameProductBill());
        holder.textViewSumPriceProductBill.setText(bill.getSumPriceBill());
        holder.imageViewBill.setImageResource(bill.getResourceImageBill());
        holder.textViewQuantityProductBill.setText(bill.getQuantityProductBill());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickedListener != null){
                    itemClickedListener.ClickedListener(holder.itemView, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listBill.size();
    }


    public interface OnItemClickedListener{
        void ClickedListener(View view, int possion);
    }
    private OnItemClickedListener itemClickedListener;
    public void setOnItemClickedListener(OnItemClickedListener itemClickedListener){
        this.itemClickedListener = itemClickedListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNameProductBill;
        private TextView textViewQuantityProductBill;
        private LinearLayout linearLayout;
        private TextView textViewSumPriceProductBill;
        private ImageView imageViewBill;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewQuantityProductBill = itemView.findViewById(R.id.textViewQuantityProductBill);
            textViewNameProductBill = itemView.findViewById(R.id.textViewNameProductBill);
            textViewSumPriceProductBill = itemView.findViewById(R.id.textViewSumPriceBill);
            linearLayout = itemView.findViewById(R.id.linearLayoutBill);
            imageViewBill = itemView.findViewById(R.id.imageViewBill);
        }
    }
}
