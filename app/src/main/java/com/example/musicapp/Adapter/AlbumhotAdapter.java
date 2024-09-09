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
import com.example.musicapp.Model.Album;
import com.example.musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumhotAdapter extends RecyclerView.Adapter<AlbumhotAdapter.ViewHolder>{
    Context context;
    ArrayList<Album> mangalbum;

    public AlbumhotAdapter(Context context, ArrayList<Album> mangalbum) {
        this.context = context;
        this.mangalbum = mangalbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_albumhot,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = mangalbum.get(position);
        holder.txttenalbum.setText(album.getTenAlbum());
        holder.txttencasialbum.setText(album.getTenCaSi());
        Picasso.get().load(album.getHinhAlbum()).into(holder.imghinhalbum);
    }

    @Override
    public int getItemCount() {
        return mangalbum.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imghinhalbum;
        TextView txttenalbum, txttencasialbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhalbum = itemView.findViewById(R.id.imageviewalbum);
            txttenalbum = itemView.findViewById(R.id.textviewalbum);
            txttencasialbum = itemView.findViewById(R.id.textviewtencasialbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("album",mangalbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
