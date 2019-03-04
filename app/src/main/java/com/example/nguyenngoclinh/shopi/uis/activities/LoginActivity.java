package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.model.User;
import com.example.nguyenngoclinh.shopi.uis.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText editTextUser;
    private EditText editTextPass;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnLostPass;
    private CheckBox checkBoxSaveAccount;
    private String userName;
    private String pass;
    private boolean isCheck;
    private ViewFlipper viewFlipper;
    private TextView textViewLostPass;
    private DatabaseReference reference;
    private String key;
    private List<User> listUser;



    // 1 : quan ao
    // 2 : dung cu
    // 3 : phu kien


    @Override
    public int injectLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        editTextPass = findViewById(R.id.editTextPass);
        editTextUser = findViewById(R.id.editTextUserName);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        checkBoxSaveAccount = findViewById(R.id.checkBoxSaveAccount);
        viewFlipper = findViewById(R.id.viewFlipperLogin);
        textViewLostPass = findViewById(R.id.textViewLostPass);

    }

    @Override
    public void initVariable() {
        Intent intent = getIntent();
        userName = "";
        pass = "";
        key = "";
//        listUser = new ArrayList<>();
        btnLogin.setOnClickListener(this);
        textViewLostPass.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        isCheck = false;
        key = intent.getStringExtra("key");
        actionViewFlipper();
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        if (key != null) {
            reference.child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    userName = user.getUserName();
                    editTextUser.setText(userName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "khong lay duoc du lieu", Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnLogin.setEnabled(false);
        btnLogin.setTextColor(Color.BLACK);
        btnLogin.setBackgroundColor(Color.WHITE);

        editTextUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    btnLogin.setBackgroundColor(Color.RED);
                    btnLogin.setTextColor(Color.WHITE);
                    btnLogin.setEnabled(true);
                }else{
                    btnLogin.setEnabled(false);
                    btnLogin.setTextColor(Color.BLACK);
                    btnLogin.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {
                userName = editTextUser.getText().toString().trim();
                pass = editTextPass.getText().toString().trim();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listUser = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            listUser.add(new User(user.getId(),
                                    user.getName(),
                                    user.getUserName(),
                                    user.getPass(),
                                    user.getPhone(),
                                    user.getImage(),
                                    user.getLevel()));
                        }
                        if (TextUtils.isEmpty(userName) || (TextUtils.isEmpty(pass))) {
                            Toast.makeText(LoginActivity.this, "Nhap lai thong tin", Toast.LENGTH_SHORT).show();
                        } else {
                            for (User itemUser : listUser) {
                                if ((userName.equals(itemUser.getUserName()) &&
                                        pass.equals(itemUser.getPass()) &&
                                        itemUser.getLevel() == 3)) {
                                    Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
                                    intent.putExtra("id", itemUser.getId());
                                    startActivity(intent);
                                }
                                if ((userName.equals(itemUser.getUserName()) &&
                                        pass.equals(itemUser.getPass()) &&
                                        itemUser.getLevel() != 3)) {
                                    Intent intent = new Intent(LoginActivity.this, ActivityMainUser.class);
                                    intent.putExtra("id", itemUser.getId());
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this, "Nguoi dung ha em ?", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "sai tai khoan hoac mat khau roi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
            break;
            case R.id.btnRegister: {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.textViewLostPass: {
                Toast.makeText(this, "Hello ban da mat mk", Toast.LENGTH_SHORT).show();
            }
            break;
        }

    }

    private void actionViewFlipper() {
        List<String> list = new ArrayList<>();
        list.add("http://s1.storage.5giay.vn/image/2015/09/20150929_1f962f8664e0e2c7f6865b47ad36393a_1443523578.JPG");
        list.add("http://i1184.photobucket.com/albums/z328/xblack1991/IMG_20140722_173941_zps5330fa00.jpg");
        list.add("http://i.imgur.com/2RWFn0I.jpg");
        list.add("https://sieumuanhanh.com/Data/tin-tuc/201708/tu-van-mo-cua-hang-dung-cu-the-duc-the-thao.jpg");
        list.add("https://file.hstatic.net/1000261193/file/quan_ao_bong_da_bulbul_1024x1024.jpg");
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(this).load(list.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3900);
        viewFlipper.setAutoStart(true);
    }
}
