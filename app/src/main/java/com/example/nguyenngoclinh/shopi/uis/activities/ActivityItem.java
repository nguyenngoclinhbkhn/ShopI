package com.example.nguyenngoclinh.shopi.uis.activities;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.adapter.AdapterProduct;
import com.example.nguyenngoclinh.shopi.model.Product;
import com.example.nguyenngoclinh.shopi.uis.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityItem extends BaseActivity {
    private Toolbar toolbarItem;
    private EditText editTextSearchItem;
    private ImageButton imageButtonBackItem;
    private RecyclerView recyclerView;
    private AdapterProduct adapterProduct;
    private List<Product> listProduct;
    private String kind = "";
    private DatabaseReference reference;

    @Override
    public int injectLayout() {
        return R.layout.activity_item_product;
    }

    @Override
    public void initView() {
        toolbarItem = findViewById(R.id.toolbarItem);
        editTextSearchItem = findViewById(R.id.editTextSearchItem);
        imageButtonBackItem = findViewById(R.id.imageButtonBackItem);
        recyclerView = findViewById(R.id.recycleItem);

    }

    @Override
    public void initVariable() {
        kind = ActivityMainUser.KINDClOTHING;
        reference = FirebaseDatabase.getInstance().getReference("products");
        listProduct = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    if(product.equals(kind) != false){
                        listProduct.add(product);
                    }
                }
                adapterProduct = new AdapterProduct(ActivityItem.this, listProduct);
                recyclerView.setAdapter(adapterProduct);
                adapterProduct.notifyDataSetChanged();

                RecyclerView.LayoutManager manager = new GridLayoutManager(ActivityItem.this, 2);
                recyclerView.setLayoutManager(manager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
