package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.model.User;
import com.example.nguyenngoclinh.shopi.uis.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityDetailAccountUser extends BaseActivity {
    private TextView textViewNameUser;
    private TextView textViewUserName;
    private TextView textViewPassUser;
    private TextView textViewPhoneUser;
    private ImageView imageViewAccountUser;
    private Button btnEditAccount;
    private DatabaseReference reference;
    private String id = ActivityMainUser.ID_USER;
    private ImageButton imageButtonBack;
    @Override
    public int injectLayout() {
        return R.layout.activity_detail_account_user;
    }

    @Override
    public void initView() {
        textViewNameUser = findViewById(R.id.textViewNameAccountDetailUser);
        textViewPassUser = findViewById(R.id.textViewPassAccountDetailUser);
        textViewPhoneUser = findViewById(R.id.textViewPhoneAccountDetailUser);
        textViewUserName = findViewById(R.id.textViewUserNameAccountDetailUser);
        imageViewAccountUser = findViewById(R.id.imageViewDetailAccountUser);
        btnEditAccount = findViewById(R.id.btnEditAccountUser);
        imageButtonBack = findViewById(R.id.imageButtonBack);


    }

    @Override
    public void initVariable() {
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                textViewNameUser.setText(user.getName());
                textViewPassUser.setText(user.getPass());
                textViewPhoneUser.setText(user.getPhone());
                textViewUserName.setText(user.getUserName());
                if(user.getImage() != null) {
                    byte[] manghinh = Base64.decode(user.getImage().toString(), Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                    imageViewAccountUser.setImageBitmap(bmp);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ActivityDetailAccountUser.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDetailAccountUser.this, EditAccountActivity.class);
                startActivity(intent);
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDetailAccountUser.this, ActivityMainUser.class);
                intent.putExtra("id", ActivityMainUser.ID_USER);
                startActivity(intent);
            }
        });



    }
}
