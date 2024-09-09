package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.DanhsachbaihatActivity;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {


    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    class ViewHoldel{
        TextView txttenplaylist;
        ImageView imgbackgroundplaylist, imgplaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHoldel viewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_playlist, null);
            viewholder = new ViewHoldel();
            viewholder.txttenplaylist = convertView.findViewById(R.id.textviewplaylist);
            viewholder.imgplaylist = convertView.findViewById(R.id.imageviewplaylist);
            viewholder.imgbackgroundplaylist = convertView.findViewById(R.id.imageviewbackgroundplaylist);
            convertView.setTag(viewholder);

        }else{
            viewholder =(ViewHoldel) convertView.getTag();
        }
        Playlist playlist = getItem(position);
        Picasso.get().load(playlist.getHinhNen()).into(viewholder.imgbackgroundplaylist);
        Picasso.get().load(playlist.getIcon()).into(viewholder.imgplaylist);
        viewholder.txttenplaylist.setText(playlist.getTenPlaylist());
        return convertView;
    }
}
