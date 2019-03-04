package com.example.nguyenngoclinh.shopi.uis.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.adapter.AdapterProduct;
import com.example.nguyenngoclinh.shopi.model.ManagerProduct;
import com.example.nguyenngoclinh.shopi.model.Product;
import com.example.nguyenngoclinh.shopi.model.User;
import com.example.nguyenngoclinh.shopi.uis.activities.ManagerProductActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


// 1 : Quan ao
// 2 : Dung cu
// 3 : Phu kien
public class FragmentManagerInventoryProduct extends Fragment {
    private RecyclerView recyclerViewInventoryProduct;
    private AdapterProduct adapterProduct;
    private List<Product> listProduct;
    private DatabaseReference reference;
    private String level = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_inventory_product, container, false);
        recyclerViewInventoryProduct = view.findViewById(R.id.recyclerViewManagerInventoryProduct);
        listProduct = new ArrayList<>();
        if(ManagerProductActivity.KIND.equals("Quần áo")){
            level = "1";
        }else if(ManagerProductActivity.KIND.equals("Dụng cụ")){
            level = "2";
        }else {
            level = "3";
        }
        reference = FirebaseDatabase.getInstance().getReference("products");
        if(reference != null){
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listProduct.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Product product = snapshot.getValue(Product.class);
                        if(product.getKindProduct().equals(level)) {
                            listProduct.add(product);
                        }
                    }
                    adapterProduct = new AdapterProduct(getActivity(), listProduct);
                    recyclerViewInventoryProduct.setAdapter(adapterProduct);
                    adapterProduct.notifyDataSetChanged();
                    RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                    recyclerViewInventoryProduct.hasFixedSize();
                    recyclerViewInventoryProduct.setLayoutManager(manager);
                    adapterProduct.setOnItemClickListener(new AdapterProduct.OnItemClickedListener() {
                        @Override
                        public void onItemClicked(View view, int position) {
                            Product p = listProduct.get(position);
                            Toast.makeText(getActivity(), "ID : " + p.getIdProduct(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
