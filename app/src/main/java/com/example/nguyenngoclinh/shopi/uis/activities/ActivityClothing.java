package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class ActivityClothing extends BaseActivity {
    private ImageButton imageButtonSearch;
    private Toolbar toolbarClothingUser;
    private ImageButton imageButtonBackClothing;
    private RecyclerView recyclerView;
    private EditText editTextSearch;
    private List<Product> listProduct;
    private AdapterProduct adapterProduct;
    private DatabaseReference reference;
    private String nameSearch = "";
    private String kind = "";
    private String idUser = "";
    @Override
    public int injectLayout() {
        return R.layout.activity_clothing_user;
    }

    @Override
    public void initView() {
        imageButtonSearch = findViewById(R.id.imageButtonSearch);
        imageButtonBackClothing = findViewById(R.id.imageButtonBackClothing);
        editTextSearch = findViewById(R.id.editTextSearch);
        toolbarClothingUser = findViewById(R.id.toolBarClothing);
        recyclerView = findViewById(R.id.recycleClothing);

    }

    @Override
    public void initVariable() {
        idUser = ActivityMainUser.ID_USER;
        final Intent intent = getIntent();
        kind = intent.getStringExtra("KIND");
        setSupportActionBar(toolbarClothingUser);
        reference = FirebaseDatabase.getInstance().getReference("products");
        listProduct = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    if(product.getKindProduct().equals(kind)){
                        listProduct.add(product);
                    }
                }
                adapterProduct = new AdapterProduct(ActivityClothing.this, listProduct);
                recyclerView.setAdapter(adapterProduct);
                adapterProduct.notifyDataSetChanged();
                RecyclerView.LayoutManager manager = new GridLayoutManager(ActivityClothing.this, 2);
                recyclerView.hasFixedSize();
                recyclerView.setLayoutManager(manager);
                adapterProduct.setOnItemClickListener(new AdapterProduct.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        String id1 = listProduct.get(position).getIdProduct();
                        String name = listProduct.get(position).getNameProduct();
                        String price = listProduct.get(position).getPriceProduct();
                        String image = listProduct.get(position).getImageProduct();
                        Intent intent1 = new Intent(ActivityClothing.this, ActivityOrder.class);
                        intent1.putExtra("idItem", id1);
                        intent1.putExtra("nameItem", name);
                        intent1.putExtra("price",price);
                        intent1.putExtra("kind", kind);
                        intent1.putExtra("image", image);
                        startActivity(intent1);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ActivityClothing.this, "", Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonBackClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityClothing.this, ActivityMainUser.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });


    }
}
