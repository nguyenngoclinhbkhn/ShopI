package com.example.nguyenngoclinh.shopi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.model.Product;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder>{
    private Context context;
    private List<Product> listProduct;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_product_selled_and_inventory, parent, false);
        return new ViewHolder(view);
    }
    public AdapterProduct(Context context, List<Product> listProduct){
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Product product = listProduct.get(position);
        holder.textViewNameProduct.setText(product.getNameProduct());
        holder.textViewPriceProduct.setText(product.getPriceProduct());
        byte[] manghinh = Base64.decode(product.getImageProduct().toString(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
        holder.imageViewProduct.setImageBitmap(bmp);
//        Glide.with(context).load(product.getImageProduct()).into(holder.imageViewProduct);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickedListener != null){
                    itemClickedListener.onItemClicked(holder.itemView, position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewProduct;
        private TextView textViewNameProduct;
        private TextView textViewPriceProduct;
        private LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProductSellAndInventory);
            textViewNameProduct = itemView.findViewById(R.id.textViewNameProductSellAndInventory);
            textViewPriceProduct = itemView.findViewById(R.id.textViewPriceProductSellAndInventory);
            linearLayout = itemView.findViewById(R.id.linearLayoutProduct);
        }
    }
    public interface OnItemClickedListener{
        void onItemClicked(View view, int position);
    }
    private OnItemClickedListener itemClickedListener;
    public void setOnItemClickListener(OnItemClickedListener listener){
        this.itemClickedListener = listener;
    }
}
