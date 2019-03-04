package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.uis.BaseActivity;
import com.example.nguyenngoclinh.shopi.uis.fragment.FragmentManagerAddProduct;
import com.example.nguyenngoclinh.shopi.uis.fragment.FragmentManagerInventoryProduct;
import com.example.nguyenngoclinh.shopi.uis.fragment.FragmentManagerSelledProduct;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManagerProductActivity extends BaseActivity implements View.OnClickListener{
    private DrawerLayout drawerLayoutManagerProduct;
    private NavigationView navigationViewManagerProduct;
    private Toolbar toolbarManagerProduct;
    private FragmentManagerAddProduct addProduct;
    private FragmentManagerInventoryProduct inventoryProduct;
    private FragmentManagerSelledProduct selledProduct;
    public static String KIND = "";
    private TextView textViewToolbar;
    @Override
    public int injectLayout() {
        return R.layout.activity_manager_product;
    }

    @Override
    public void initView() {
        drawerLayoutManagerProduct = findViewById(R.id.drawerLayOutManagerProduct);
        navigationViewManagerProduct = findViewById(R.id.navigationViewManagerProduct);
        toolbarManagerProduct = findViewById(R.id.toolbarActivityManagerProduct);
        textViewToolbar = findViewById(R.id.textViewToolbarManagerProduct);
    }

    @Override
    public void initVariable() {
        addProduct = new FragmentManagerAddProduct();
        selledProduct = new FragmentManagerSelledProduct();
        inventoryProduct = new FragmentManagerInventoryProduct();

        Intent intent = getIntent();
        KIND = intent.getStringExtra("kind");
        Toast.makeText(this, "Hello : " + KIND, Toast.LENGTH_SHORT).show();
        textViewToolbar.setText(KIND);

        setSupportActionBar(toolbarManagerProduct);
        toolbarManagerProduct.setNavigationIcon(R.drawable.ic_action_list);
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        if(inventoryProduct.isAdded()){
            transaction1.show(inventoryProduct);
        }else{
            transaction1.add(R.id.frameLayOutContainerManagerProduct, inventoryProduct);
        }
        if(selledProduct.isAdded() || addProduct.isAdded()){
            transaction1.hide(selledProduct);
            transaction1.hide(addProduct);
        }
        transaction1.commit();
        toolbarManagerProduct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutManagerProduct.openDrawer(Gravity.START);
            }
        });

        navigationViewManagerProduct.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.itemAddProduct : {
                        if(addProduct.isAdded()){
                            transaction.show(addProduct);
                        }else{
                            transaction.add(R.id.frameLayOutContainerManagerProduct, addProduct);
                        }
                        if(selledProduct.isAdded() || inventoryProduct.isAdded()){
                            transaction.hide(selledProduct);
                            transaction.hide(inventoryProduct);
                        }
                        transaction.commit();
                    } break;
                    case R.id.itemInventoryProduct : {
                        if(inventoryProduct.isAdded()){
                            transaction.show(inventoryProduct);
                        }else{
                            transaction.add(R.id.frameLayOutContainerManagerProduct, inventoryProduct);
                        }
                        if(addProduct.isAdded() || selledProduct.isAdded()){
                            transaction.hide(addProduct);
                            transaction.hide(selledProduct);
                        }
                        transaction.commit();
                    } break;
                    case R.id.itemBackProduct : {
                        Intent intent = new Intent(ManagerProductActivity.this, ManagerActivity.class);
                        intent.putExtra("id",ManagerActivity.ID_ADMIN);
                        startActivity(intent);
                    } break;
                }
                drawerLayoutManagerProduct.closeDrawer(Gravity.START);
                return false;
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}
