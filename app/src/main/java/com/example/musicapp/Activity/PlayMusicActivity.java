package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.example.musicapp.Adapter.ViewParperPlayNhac;

import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Fragment.Fragment_DiaNhac;
import com.example.musicapp.Fragment.Fragment_Play_Music;
import com.example.musicapp.Model.BaiHat;
import com.example.musicapp.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayMusicActivity extends AppCompatActivity {
    Toolbar toolbarplaysong;
    TextView txttimesong, txtotaltimesong;
    SeekBar sktime;
    ImageButton imgplay, imgpreview, imgnext, imgrepeat, imgrandom;
    ViewPager viewPagerplaysong;
    Fragment_DiaNhac fragment_diaNhac;
    Fragment_Play_Music fragment_play_music;

    MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean next = false;

    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();

    public static ViewParperPlayNhac adapternhac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intent = getIntent();
        mangbaihat.clear();
        if (intent != null) {
            if (intent.hasExtra("music")) {
                BaiHat baiHat = intent.getParcelableExtra("music");
                mangbaihat.add(baiHat);
                Toast.makeText(this, baiHat.getTenBaiHat(), Toast.LENGTH_SHORT).show();
            }
            if (intent.hasExtra("cacbaihat")) {
                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat = baiHatArrayList;
            }
        }
        GetDataFromIntent();
        init();
        evenClick();
    }

    private void evenClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapternhac.getItem(1) != null){
                    if(mangbaihat.size() > 0){
                        fragment_diaNhac.Playnhac(mangbaihat.get(0).getHinhBaiHat());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this,300);
                    }
                }
            }
        }, 500);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.iconplay);
                } else {
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.iconpause);
                }
            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false){
                    if(checkrandom == true){
                        checkrandom = false;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                        imgrandom.setImageResource(R.drawable.iconsuffle);
                    }
                    imgrepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                } else {
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                    repeat=false;
                }
            }
        });
        imgrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkrandom == false){
                    if(repeat == true){
                        repeat = false;
                        imgrandom.setImageResource(R.drawable.iconshuffled);
                        imgrepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    imgrandom.setImageResource(R.drawable.iconshuffled);
                    checkrandom = true;
                } else {
                    imgrandom.setImageResource(R.drawable.iconsuffle);
                    checkrandom=false;
                }
            }
        });
        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mangbaihat.size()>0){
                    if(mediaPlayer.isPlaying() || mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(position < (mangbaihat.size())){
                        imgplay.setImageResource(R.drawable.iconpause);
                        position++;
                        if(repeat == true){
                            if(position == 0){
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if(index == position){
                                position = index -1;
                            }
                            position = index;
                        }
                        if(position > mangbaihat.size()-1){
                            position = 0;
                        }
                        new PlayMP3().execute(mangbaihat.get(position).getLink());
                        fragment_diaNhac.Playnhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        UpdateTime();
                    }
                }
                imgpreview.setClickable(false);
                imgnext.setClickable(false);
                Handler handlernext = new Handler();
                handlernext.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpreview.setClickable(true);
                        imgnext.setClickable(true);
                    }
                },5000);
            }
        });
        // bat su kien khi click vao nut preview
        imgpreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mangbaihat.size()>0){
                    if(mediaPlayer.isPlaying() || mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(position < (mangbaihat.size())){
                        imgplay.setImageResource(R.drawable.iconpause);
                        position--;
                        if(position < 0){
                            position = mangbaihat.size() -1;
                        }
                        if(repeat == true){
                            position += 1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if(index == position){
                                position = index -1;
                            }
                            position = index;
                        }
                        new PlayMP3().execute(mangbaihat.get(position).getLink());
                        fragment_diaNhac.Playnhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        UpdateTime();
                    }
                }
                imgpreview.setClickable(false);
                imgnext.setClickable(false);
                Handler handlernext = new Handler();
                handlernext.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpreview.setClickable(true);
                        imgnext.setClickable(true);
                    }
                },5000);
            }
        });
    }

    private void GetDataFromIntent() {

    }

    private void init(){
        toolbarplaysong = findViewById(R.id.toolbarplaymusic);
        txttimesong = findViewById(R.id.textviewtimesong);
        txtotaltimesong = findViewById(R.id.textviewtotaltimesong);
        sktime = findViewById(R.id.seekbarsong);
        imgplay = findViewById(R.id.imagebuttonplay);
        imgnext = findViewById(R.id.imagebuttonnext);
        imgpreview = findViewById(R.id.imagebuttonpreview);
        imgrandom = findViewById(R.id.imagebuttonsuffle);
        imgrepeat = findViewById(R.id.imagebuttonrepeat);
        viewPagerplaysong = findViewById(R.id.viewPagerplaymusic);
        setSupportActionBar(toolbarplaysong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaysong.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangbaihat.clear();
            }
        });
        toolbarplaysong.setTitleTextColor(Color.WHITE);
        fragment_diaNhac = new Fragment_DiaNhac();
        fragment_play_music = new Fragment_Play_Music();
        adapternhac = new ViewParperPlayNhac(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_play_music);
        adapternhac.AddFragment(fragment_diaNhac);
        viewPagerplaysong.setAdapter(adapternhac);
        fragment_diaNhac = (Fragment_DiaNhac) adapternhac.getItem(1);
        if(mangbaihat.size()>0){
            getSupportActionBar().setTitle(mangbaihat.get(0).getTenBaiHat());
            new PlayMP3().execute(mangbaihat.get(0).getLink());
            imgplay.setImageResource(R.drawable.iconpause);
        }
    }

    public class PlayMP3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp)
                    {
                        mediaPlayer.start();
                        TimeSong();
                        UpdateTime();
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void TimeSong() {
        if (mediaPlayer != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
            txtotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
            sktime.setMax(mediaPlayer.getDuration());
        }
    }
    private  void UpdateTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    sktime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txttimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        },300);
        Handler handlertime = new Handler();
        handlertime.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next == true){
                    if(position < (mangbaihat.size())){
                        imgplay.setImageResource(R.drawable.iconpause);
                        position++;
                        if(repeat == true){
                            if(position == 0){
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if(index == position){
                                position = index -1;
                            }
                            position = index;
                        }
                        if(position > mangbaihat.size()-1){
                            position = 0;
                        }
                        new PlayMP3().execute(mangbaihat.get(position).getLink());
                        fragment_diaNhac.Playnhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                    }
                imgpreview.setClickable(false);
                imgnext.setClickable(false);
                Handler handlernext = new Handler();
                handlernext.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpreview.setClickable(true);
                        imgnext.setClickable(true);
                    }
                },5000);
                next = false;
                handlertime.removeCallbacks(this);
                }else {
                    handlertime.postDelayed(this,1000);
                }
            }
        },1000);
    }

}