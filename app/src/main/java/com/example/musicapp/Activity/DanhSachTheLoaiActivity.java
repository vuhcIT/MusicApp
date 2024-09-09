package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.musicapp.Adapter.AllTheLoaiAdapter;
import com.example.musicapp.Model.TheLoai;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTheLoaiActivity extends AppCompatActivity {
    RecyclerView recyclerViewtatcatheloai;
    Toolbar toolbartatcatheloai;
    AllTheLoaiAdapter allTheLoaiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_the_loai);
        init();
        Getdata();
    }

    private void Getdata() {
        Dataservice dataservice = APIService.getservice();
        Call<List<TheLoai>> callback = dataservice.GetTatCaTheLoai();
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> mangtheloai =(ArrayList<TheLoai>) response.body();
                allTheLoaiAdapter = new AllTheLoaiAdapter(DanhSachTheLoaiActivity.this,mangtheloai);
                recyclerViewtatcatheloai.setLayoutManager(new GridLayoutManager(DanhSachTheLoaiActivity.this,2));
                recyclerViewtatcatheloai.setAdapter(allTheLoaiAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable throwable) {

            }
        });
    }

    private void init(){
        recyclerViewtatcatheloai = findViewById(R.id.recyclerviewtatcatheloai);
        toolbartatcatheloai = findViewById(R.id.toolbartatcatheloai);
        setSupportActionBar(toolbartatcatheloai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả các thể loại");
        toolbartatcatheloai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}