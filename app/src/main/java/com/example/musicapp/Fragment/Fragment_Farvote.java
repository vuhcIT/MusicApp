package com.example.musicapp.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaiHatYeuthichAdapter;
import com.example.musicapp.Adapter.FarvoteAdapter;
import com.example.musicapp.Model.BaiHat;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Farvote extends Fragment {
    View view;
    RecyclerView recyclerView;
    FarvoteAdapter farvoteAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_yeuthich,container,false);
        recyclerView = view.findViewById(R.id.recyclerviewfarvote);
        Getdata();
        return view;
    }

    private void Getdata() {
        Dataservice dataservice = APIService.getservice();
        Call<List<BaiHat>> callback = dataservice.Getfarvote(getUserIdFromSharedPreferences());
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> baiHatArrayList =(ArrayList<BaiHat>) response.body();
                farvoteAdapter = new FarvoteAdapter(getActivity(),baiHatArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(farvoteAdapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable throwable) {

            }
        });
    }
    private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }
}
