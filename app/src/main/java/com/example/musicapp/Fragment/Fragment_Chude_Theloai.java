package com.example.musicapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.DanhSachChuDeActivity;
import com.example.musicapp.Adapter.ChudeAdapter;
import com.example.musicapp.Model.ChuDe;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Chude_Theloai extends Fragment {
    View view;
    ArrayList<ChuDe> mangchude;

    ChudeAdapter chudeAdapter;
    RecyclerView recyclerViewchude;

    TextView txtviewtitlechude, txtxemthemchude;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chudevatheloai,container,false);
        recyclerViewchude = view.findViewById(R.id.recyclerviewchude);
        txtviewtitlechude = view.findViewById(R.id.textviewchude);
        txtxemthemchude = view.findViewById(R.id.textviewxemthemchude);
        txtxemthemchude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachChuDeActivity.class);
                startActivity(intent);
            }
        });
        Getdatachude();
        return view;
    }

    private void Getdatachude() {
        Dataservice dataservice = APIService.getservice();
        Call<List<ChuDe>> callback = dataservice.GetChude();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                mangchude =(ArrayList<ChuDe>) response.body();
                chudeAdapter = new ChudeAdapter(getActivity(),mangchude);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewchude.setLayoutManager(linearLayoutManager);
                recyclerViewchude.setAdapter(chudeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable throwable) {

            }
        });
    }
}
