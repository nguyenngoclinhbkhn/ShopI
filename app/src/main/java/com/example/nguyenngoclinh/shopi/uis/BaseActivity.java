package com.example.nguyenngoclinh.shopi.uis;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(injectLayout());
        context = BaseActivity.this;

        initView();
        initVariable();
    }

    // xac dinh layout hien thi cho activity
    public abstract int injectLayout();

    // khoi tao cac view
    public abstract void initView();


    // khoi tao cac gia tri ban dau
    public abstract void initVariable();

    public void showProgressDialog(){
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.show();
                dialog.setCancelable(false);
            }
        }catch (Exception e){
            Log.e("TAG", "Loi " + e.toString());
        }
    }
    public void closeProgressDialog(){
        try {
            if (dialog != null) {
                dialog = new ProgressDialog(this);
                dialog.cancel();
                dialog = null;
            }
        } catch(Exception e){
            Log.e("TAG", "Loi " + e.toString());
        }
    }
}
