package com.example.nguyenngoclinh.shopi.uis.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class ActivityOrder extends BaseActivity {
    private String KIND = ActivityMainUser.KINDClOTHING;
    private String idItem ;
    private TextView textViewIdItem;
    private TextView textViewNameItem;
    private Spinner spinnerQuantity;
    private Toolbar toolbar;
    private Button button;
    private EditText editTextAddressUser;
    private DatabaseReference reference;
    private ImageButton imageButtonBackItem;
    private DatabaseReference reference1;
    private String name;
    private String quantity;
    private String price;
    private String image;
    private List<String> listQuantity;
    private int statusBillOk = R.drawable.ic_thumb_up;
    private int statusBillNo = R.drawable.ic_thumb_down;
    private String kind ;
    private String addressUser;
    private DatabaseReference reference2;
    @Override
    public int injectLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolBarItem1);
        spinnerQuantity = findViewById(R.id.spinnerQuantityOrder);
        textViewIdItem = findViewById(R.id.edID);
        textViewNameItem = findViewById(R.id.edAddNameProduct);
        button = findViewById(R.id.btOder);
        imageButtonBackItem = findViewById(R.id.imageButtonBackItem);
        editTextAddressUser = findViewById(R.id.editTextAddressUser);


    }

    @Override
    public void initVariable() {
        setSupportActionBar(toolbar);
        reference1 = FirebaseDatabase.getInstance().getReference("products");
        reference = FirebaseDatabase.getInstance().getReference("bills");
        final Intent intent = getIntent();

        idItem = intent.getStringExtra("idItem");
        name = intent.getStringExtra("nameItem");
        price = intent.getStringExtra("price");
        kind = intent.getStringExtra("kind");
        image = intent.getStringExtra("image");
        Log.e("TAG", "id" + idItem);

        textViewNameItem.setText(name);
        textViewIdItem.setText(idItem);
        listQuantity = new ArrayList<>();
        reference1.child(idItem).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                String maxPosition = product.getQuantity();
                for (int i = 0; i <= Integer.parseInt(maxPosition); i++){
                    listQuantity.add(String.valueOf(i));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityOrder.this, android.R.layout.simple_spinner_item, listQuantity);
                spinnerQuantity.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ActivityOrder.this, "", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quantity = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityOrder.this, "quantity : " + quantity, Toast.LENGTH_SHORT).show();
                if (quantity.equals("0") == false) {
                    addressUser = editTextAddressUser.getText().toString().trim();
                    String idBill = reference.push().getKey();
                    reference.child(idBill).setValue(new Bill(idBill,
                            String.valueOf(Integer.parseInt(quantity) * Integer.parseInt(price)),
                            statusBillNo,
                            "0",
                            quantity,
                            name,
                            idItem,
                            addressUser,
                            image));
                    Toast.makeText(ActivityOrder.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ActivityOrder.this, "Đã hết hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageButtonBackItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ActivityOrder.this, ActivityClothing.class);
                intent.putExtra("KIND", KIND);
                startActivity(intent1);
            }
        });



    }
}
