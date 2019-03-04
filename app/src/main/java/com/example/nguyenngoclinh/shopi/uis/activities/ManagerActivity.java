package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.model.User;
import com.example.nguyenngoclinh.shopi.uis.BaseActivity;
import com.example.nguyenngoclinh.shopi.uis.fragment.FragmentManagerBill;
import com.example.nguyenngoclinh.shopi.uis.fragment.FragmentManagerProduct;
import com.example.nguyenngoclinh.shopi.uis.fragment.FragmentManagerSelledProduct;
import com.example.nguyenngoclinh.shopi.uis.fragment.FragmentManagerUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class ManagerActivity extends BaseActivity implements View.OnClickListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FragmentManagerUser managerUser;
    private FragmentManagerProduct managerProduct;
    private FragmentManagerBill managerBill;
    private FragmentManagerSelledProduct selledProduct;
    private TextView textViewNameUser;
    private ImageView imageViewUser;
    private ImageButton imgButtonTakePhotoAdmin;
    private ImageButton imageButtonSavePhotoAdmin;
    private Bitmap bitmap;
    private DatabaseReference reference;
    public static String ID_ADMIN;

    @Override
    public int injectLayout() {
        return R.layout.activity_manager;
    }

    @Override
    public void initView() {
        navigationView = findViewById(R.id.navigationMenuManager);
        drawerLayout = findViewById(R.id.drawerLayOutAdmin);
        toolbar = findViewById(R.id.toolbarManagerAdmin);
        textViewNameUser = navigationView.getHeaderView(0).findViewById(R.id.textViewNameAdmin);
        imageViewUser = navigationView.getHeaderView(0).findViewById(R.id.imageViewAdmin);
        imgButtonTakePhotoAdmin = navigationView.getHeaderView(0).findViewById(R.id.imageButtonTakePhotoAdmin);
        imageButtonSavePhotoAdmin = navigationView.getHeaderView(0).findViewById(R.id.imageButtonSavePhotoAdmin);

    }

    @Override
    public void initVariable() {
        Intent intent = getIntent();
        setSupportActionBar(toolbar);
        ID_ADMIN = intent.getStringExtra("id");
        reference = FirebaseDatabase.getInstance().getReference("users");

        imageButtonSavePhotoAdmin.setOnClickListener(this);
        imgButtonTakePhotoAdmin.setOnClickListener(this);

        managerUser = new FragmentManagerUser();
        managerBill = new FragmentManagerBill();
        managerProduct = new FragmentManagerProduct();
        selledProduct = new FragmentManagerSelledProduct();

        toolbar.setTitleTextColor(Color.WHITE);


//
//        String name = intent.getStringExtra("nameUser");
//        String image = intent.getStringExtra("imageUser");

        reference.child(ID_ADMIN).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                textViewNameUser.setText(user.getName());
                if (user.getImage() != null) {
                    byte[] manghinh = Base64.decode(user.getImage().toString(), Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                    imageViewUser.setImageBitmap(bmp);
                } else {
                    imageViewUser.setImageBitmap(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManagerActivity.this, "Khong lay duoc du lieu", Toast.LENGTH_SHORT).show();
            }
        });

//        byte[] manghinh = Base64.decode(image.toString(), Base64.DEFAULT);
//        Bitmap bmp = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
//        imageViewUser.setImageBitmap(bmp);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_list);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameContainer, managerProduct);
        transaction.commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.itemManagerUser: {
                        if (managerUser.isAdded()) {
                            transaction1.show(managerUser);
                        } else {
                            transaction1.add(R.id.frameContainer, managerUser);
                        }
                        if (managerBill.isAdded() || managerProduct.isAdded() || selledProduct.isAdded()) {
                            transaction1.hide(managerBill);
                            transaction1.hide(managerProduct);
                            transaction1.hide(selledProduct);
                        }
                        transaction1.commit();
                    }
                    break;
                    case R.id.itemManagerBill: {
                        if (managerBill.isAdded()) {
                            transaction1.show(managerBill);
                        } else {
                            transaction1.add(R.id.frameContainer, managerBill);
                        }
                        if (managerUser.isAdded() || managerProduct.isAdded() || selledProduct.isAdded()) {
                            transaction1.hide(managerUser);
                            transaction1.hide(managerProduct);
                            transaction1.hide(selledProduct);
                        }
                        transaction1.commit();
                    }
                    break;
                    case R.id.itemManagerProduct: {
                        if (managerProduct.isAdded()) {
                            transaction1.show(managerProduct);
                        } else {
                            transaction1.add(R.id.frameContainer, managerProduct);
                        }
                        if (managerUser.isAdded() || managerBill.isAdded() || selledProduct.isAdded()) {
                            transaction1.hide(managerUser);
                            transaction1.hide(managerBill);
                            transaction1.hide(selledProduct);
                        }
                        transaction1.commit();
                    } break;
                    case R.id.itemSelledProduct : {
                        if (selledProduct.isAdded()){
                            transaction1.show(selledProduct);
                        }else{
                            transaction1.add(R.id.frameContainer, selledProduct);
                        }
                        if(managerUser.isAdded() || managerBill.isAdded() || managerProduct.isAdded()){
                            transaction1.hide(managerUser);
                            transaction1.hide(managerProduct);
                            transaction1.hide(managerBill);
                        }
                        transaction1.commit();
                    }
                    break;
                    case R.id.itemLogOut: {
                        Intent intent = new Intent(ManagerActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        ManagerActivity.this.finish();
                        startActivity(intent);
                    }
                    break;
                }
                drawerLayout.closeDrawer(Gravity.START);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonTakePhotoAdmin: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);
            }
            break;
            case R.id.imageButtonSavePhotoAdmin: {
                if (bitmap != null) {
                    byte[] image = ImageView_byte(bitmap);
                    String imageString = Base64.encodeToString(image, Base64.DEFAULT);
                    reference.child(ID_ADMIN).child("image").setValue(imageString);
                    Toast.makeText(this, "Lưu ảnh thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Chưa có ảnh", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        imageViewUser.setImageBitmap(bitmap);
    }

    public byte[] ImageView_byte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
