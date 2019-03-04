package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.model.Bill;
import com.example.nguyenngoclinh.shopi.model.Product;
import com.example.nguyenngoclinh.shopi.uis.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailBill extends BaseActivity {
    private RecyclerView recyclerViewBill;
    private int statusBillOk = R.drawable.ic_thumb_up;
    private int statusBillNo = R.drawable.ic_thumb_down;
    private String id;
    private Toolbar toolbar;
    private TextView textViewNameProductDetailBill;
    private TextView textViewQuantityProductDetailBill;
    private TextView textViewSumPriceProductDetailBill;
    private ImageView imageViewStatusProductDetailBill;
    private Button buttonOkDetailBill;
    private Button buttonBackDetailBill;
    private DatabaseReference reference;
    private DatabaseReference reference1;

    private String nameProduct;
    private String idProduct;
    private String quantity;

    private String newQuantity;
    private String oldQuantity;
    private List<Product> listProduct;
    private String imageProduct;
    private DatabaseReference reference2;
    private String priceProduct;


    // status = "0" chua xu ly
    // status = "1" da xu ly
    @Override
    public int injectLayout() {
        return R.layout.activity_detail_bill;
    }

    @Override
    public void initView() {
        textViewNameProductDetailBill = findViewById(R.id.textViewNameProductDetailBill);
        textViewQuantityProductDetailBill = findViewById(R.id.textViewQuantityProductDetailBill);
        textViewSumPriceProductDetailBill = findViewById(R.id.textViewSumPriceDetailBill);
        imageViewStatusProductDetailBill = findViewById(R.id.imageViewStatusProductDetailBill);
        buttonOkDetailBill = findViewById(R.id.buttonOkDetailBill);
        buttonBackDetailBill = findViewById(R.id.buttonBackDetailBill);

    }

    @Override
    public void initVariable() {
        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        reference = FirebaseDatabase.getInstance().getReference("bills");
        reference1 = FirebaseDatabase.getInstance().getReference("products");
        reference2 = FirebaseDatabase.getInstance().getReference("productSells");
        listProduct = new ArrayList<>();
        if (reference != null) {
            reference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Bill bill = dataSnapshot.getValue(Bill.class);
                    quantity = bill.getQuantityProductBill();
                    textViewNameProductDetailBill.setText(bill.getNameProductBill());
                    textViewQuantityProductDetailBill.setText(bill.getQuantityProductBill());
                    textViewSumPriceProductDetailBill.setText(bill.getSumPriceBill());
                    imageViewStatusProductDetailBill.setImageResource(bill.getResourceImageBill());
                    imageProduct = bill.getImageProduct();
                    nameProduct = bill.getNameProductBill();
                    priceProduct = String.valueOf(Integer.parseInt(bill.getSumPriceBill()) / Integer.parseInt(bill.getQuantityProductBill()));
                    idProduct = bill.getIdProduct();

                    reference1.child(idProduct).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Product p = dataSnapshot.getValue(Product.class);
                            oldQuantity = p.getQuantity();
                            newQuantity = String.valueOf(Integer.parseInt(oldQuantity) - Integer.parseInt(quantity));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    if (bill.getStatusBill().equals("0")) {
                        buttonOkDetailBill.setEnabled(true);
                    } else {
                        buttonOkDetailBill.setEnabled(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        buttonOkDetailBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(id).child("resourceImageBill").setValue(statusBillOk);
                reference.child(id).child("statusBill").setValue("1");

                reference2.child(idProduct).setValue(new Product(idProduct,
                       nameProduct,
                        priceProduct,
                        "",
                        imageProduct,
                        "",
                        quantity));
                Log.e("TAG", ":" + newQuantity);
                reference1.child(idProduct).child("quantity").setValue(newQuantity);

//                Log.e("TAG", ": " + newQuantity);
//                reference1.child(idProduct).child("quantity").setValue(newQuantity);
                Toast.makeText(DetailBill.this, "Đã xử lý đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });
        buttonBackDetailBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailBill.this, ManagerActivity.class);
                intent1.putExtra("id", ManagerActivity.ID_ADMIN);
                startActivity(intent1);
            }
        });
    }
}
