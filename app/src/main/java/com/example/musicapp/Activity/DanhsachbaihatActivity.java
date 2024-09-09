package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.musicapp.Adapter.DanhSachBaiHatAdapter;
import com.example.musicapp.Model.Album;
import com.example.musicapp.Model.BaiHat;
import com.example.musicapp.Model.ChuDe;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.QuangCao;
import com.example.musicapp.Model.TheLoai;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachbaihatActivity extends AppCompatActivity {
    QuangCao quangCao;
    Playlist playlist;
    ChuDe chuDe;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewdanhsachbaihat;
    FloatingActionButton floatingActionButton;
    ImageView imgdanhsachcakhuc;
    ArrayList<BaiHat> mangbaihat;
    DanhSachBaiHatAdapter danhSachBaiHatAdapter;
    TheLoai theLoai;
    Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbaihat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataIntent();
        anhxa();
        init();
        if (quangCao != null && !quangCao.getTenBaiHat().equals("")){
            setValueinView(quangCao.getTenBaiHat(), quangCao.getHinhAnhBai());
            GetdataQuangCao(quangCao.getIdQuangCao());
        }
        if(playlist != null && !playlist.getTenPlaylist().equals("")){
            setValueinView(playlist.getTenPlaylist(), playlist.getHinhNen());
            GetdataPlaylist(playlist.getIDPlaylist());
        }
        if(album != null && !album.getTenAlbum().equals("")) {
            setValueinView(album.getTenAlbum(), album.getHinhAlbum());
            GetdataAlbum(album.getIDAlbum());
        }
        if(chuDe != null && !chuDe.getTenChuDe().equals("")) {
            setValueinView(chuDe.getTenChuDe(), chuDe.getHinhChuDe());
            GetdataChuDe(chuDe.getIDChuDe());
        }
        if(theLoai != null && !theLoai.getTenTheLoai().equals("")) {
            setValueinView(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            GetdataTheLoai(theLoai.getIDTheLoai());
        }
    }

    private void GetdataTheLoai(String IDTheLoai) {
        Dataservice dataservice = APIService.getservice();
        Call<List<BaiHat>> callback = dataservice.GetDanhSachTheoTheLoai(Integer.parseInt(IDTheLoai));
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat =(ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this,mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhSachBaiHatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable throwable) {

            }
        });
    }

    private void GetdataChuDe(String IDChuDe) {
        Dataservice dataservice = APIService.getservice();
        Call<List<BaiHat>> callback = dataservice.GetDanhSachTheoChuDe(Integer.parseInt(IDChuDe));
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat =(ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this,mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhSachBaiHatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable throwable) {

            }
        });
    }

    private void GetdataAlbum(String IDAlbum) {
        Dataservice dataservice = APIService.getservice();
        Call<List<BaiHat>> callback = dataservice.GetDanhSachTheoAlbum(Integer.parseInt(IDAlbum));
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat =(ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this,mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhSachBaiHatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable throwable) {

            }
        });
    }

    private void GetdataPlaylist(String IDPlaylist) {
        Dataservice dataservice = APIService.getservice();
        Call<List<BaiHat>> callback = dataservice.GetDanhSachTheoPlaylist(Integer.parseInt(IDPlaylist));
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat =(ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this,mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhSachBaiHatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable throwable) {

            }
        });
    }

    private void setValueinView(String ten, String hinh){
        collapsingToolbarLayout.setTitle(ten);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(hinh);
                    final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    if (bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                                collapsingToolbarLayout.setBackground(bitmapDrawable);
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Picasso.get().load(hinh).into(imgdanhsachcakhuc);
    }

    private void GetdataQuangCao(String IDQuangCao) {
        Dataservice dataservice = APIService.getservice();
        Call<List<BaiHat>> callback = dataservice.Getdanhsachbaihattheoquangcao(Integer.parseInt(IDQuangCao));
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                 mangbaihat =(ArrayList<BaiHat>) response.body();
                 danhSachBaiHatAdapter =new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this,mangbaihat);
                 recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                 recyclerViewdanhsachbaihat.setAdapter(danhSachBaiHatAdapter);
                 evenClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable throwable) {

            }
        });
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void anhxa() {
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        collapsingToolbarLayout = findViewById(R.id.collapstringtoolbar);
        recyclerViewdanhsachbaihat = findViewById(R.id.recyclerviewdanhsachbaihat);
        toolbar = findViewById(R.id.toolbardanhsach);
        floatingActionButton = findViewById(R.id.floatingactionbutton);
        imgdanhsachcakhuc = findViewById(R.id.imageviewdanhsachcakhuc);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if(intent != null){
            if (intent.hasExtra("banner")) {
                quangCao =(QuangCao) intent.getSerializableExtra("banner");
                Toast.makeText(this,quangCao.getTenBaiHat(),Toast.LENGTH_SHORT).show();
            }
            if(intent.hasExtra("itemplaylist")){
                playlist =(Playlist) intent.getSerializableExtra("itemplaylist");
                Toast.makeText(this,playlist.getTenPlaylist(),Toast.LENGTH_SHORT).show();
            }

            if (intent.hasExtra("album")){
                album =(Album) intent.getSerializableExtra("album");
                Toast.makeText(this,album.getTenAlbum(),Toast.LENGTH_SHORT).show();
            }

            if (intent.hasExtra("chude")){
                chuDe =(ChuDe) intent.getSerializableExtra("chude");
                Toast.makeText(this,chuDe.getTenChuDe(),Toast.LENGTH_SHORT).show();
            }

            if (intent.hasExtra("theloai")){
                theLoai =(TheLoai) intent.getSerializableExtra("theloai");
                Toast.makeText(this,theLoai.getTenTheLoai(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void evenClick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhsachbaihatActivity.this, PlayMusicActivity.class);
                intent.putExtra("cacbaihat",mangbaihat);
                startActivity(intent);
            }
        });
    }
}