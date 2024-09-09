package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.musicapp.Activity.DanhsachbaihatActivity;
import com.example.musicapp.Model.TheLoai;
import com.example.musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TheloaiAdapter extends RecyclerView.Adapter<TheloaiAdapter.ViewHolder>{
    Context context;
    ArrayList<TheLoai> mangtheloai;

    public TheloaiAdapter(Context context, ArrayList<TheLoai> mangtheloai) {
        this.context = context;
        this.mangtheloai = mangtheloai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_theloai,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheLoai theLoai = mangtheloai.get(position);
        holder.txttheloai.setText(theLoai.getTenTheLoai());
        Picasso.get().load(theLoai.getHinhTheLoai()).into(holder.imgtheloai);
    }

    @Override
    public int getItemCount() {
        return mangtheloai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgtheloai;
        TextView txttheloai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgtheloai = itemView.findViewById(R.id.imageviewtheloai);
            txttheloai = itemView.findViewById(R.id.textviewtheloai);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("theloai",mangtheloai.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
