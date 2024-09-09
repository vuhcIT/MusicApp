package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaiHatYeuthichAdapter;
import com.example.musicapp.Model.BaiHat;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_BaiHatYeuThich extends Fragment {
    View view;
    RecyclerView recyclerViewbaihatyeuthich;

    BaiHatYeuthichAdapter baiHatYeuthichAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_baihatyeuthich,container,false);
        recyclerViewbaihatyeuthich = view.findViewById(R.id.recyclerviewbaihatyeuthich);
        Getdata();
        return view;
    }

    private void Getdata() {
        Dataservice dataservice = APIService.getservice();
        Call<List<BaiHat>> callback = dataservice.GetBaiHatYeuThich();
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> baiHatArrayList =(ArrayList<BaiHat>) response.body();
                baiHatYeuthichAdapter = new BaiHatYeuthichAdapter(getActivity(),baiHatArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewbaihatyeuthich.setLayoutManager(linearLayoutManager);
                recyclerViewbaihatyeuthich.setAdapter(baiHatYeuthichAdapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable throwable) {

            }
        });
    }
}
