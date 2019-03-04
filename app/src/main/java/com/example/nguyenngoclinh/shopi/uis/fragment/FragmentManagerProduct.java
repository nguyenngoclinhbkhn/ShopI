package com.example.nguyenngoclinh.shopi.uis.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.adapter.AdapterMangerProduct;
import com.example.nguyenngoclinh.shopi.model.ManagerProduct;
import com.example.nguyenngoclinh.shopi.uis.activities.ManagerProductActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentManagerProduct extends Fragment {
    private RecyclerView recyclerViewManagerProduct;
    private AdapterMangerProduct adapterMangerProduct;
    private List<ManagerProduct> listManagerProduct;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_product, container, false);
        recyclerViewManagerProduct = view.findViewById(R.id.recyclerViewManagerProduct);

        listManagerProduct = new ArrayList<>();
        listManagerProduct.add(new ManagerProduct("Quần áo", "https://www.sporter.vn/wp-content/uploads/2017/06/Ao-liverpool-san-nha.jpg"));
        listManagerProduct.add(new ManagerProduct("Dụng cụ", "https://toplist.vn/images/800px/shop-toi-yeu-the-thao-103682.jpg"));
        listManagerProduct.add(new ManagerProduct("Phụ kiện", "https://vuainnhanh.com/wp-content/uploads/2018/04/ph%E1%BB%A5-kien-the-thao-1.jpg"));
        adapterMangerProduct = new AdapterMangerProduct(listManagerProduct, getActivity());
        recyclerViewManagerProduct.setAdapter(adapterMangerProduct);
        adapterMangerProduct.notifyDataSetChanged();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerViewManagerProduct.setLayoutManager(manager);

        adapterMangerProduct.setOnItemClickListener(new AdapterMangerProduct.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ManagerProduct product = listManagerProduct.get(position);
                Intent intent = new Intent(getActivity(), ManagerProductActivity.class);
                intent.putExtra("kind",product.getName());
                startActivity(intent);
            }
        });
        return view;
    }
}
