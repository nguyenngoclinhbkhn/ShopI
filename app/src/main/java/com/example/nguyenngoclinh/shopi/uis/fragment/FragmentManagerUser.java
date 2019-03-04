package com.example.nguyenngoclinh.shopi.uis.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.adapter.AdapterUser;
import com.example.nguyenngoclinh.shopi.model.User;
import com.example.nguyenngoclinh.shopi.uis.activities.DetailAccountActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentManagerUser extends Fragment {
    private ListView listView;
    private AdapterUser adapterUser;
    private List<User> listUser;
    private DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_user, container, false);
        listView = view.findViewById(R.id.listViewManagerUser);
        listUser = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    listUser.add(new User(user.getId(),
                            user.getName(),
                            user.getUserName(),
                            user.getPass(),
                            user.getPhone(),
                            user.getImage(),
                            user.getLevel()));

                    Log.e("TAG", " : "+ user.getId()+ " : " +
                            user.getName()+ " : " +
                            user.getUserName()+ " : " +
                            user.getPass()+ " : " +
                            user.getPhone()+ " : " +
                            user.getImage()+ " : " +
                            user.getLevel());
                }
                adapterUser = new AdapterUser(getActivity(), R.layout.item_user, listUser);
                listView.setAdapter(adapterUser);
                adapterUser.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Khong lay duoc du lieu", Toast.LENGTH_SHORT).show();

            }
        });
        Log.e("TAG", "size : " + listUser.size());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = listUser.get(position);
                Intent intent = new Intent(getActivity(), DetailAccountActivity.class);
                intent.putExtra("name", user.getName());
                intent.putExtra("userName", user.getUserName());
                intent.putExtra("pass", user.getPass());
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("image", user.getImage());
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User user = listUser.get(position);
                Toast.makeText(getActivity(), "Ban da chon : " + user.getId(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return view;
    }
}
