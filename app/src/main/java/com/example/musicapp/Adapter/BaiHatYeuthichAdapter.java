package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.PlayMusicActivity;
import com.example.musicapp.Model.BaiHat;
import com.example.musicapp.Model.LuotThich;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiHatYeuthichAdapter extends RecyclerView.Adapter<BaiHatYeuthichAdapter.ViewHolder>{

    Context context;
    ArrayList<BaiHat> baiHatArrayList;

    public BaiHatYeuthichAdapter(Context context, ArrayList<BaiHat> baiHatArrayList) {
        this.context = context;
        this.baiHatArrayList = baiHatArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_baihatyeuthich,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            BaiHat baiHat = baiHatArrayList.get(position);
            holder.txttenbaihat.setText(baiHat.getTenBaiHat());
            holder.txttencasi.setText(baiHat.getTenCaSi());
            Picasso.get().load(baiHat.getHinhBaiHat()).into(holder.imghinhbaihat);
            getluotthich(holder, baiHat.getIDBaiHat());
    }

    @Override
    public int getItemCount() {
        return baiHatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenbaihat, txttencasi;
        ImageView imghinhbaihat, imgluotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttenbaihat = itemView.findViewById(R.id.textviewbaihatyeuthich);
            txttencasi = itemView.findViewById(R.id.textviewcasibaihatyeuthch);
            imghinhbaihat = itemView.findViewById(R.id.imageviewbaihatyeuthich);
            imgluotthich = itemView.findViewById(R.id.imageviewluotthich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("music",baiHatArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });

            imgluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imgluotthich.getDrawable().getConstantState().equals(context.getResources().getDrawable(R.drawable.iconloved).getConstantState())){
                        Dataservice dataservice = APIService.getservice();
                        Call<String> callback = dataservice.UnLike(getUserIdFromSharedPreferences(), Integer.parseInt(baiHatArrayList.get(getPosition()).getIDBaiHat()));
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if(ketqua.equals("Success")){
                                    imgluotthich.setImageResource(R.drawable.iconlove);
                                    Toast.makeText(context, "Ứ còn thích nữa", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable throwable) {

                            }
                        });
                    } else{
                        Dataservice dataservice = APIService.getservice();
                        Call<String> callback = dataservice.UpdateLike(getUserIdFromSharedPreferences(), Integer.parseInt(baiHatArrayList.get(getPosition()).getIDBaiHat()));
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if(ketqua.equals("Success")){
                                    imgluotthich.setImageResource(R.drawable.iconloved);
                                    Toast.makeText(context, "Thêm danh sách bài hát yêu thích", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable throwable) {

                            }
                        });
                    }
                }
            });
        }
    }
    private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs",context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }

    private void getluotthich(final ViewHolder holder, final String idBaiHat){
        Dataservice dataservice = APIService.getservice();
        Call<List<LuotThich>> callback = dataservice.GetLuotThich(getUserIdFromSharedPreferences());
        callback.enqueue(new Callback<List<LuotThich>>() {
            @Override
            public void onResponse(Call<List<LuotThich>> call, Response<List<LuotThich>> response) {
                ArrayList<LuotThich> luotThiches =(ArrayList<LuotThich>) response.body();
                if (luotThiches != null && luotThiches.size() > 0) {
                    boolean isLiked = false;
                    for (LuotThich luotThich : luotThiches) {
                        String idBaiHatLuotThich = String.valueOf(luotThich.getIDBaiHat());
                        if (idBaiHatLuotThich.equals(idBaiHat)) {
                            isLiked = true;
                            break;
                        }
                    }
                    if (isLiked) {
                        holder.imgluotthich.setImageResource(R.drawable.iconloved);
                    } else {
                        holder.imgluotthich.setImageResource(R.drawable.iconlove);
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<LuotThich>> call, Throwable throwable) {

            }
        });
    }
}
