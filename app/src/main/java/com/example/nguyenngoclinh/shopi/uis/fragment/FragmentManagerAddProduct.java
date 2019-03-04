package com.example.nguyenngoclinh.shopi.uis.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.model.Product;
import com.example.nguyenngoclinh.shopi.uis.activities.ManagerProductActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import static android.app.Activity.RESULT_OK;

public class FragmentManagerAddProduct extends Fragment implements View.OnClickListener {
    private EditText editTextNameProduct;
    private EditText editTextPriceProduct;
    private ImageView imageViewProduct;
    private ImageButton imageButtonTakePhotoProduct;
    private Button btnAddProduct;
    private Button btnCancleProduct;
    private String idProduct;
    private String nameProduct;
    private String priceProduct;
    private TextView textViewKindOfProduct;
    private DatabaseReference reference;
    private String quantity;
    private EditText editTextQuantity;
    private ImageButton imageButtonChooseImage;
    private final int REQUEST_CAPTURE = 111;
    private final int REQUEST_GALLERY = 222;
    private Uri filePath;
    private StorageReference referenceImage;
    private FirebaseStorage firebaseStorage;
    private Bitmap bitmap1;
    private String level = "";

    private ImageView imageViewTest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_add_product, container, false);
        editTextNameProduct = view.findViewById(R.id.editTextNameProduct);
        editTextPriceProduct = view.findViewById(R.id.editTextPriceProduct);
        imageButtonTakePhotoProduct = view.findViewById(R.id.imageBtnTakePhotoProduct);
        imageViewProduct = view.findViewById(R.id.imageViewProduct);
        btnAddProduct = view.findViewById(R.id.btnAddProduct);
        btnCancleProduct = view.findViewById(R.id.btnCancelProduct);
        textViewKindOfProduct = view.findViewById(R.id.textViewKindOfProduct);
        editTextQuantity = view.findViewById(R.id.editTextQuantity);
        imageButtonChooseImage = view.findViewById(R.id.imageButtonChooseImage);
        textViewKindOfProduct.setText(ManagerProductActivity.KIND);
        imageViewTest = view.findViewById(R.id.imageViewTest);

        btnAddProduct.setOnClickListener(this);
        btnCancleProduct.setOnClickListener(this);
        btnCancleProduct.setOnClickListener(this);
        imageButtonChooseImage.setOnClickListener(this);
        imageButtonTakePhotoProduct.setOnClickListener(this);

        if (ManagerProductActivity.KIND.equals("Quần áo")) {
            level = "1";
        } else if (ManagerProductActivity.KIND.equals("Dụng cụ")) {
            level = "2";
        } else {
            level = "3";
        }
        reference = FirebaseDatabase.getInstance().getReference("products");
        idProduct = "";
        nameProduct = "";
        priceProduct = "";
        quantity = "";
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddProduct: {
                idProduct = reference.push().getKey();
                nameProduct = editTextNameProduct.getText().toString().trim();
                priceProduct = editTextPriceProduct.getText().toString().trim();
                quantity = editTextQuantity.getText().toString().trim();
                if (bitmap1 != null || nameProduct.isEmpty() || priceProduct.isEmpty() || quantity.isEmpty()) {
                    Toast.makeText(getActivity(), "Nhap thieu thong tin san pham", Toast.LENGTH_SHORT).show();
                }else {
                    byte[] image = ImageView_byte(bitmap1);
                    String imageString = Base64.encodeToString(image, Base64.DEFAULT);
                    reference.child(idProduct).setValue(new Product(idProduct,
                            nameProduct,
                            priceProduct,
                            level,
                            imageString,
                            "",
                            quantity));
                    clearInfomation();
                    Toast.makeText(getActivity(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.btnCancelProduct: {
                clearInfomation();
                Toast.makeText(getActivity(), "ko them", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.imageBtnTakePhotoProduct: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);
//                Bitmap bitmap = getActivity().startActivityForResult(intent, REQUEST_ID_IMAGE_PICTURE);
            }
            break;
            case R.id.imageButtonChooseImage: {
//                getImageFromGallery();
                Toast.makeText(getActivity(), "Đang phát triển", Toast.LENGTH_SHORT).show();
            }
            ;
            break;
        }

    }

    public void getImageFromGallery() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_GALLERY);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Chọn 1 ảnh"), REQUEST_GALLERY);
    }


    public void clearInfomation() {
        editTextPriceProduct.setText("");
        editTextNameProduct.setText("");
        editTextQuantity.setText("");
        imageViewProduct.setImageBitmap(null);
    }

//    public void getImageFromCamera(){
//        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) !=
//                PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[] {Manifest.permission.CAMERA}, REQUEST_CAPTURE);
//        }else{
//            openCamera();
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == 2) {
            Uri uri = data.getData();
            try {
                bitmap1 = getBitmapFromUri(getActivity(), uri);
            } catch (FileNotFoundException e) {
            }
            imageViewProduct.setImageBitmap(bitmap1);
        }else{
            bitmap1 = (Bitmap) data.getExtras().get("data");
            imageViewProduct.setImageBitmap(bitmap1);
        }

    }

    public Bitmap getBitmapFromUri(Context context, Uri uriFile) throws FileNotFoundException {
        Bitmap bitmap = null;
        bitmap = decodeUri(context, uriFile);
        return bitmap;
    }

    public static Bitmap decodeUri(Context context, Uri uriSelectImage) throws FileNotFoundException {
        BitmapFactory.Options b = new BitmapFactory.Options();
        b.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uriSelectImage), null, b);
        final int REQUEST_SIZE = 140;
        int width = b.outWidth;
        int height = b.outHeight;
        int scale = 1;
        while (true) {
            if ((width < REQUEST_SIZE / 2) || (height < REQUEST_SIZE / 2)) {
                break;
            }
            width /= 2;
            height /= 2;
            scale *= 2;
        }
        BitmapFactory.Options b2 = new BitmapFactory.Options();
        b2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uriSelectImage), null, b2);
    }

    public byte[] ImageView_byte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }



}
