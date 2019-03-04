package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.adapter.AdapterProduct;
import com.example.nguyenngoclinh.shopi.model.Product;
import com.example.nguyenngoclinh.shopi.model.User;
import com.example.nguyenngoclinh.shopi.uis.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityMainUser extends BaseActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private RecyclerView recyclerViewMainScreen;
    //    private List<MainScreen> mainScreenList;
//    private AdapterMainScreen adapterMainScreen;
    public static String ID_USER;
    private ImageView imageViewUser;
    private TextView textViewUserName;
    private DatabaseReference reference;
    private AdapterProduct adapterProduct;
    private List<Product> listProduct;
    private DatabaseReference reference1;
    public static String KINDClOTHING = "1";
    @Override
    public int injectLayout() {
        return R.layout.activity_main_user;
    }

    @Override
    public void initView() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationview);
        recyclerViewMainScreen = findViewById(R.id.recycleMainScreen);
        imageViewUser = navigationView.getHeaderView(0).findViewById(R.id.imageViewAdmin);
        textViewUserName = navigationView.getHeaderView(0).findViewById(R.id.textViewNameAdmin);
//        mainScreenList = new ArrayList<>();
//        adapterMainScreen = new AdapterMainScreen(mainScreenList, this);
//        recyclerViewMainScreen.setAdapter(adapterMainScreen);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
//        recyclerViewMainScreen.setLayoutManager(manager);
//
//        adapterMainScreen.setOnItemClickedListener(new AdapterMainScreen.OnItemClickedListener() {
//            @Override
//            public void onItemClick(String view) {
//                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                intent.putExtra("image", "http://thethaominhphu.com/wp-content/uploads/2017/04/ao-bong-da-man-city-xanh-2018.jpg");
//                startActivity(intent);
//            }
//        });


    }

    @Override
    public void initVariable() {
        reference1 = FirebaseDatabase.getInstance().getReference("products");
        listProduct = new ArrayList<>();
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    listProduct.add(product);
                }
                adapterProduct = new AdapterProduct(ActivityMainUser.this, listProduct);
                recyclerViewMainScreen.setAdapter(adapterProduct);
                adapterProduct.notifyDataSetChanged();

                RecyclerView.LayoutManager manager = new GridLayoutManager(ActivityMainUser.this, 2);
                recyclerViewMainScreen.setLayoutManager(manager);

//                adapterProduct.setOnItemClickListener(new AdapterProduct.OnItemClickedListener() {
//                    @Override
//                    public void onItemClicked(View view, int position) {
//                        String id1 = listProduct.get(position).getIdProduct();
//                        String name = listProduct.get(position).getNameProduct();
//                        String price = listProduct.get(position).getPriceProduct();
//                        Intent intent1 = new Intent(ActivityMainUser.this, ActivityOrder.class);
//                        intent1.putExtra("idItem", id1);
//                        intent1.putExtra("nameItem", name);
//                        intent1.putExtra("price",price);
//                        startActivity(intent1);
//                    }
//                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ActivityMainUser.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("id");
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(ID_USER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                textViewUserName.setText(user.getUserName());
                if(user.getImage() != null) {
                    byte[] manghinh = Base64.decode(user.getImage().toString(), Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                    imageViewUser.setImageBitmap(bmp);
                } else{
                    imageViewUser.setImageBitmap(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ActivityMainUser.this, "Khong lay duoc du lieu", Toast.LENGTH_SHORT).show();
            }
        });
        setSupportActionBar(toolbar);

        final Intent intent1 = new Intent(this, ActivityClothing.class);
        final Intent intent2 = new Intent(this, ActivityItem.class);
        final Intent intent4 = new Intent(this, ActivityDetailAccountUser.class);
        toolbar.setNavigationIcon(R.drawable.ic_action_list);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemClothing:
                        intent1.putExtra("KIND", KINDClOTHING);
                        startActivity(intent1);
                        Toast.makeText(ActivityMainUser.this, "hello clothing", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.itemItem:
                        startActivity(intent2);
                        intent2.putExtra("KIND", KINDClOTHING);
                        Toast.makeText(ActivityMainUser.this, "Hello item", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.itemAccount:
                        startActivity(intent4);
                        Toast.makeText(ActivityMainUser.this, "Helloo item account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.itemEditAccountUser :
                        Intent intent = new Intent(ActivityMainUser.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        ActivityMainUser.this.finish();
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawer(Gravity.START);
                return false;
            }
        });

    }
}
