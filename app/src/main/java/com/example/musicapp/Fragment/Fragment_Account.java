package com.example.musicapp.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.musicapp.Activity.LoginActivity;
import com.example.musicapp.Activity.MainActivity;
import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Account extends Fragment {
    View view;
    Button btndansuat;
    TextView txtusername;
    ImageView img;

    ArrayList<User> manguser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account,container,false);
        btndansuat = view.findViewById(R.id.buttonLogout);
        img = view.findViewById(R.id.imageViewUserProfile);
        txtusername = view.findViewById(R.id.editTextUsername);
        Dataservice dataservice = APIService.getservice();
        Call<List<User>> callback = dataservice.GetUser();
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                manguser =(ArrayList<User>) response.body();
                for(User user : manguser){
                    if(Integer.parseInt(user.getId()) == getUserIdFromSharedPreferences()){
                        String username = user.getUsername();
                        txtusername.setText(username);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {

            }
        });
        btndansuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;
    }

    private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }
}
