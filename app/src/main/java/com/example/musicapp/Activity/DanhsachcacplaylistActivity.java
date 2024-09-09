package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.musicapp.Adapter.DanhSachCacPlaylistAdapter;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.QuangCao;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachcacplaylistActivity extends AppCompatActivity {

    Toolbar toolbarplaylist;
    RecyclerView recyclerViewdanhsachplaylist;

    DanhSachCacPlaylistAdapter danhSachCacPlaylistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachcacplaylist);
        anhxa();
        init();
        Getdata();
    }

    private void Getdata() {
        Dataservice dataservice = APIService.getservice();
        Call<List<Playlist>> callback = dataservice.GetDanhSachPlaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> mangplaylist =(ArrayList<Playlist>) response.body();
                danhSachCacPlaylistAdapter = new DanhSachCacPlaylistAdapter(DanhsachcacplaylistActivity.this,mangplaylist);
                recyclerViewdanhsachplaylist.setLayoutManager(new GridLayoutManager(DanhsachcacplaylistActivity.this,2));
                recyclerViewdanhsachplaylist.setAdapter(danhSachCacPlaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable throwable) {

            }
        });

    }

    private void init(){
        setSupportActionBar(toolbarplaylist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Playlist");
        toolbarplaylist.setTitleTextColor(getResources().getColor(R.color.purple));
        toolbarplaylist.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbarplaylist = findViewById(R.id.toolbardanhsachplaylist);
        recyclerViewdanhsachplaylist = findViewById(R.id.recyclerviewdanhsachplaylist);
    }
}