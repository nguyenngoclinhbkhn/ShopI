package com.example.nguyenngoclinh.shopi.uis.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.adapter.AdapterProduct;
import com.example.nguyenngoclinh.shopi.model.Product;
import com.example.nguyenngoclinh.shopi.uis.activities.ManagerProductActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentManagerSelledProduct extends Fragment {
    private RecyclerView recyclerViewSelledProduct;
    private AdapterProduct adapterProduct;
    private List<Product> listProduct;
    private int level = 0;
    private DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_selled_product, container, false);
        recyclerViewSelledProduct = view.findViewById(R.id.recyclerViewManagerSellProduct);
        listProduct = new ArrayList<>();
//        if(ManagerProductActivity.KIND.equals("Quần áo")){
//            level = 1;
//        }else if(ManagerProductActivity.KIND.equals("Dụng cụ")){
//            level = 2;
//        }else {
//            level = 3;
//        }
        listProduct = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("productSells");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProduct.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    listProduct.add(product);
                }
                adapterProduct = new AdapterProduct(getActivity(), listProduct);
                adapterProduct.notifyDataSetChanged();
                recyclerViewSelledProduct.setAdapter(adapterProduct);

                RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                recyclerViewSelledProduct.hasFixedSize();
                recyclerViewSelledProduct.setLayoutManager(manager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
}
