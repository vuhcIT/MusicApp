package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.musicapp.Adapter.AllChuDeAdapter;
import com.example.musicapp.Model.ChuDe;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachChuDeActivity extends AppCompatActivity {
    RecyclerView recyclerViewtatcachude;
    Toolbar toolbartatcachude;

    AllChuDeAdapter allChuDeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_chu_de);
        init();
        Getdata();
    }

    private void Getdata() {
        Dataservice dataservice = APIService.getservice();
        Call<List<ChuDe>> callback = dataservice.GetTatCaChuDe();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> mangchude =(ArrayList<ChuDe>) response.body();
                allChuDeAdapter =new AllChuDeAdapter(DanhSachChuDeActivity.this,mangchude);
                recyclerViewtatcachude.setLayoutManager(new GridLayoutManager(DanhSachChuDeActivity.this,2));
                recyclerViewtatcachude.setAdapter(allChuDeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable throwable) {

            }
        });
    }

    private void init(){
        recyclerViewtatcachude = findViewById(R.id.recyclerviewdanhsachchude);
        toolbartatcachude = findViewById(R.id.toolbardanhsachchude);
        setSupportActionBar(toolbartatcachude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả chủ đề");
        toolbartatcachude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}