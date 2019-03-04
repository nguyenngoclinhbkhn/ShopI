package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private Button btnRegister;
    private Button btnCancel;
    private EditText editTextUser;
    private EditText editTextPass;
    private EditText editTextPhoneNumber;
    private ImageButton imgBtnBackLogin;
    private String userName;
    private String pass;
    private String phoneNumber;
    private String name;
    private DatabaseReference reference;
    private EditText editTextUserNameAccount;
    private final int levelAccount = 1;
    private List<User> listUser;
    private boolean isCheck = false;


    @Override
    public int injectLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        btnCancel = findViewById(R.id.btnCancelRegisterAccount);
        btnRegister = findViewById(R.id.btnRegisterAccount);
        editTextPass = findViewById(R.id.editTextPassRegister);
        editTextUser = findViewById(R.id.editTextUserNameRegister);
        editTextPhoneNumber = findViewById(R.id.editTextPhone);
        imgBtnBackLogin = findViewById(R.id.imageButtonBackLogin);
        editTextUserNameAccount = findViewById(R.id.editTextUserNameAccount);

    }

    @Override
    public void initVariable() {
        name = "";
        userName = "";
        pass = "";
        phoneNumber = "";
        btnRegister.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgBtnBackLogin.setOnClickListener(this);
        reference = FirebaseDatabase.getInstance().getReference("users");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancelRegisterAccount: {
                clearText();
            }
            break;
            case R.id.btnRegisterAccount: {
                userName = editTextUser.getText().toString().trim();
                pass = editTextPass.getText().toString().trim();
                phoneNumber = editTextPhoneNumber.getText().toString().trim();
                name = editTextUserNameAccount.getText().toString().trim();
                listUser = new ArrayList<>();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);
                            listUser.add(user);
                        }
                        for (User user : listUser){
                            if(user.getUserName().equals(userName)){
                                isCheck = true; break;
                            }
                        }
                        if(isCheck == true){
                            Toast.makeText(RegisterActivity.this, "Tài khoản này đã có, mời lập tài khoản khác", Toast.LENGTH_SHORT).show();
                        }else{
                            String userId = reference.push().getKey();
                            User user = new User(userId, name, userName, pass, phoneNumber, "", levelAccount);
                            reference.child(userId).setValue(user);
                            Toast.makeText(RegisterActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("key", userId);
                            Log.e("KEY", " : " + userId);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(RegisterActivity.this, "Khong lay duoc data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            break;
            case R.id.imageButtonBackLogin: {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    public void clearText() {
        editTextUserNameAccount.setText("");
        editTextUser.setText("");
        editTextPass.setText("");
        editTextPhoneNumber.setText("");
    }
}
