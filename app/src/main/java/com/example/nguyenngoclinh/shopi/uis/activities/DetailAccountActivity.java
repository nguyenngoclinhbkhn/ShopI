package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.uis.BaseActivity;
import com.example.nguyenngoclinh.shopi.uis.fragment.FragmentManagerUser;

import java.io.ByteArrayOutputStream;

public class DetailAccountActivity extends BaseActivity {
    private TextView textViewName;
    private TextView textViewUserName;
    private TextView textViewPass;
    private TextView textViewPhone;
    private ImageView imageViewAccount;
    private String nameAccount;
    private String userNameAccount;
    private String phoneNumber;
    private String passAccount;
    private ImageButton imageButtonBack;
    private String imageAccount;
    @Override
    public int injectLayout() {
        return R.layout.activity_detail_account;
    }

    @Override
    public void initView() {
        textViewName = findViewById(R.id.textViewNameAccountDetail);
        textViewUserName = findViewById(R.id.textViewUserNameAccountDetail);
        textViewPass = findViewById(R.id.textViewPassAccountDetail);
        textViewPhone = findViewById(R.id.textViewPhoneAccountDetail);
        imageViewAccount = findViewById(R.id.imageViewDetailAccount);
        imageButtonBack = findViewById(R.id.imageButtonBackDetailAccount);

    }

    @Override
    public void initVariable() {
        final Intent intent = getIntent();
        nameAccount = intent.getStringExtra("name");
        userNameAccount = intent.getStringExtra("userName");
        phoneNumber = intent.getStringExtra("phone");
        passAccount = intent.getStringExtra("pass");
        imageAccount = intent.getStringExtra("image");

        byte[] manghinh = Base64.decode(imageAccount.toString(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);

        textViewName.setText(nameAccount);
        textViewUserName.setText(userNameAccount);
        textViewPass.setText(passAccount);
        textViewPhone.setText(phoneNumber);
        imageViewAccount.setImageBitmap(bmp);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailAccountActivity.this, ManagerActivity.class);
                intent1.putExtra("id", ManagerActivity.ID_ADMIN);
                startActivity(intent1);
            }
        });

    }

    public byte[] ImageView_byte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
