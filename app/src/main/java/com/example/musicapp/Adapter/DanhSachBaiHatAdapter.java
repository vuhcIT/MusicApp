package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachBaiHatAdapter extends RecyclerView.Adapter<DanhSachBaiHatAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> mangbaihat;

    public DanhSachBaiHatAdapter(Context context, ArrayList<BaiHat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danhsachbaihat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = mangbaihat.get(position);
        holder.txtcasi.setText(baiHat.getTenCaSi());
        holder.txtbaihat.setText(baiHat.getTenBaiHat());
        holder.txtindex.setText(position + 1 + "");
        getluotthich(holder, baiHat.getIDBaiHat());
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtindex,txtcasi,txtbaihat;
        ImageView imgluotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtcasi = itemView.findViewById(R.id.textviewtencasi);
            txtbaihat = itemView.findViewById(R.id.textviewtenbaihat);
            txtindex = itemView.findViewById(R.id.textviewdanhsachindex);
            imgluotthich = itemView.findViewById(R.id.imageviewcholuotthich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("music",mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });

            imgluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imgluotthich.getDrawable().getConstantState().equals(context.getResources().getDrawable(R.drawable.iconloved).getConstantState())){
                        Dataservice dataservice = APIService.getservice();
                        Call<String> callback = dataservice.UnLike(getUserIdFromSharedPreferences(), Integer.parseInt(mangbaihat.get(getPosition()).getIDBaiHat()));
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
                        Call<String> callback = dataservice.UpdateLike(getUserIdFromSharedPreferences(), Integer.parseInt(mangbaihat.get(getPosition()).getIDBaiHat()));
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

    private void getluotthich(final DanhSachBaiHatAdapter.ViewHolder holder, final String idBaiHat){
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
