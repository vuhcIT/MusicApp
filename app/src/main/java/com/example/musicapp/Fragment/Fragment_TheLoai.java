package com.example.musicapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.DanhSachTheLoaiActivity;
import com.example.musicapp.Adapter.TheloaiAdapter;
import com.example.musicapp.Model.TheLoai;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_TheLoai extends Fragment {
    View view;
    RecyclerView recyclerViewtheloai;
    TextView txttentheloai, txtxemthemtheloai;

    TheloaiAdapter theloaiAdapter;

    ArrayList<TheLoai> mangtheloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theloai,container,false);
        txttentheloai = view.findViewById(R.id.textviewtheloai);
        recyclerViewtheloai = view.findViewById(R.id.recyclerviewtheloai);
        txtxemthemtheloai = view.findViewById(R.id.textviewxemthemtheloai);
        txtxemthemtheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachTheLoaiActivity.class);
                startActivity(intent);
            }
        });
        Getdatatheloai();
        return view;
    }

    private void Getdatatheloai() {
        Dataservice dataservice = APIService.getservice();
        Call<List<TheLoai>> callback = dataservice.GetTheloai();
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                mangtheloai =(ArrayList<TheLoai>) response.body();
                theloaiAdapter = new TheloaiAdapter(getActivity(),mangtheloai);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewtheloai.setLayoutManager(linearLayoutManager);
                recyclerViewtheloai.setAdapter(theloaiAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable throwable) {

            }
        });
    }
}
