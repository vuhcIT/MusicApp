package com.example.musicapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.musicapp.Activity.DanhsachbaihatActivity;
import com.example.musicapp.Model.ChuDe;
import com.example.musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChudeAdapter extends RecyclerView.Adapter<ChudeAdapter.ViewHolder>{
    Context context;
    ArrayList<ChuDe> mangchude;

    public ChudeAdapter(Context context, ArrayList<ChuDe> mangchude) {
        this.context = context;
        this.mangchude = mangchude;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_chude,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChuDe chude = mangchude.get(position);
        holder.txttenchude.setText(chude.getTenChuDe());
        Picasso.get().load(chude.getHinhChuDe()).into(holder.imghinhchude);
    }

    @Override
    public int getItemCount() {
        return mangchude.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imghinhchude;
        TextView txttenchude;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhchude = itemView.findViewById(R.id.imageviewchude);
            txttenchude = itemView.findViewById(R.id.textviewchude);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("chude",mangchude.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
