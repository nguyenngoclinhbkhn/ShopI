package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditAccountActivity extends BaseActivity implements View.OnClickListener {
    private EditText editTextNameUser;
    private TextView textViewUser;
    private EditText editTextPassUser;
    private EditText editTextPhoneNumber;
    private Button btnCancelEditUser;
    private Button btnSaveEditUser;
    private ImageView imageViewUser;
    private String nameUser = "";
    private String user = "";
    private String phoneNumber = "";
    private String pass = "";
    private String image = "";
    private String id = "";
    private DatabaseReference reference;
    private ImageButton imageButtonBack;
    private String imageString;
    private Toolbar toolbar;
    private Bitmap bitmapUser;
    private ImageButton imageButtonTakePhoto;
    private List<User> listUserRequest;
    private Bitmap bmp;

    @Override
    public int injectLayout() {
        return R.layout.activity_edit_account;
    }

    @Override
    public void initView() {
        editTextNameUser = findViewById(R.id.editTextNameAccountDetail);
        editTextPassUser = findViewById(R.id.editTextPassAccountDetail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneAccountDetail);
        textViewUser = findViewById(R.id.editTextUserNameAccountDetail);
        imageViewUser = findViewById(R.id.imageViewEditAccount);
        btnCancelEditUser = findViewById(R.id.btnCancelEditAccount);
        btnSaveEditUser = findViewById(R.id.btnSaveEditAccount);
        toolbar = findViewById(R.id.toolbarEditAccount);
        imageButtonBack = findViewById(R.id.imageButtonBackEditAccount);
        imageButtonTakePhoto = findViewById(R.id.takePhotoUser);
        setSupportActionBar(toolbar);


    }

    @Override
    public void initVariable() {
        btnSaveEditUser.setOnClickListener(this);
        btnCancelEditUser.setOnClickListener(this);
        imageButtonTakePhoto.setOnClickListener(this);
        imageButtonBack.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference("users");
        id = ActivityMainUser.ID_USER;

        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                textViewUser.setText(user.getUserName());
                editTextPhoneNumber.setText(user.getPhone());
                editTextPassUser.setText(user.getPass());
                editTextNameUser.setText(user.getName());
                if(user.getImage() != null) {
                    byte[] manghinh = Base64.decode(user.getImage().toString(), Base64.DEFAULT);
                    bmp = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                    imageViewUser.setImageBitmap(bmp);
                }else{
                    imageViewUser.setImageBitmap(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditAccountActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancelEditAccount: {
                Toast.makeText(this, "Bạn đã không thực hiện chỉnh sửa", Toast.LENGTH_SHORT).show();

            }
            break;
            case R.id.btnSaveEditAccount: {
                reference.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        nameUser = editTextNameUser.getText().toString().trim();
                        pass = editTextPassUser.getText().toString().trim();
                        phoneNumber = editTextPhoneNumber.getText().toString().trim();
                        if (bitmapUser != null) {
                            byte[] image = ImageView_byte(bitmapUser);
                            imageString = Base64.encodeToString(image, Base64.DEFAULT);
                        }else{
                            imageString = user.getImage();
                        }
                        reference.child(id).child("name").setValue(nameUser);
                        reference.child(id).child("pass").setValue(pass);
                        reference.child(id).child("phone").setValue(phoneNumber);
                        reference.child(id).child("image").setValue(imageString);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(EditAccountActivity.this, "Khong load duoc du lieu", Toast.LENGTH_SHORT).show();
                    }
                });

                listUserRequest = new ArrayList<>();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            listUserRequest.add(user);
                        }
                        for (User user : listUserRequest) {
                            if (user.getId().equals(id)) {
                                textViewUser.setText(user.getUserName());
                                editTextNameUser.setText(user.getName());
                                editTextPassUser.setText(user.getPass());
                                editTextPhoneNumber.setText(user.getPhone());
                                if (user.getImage() != null) {
                                    byte[] manghinh = Base64.decode(user.getImage().toString(), Base64.DEFAULT);
                                    Bitmap bmp = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                                    imageViewUser.setImageBitmap(bmp);
                                }
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(EditAccountActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(this, "Chỉnh sửa thông tin thành công", Toast.LENGTH_SHORT).show();

            }
            break;
            case R.id.imageButtonBackEditAccount: {
                Intent intent = new Intent(EditAccountActivity.this, ActivityDetailAccountUser.class);
                startActivity(intent);

            }
            break;
            case R.id.takePhotoUser: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);
            }
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmapUser = (Bitmap) data.getExtras().get("data");
        imageViewUser.setImageBitmap(bitmapUser);
    }

    public byte[] ImageView_byte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
