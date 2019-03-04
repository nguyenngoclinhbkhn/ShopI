package com.example.nguyenngoclinh.shopi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.model.ManagerProduct;

import java.util.List;

public class AdapterMangerProduct extends RecyclerView.Adapter<AdapterMangerProduct.ViewHolder>{
    private List<ManagerProduct> listManagerProduct;
    private Context context;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_manager_product, parent, false);
        return new ViewHolder(view);
    }

    public AdapterMangerProduct(List<ManagerProduct> list, Context context){
        this.listManagerProduct = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ManagerProduct managerProduct = listManagerProduct.get(position);

        holder.textViewManagerProduct.setText(managerProduct.getName());
        Glide.with(context).load(managerProduct.getImage()).into(holder.imageViewManagerProduct);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(holder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listManagerProduct.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewManagerProduct;
        private TextView textViewManagerProduct;
        private LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewManagerProduct = itemView.findViewById(R.id.imageViewManagerProduct);
            textViewManagerProduct = itemView.findViewById(R.id.textViewManagerProduct);
            linearLayout = itemView.findViewById(R.id.itemLinearLayoutManagerProduct);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }
}
