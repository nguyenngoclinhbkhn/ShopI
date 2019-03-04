package com.example.nguyenngoclinh.shopi.uis.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.adapter.AdapterBill;
import com.example.nguyenngoclinh.shopi.model.Bill;
import com.example.nguyenngoclinh.shopi.uis.activities.DetailBill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentManagerBill extends Fragment {
    private RecyclerView recyclerViewBill;
    private AdapterBill adapterBill;
    private List<Bill> listBill;
    private int statusBillOk = R.drawable.ic_thumb_up;
    private int statusBillNo = R.drawable.ic_thumb_down;
    private String statusBil = "1";
    private DatabaseReference reference;

    // status = "0" chua xu ly
    // status = "1" da xu ly
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_bill, container, false);
        recyclerViewBill = view.findViewById(R.id.recyclerViewFragmentBill);
        reference = FirebaseDatabase.getInstance().getReference("bills");
        if(reference != null){
            listBill = new ArrayList<>();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listBill.clear(); // ? phai hoi cho nay
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Bill bill = snapshot.getValue(Bill.class);
                        if(bill.getStatusBill().equals("0")){
                            listBill.add(bill);
                        }
                        if(bill.getStatusBill().equals("1")){
                            listBill.add(bill);
                        }
                    }
                    adapterBill = new AdapterBill(getActivity(), listBill);
                    recyclerViewBill.setAdapter(adapterBill);

                    adapterBill.notifyDataSetChanged();

                    RecyclerView.LayoutManager lineaLayout = new LinearLayoutManager(getActivity());
                    recyclerViewBill.setLayoutManager(lineaLayout);

                    adapterBill.setOnItemClickedListener(new AdapterBill.OnItemClickedListener() {
                        @Override
                        public void ClickedListener(View view, int possion) {
                            Bill bill = listBill.get(possion);
                            Intent intent = new Intent(getActivity(), DetailBill.class);
                            intent.putExtra("id", bill.getIdBill());
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Khong lay duoc du lieu", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}
