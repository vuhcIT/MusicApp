package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.PlayMusicActivity;
import com.example.musicapp.Adapter.PlayNhacAdpter;
import com.example.musicapp.R;

public class Fragment_Play_Music extends Fragment {
    View view;
    RecyclerView recyclerViewplaynhac;

    PlayNhacAdpter playNhacAdpter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_music,container,false);
        recyclerViewplaynhac = view.findViewById(R.id.recyclerviewplaybaihat);
        if(PlayMusicActivity.mangbaihat.size() > 0){
            playNhacAdpter = new PlayNhacAdpter(getActivity(), PlayMusicActivity.mangbaihat);
            recyclerViewplaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewplaynhac.setAdapter(playNhacAdpter);
        }

        return view;
    }
}
