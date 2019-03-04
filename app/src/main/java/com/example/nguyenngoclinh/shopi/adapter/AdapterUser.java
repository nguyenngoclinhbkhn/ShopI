package com.example.nguyenngoclinh.shopi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguyenngoclinh.shopi.R;
import com.example.nguyenngoclinh.shopi.model.User;

import java.util.List;

public class AdapterUser extends ArrayAdapter<User> {
    private LayoutInflater inflater;
    private List<User> listUser;


    public AdapterUser(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.listUser = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_user, parent, false);
            viewHolder.textViewNameUser = convertView.findViewById(R.id.textViewNameUser);
            viewHolder.textViewPass = convertView.findViewById(R.id.textViewPassUser);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = listUser.get(position);
        viewHolder.textViewNameUser.setText(user.getUserName());
        viewHolder.textViewPass.setText(user.getPass());

        return convertView;
    }

    public static class ViewHolder{
        public TextView textViewNameUser;
        public TextView textViewPass;
    }
}
