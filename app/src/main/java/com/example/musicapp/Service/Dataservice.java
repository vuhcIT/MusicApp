package com.example.musicapp.Service;

import com.example.musicapp.Model.Album;
import com.example.musicapp.Model.BaiHat;
import com.example.musicapp.Model.ChuDe;
import com.example.musicapp.Model.LuotThich;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.QuangCao;
import com.example.musicapp.Model.TheLoai;
import com.example.musicapp.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Dataservice {
    @GET("songbanner.php")
    Call<List<QuangCao>> GetDataBanner();

    @GET("playlist.php")
    Call<List<Playlist>> GetPlalistCurrentDay();

    @GET("theloai.php")
    Call<List<TheLoai>> GetTheloai();

    @GET("chude.php")
    Call<List<ChuDe>> GetChude();

    @GET("albumhot.php")
    Call<List<Album>> GetAlbum();

    @GET("baihatyeuthich.php")
    Call<List<BaiHat>> GetBaiHatYeuThich();


    @GET("danhsachcacplaylist.php")
    Call<List<Playlist>> GetDanhSachPlaylist();

    @GET("tatcaalbum.php")
    Call<List<Album>> GetTatCaAlbum();

    @GET("tatcatheloai.php")
    Call<List<TheLoai>> GetTatCaTheLoai();

    @GET("tatcachude.php")
    Call<List<ChuDe>> GetTatCaChuDe();

    @GET("login.php")
    Call<List<User>> GetUser();

    @GET("signin.php")
    Call<String> InsertUser(@Query("Username") String ID, @Query("Password") String Password);

    @GET("getfarvote.php")
    Call<List<BaiHat>> Getfarvote(@Query("ID") int ID);

    @GET("danhsachbaihat.php")
    Call<List<BaiHat>> Getdanhsachbaihattheoquangcao(@Query("IDQuangCao") int IDQuangCao);

    @GET("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachTheoPlaylist(@Query("IDPlaylist") int IDPlaylist);

    @GET("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachTheoAlbum(@Query("IDAlbum") int IDAlbum);

    @GET("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachTheoChuDe(@Query("IDChuDe") int IDChuDe);

    @GET("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachTheoTheLoai(@Query("IDTheLoai") int IDTheLoai);

    @GET("searchbaihat.php")
    Call<List<BaiHat>> GetSearchBaiHat(@Query("keyword") String keyword);

    @GET("getluotthich.php")
    Call<List<LuotThich>> GetLuotThich(@Query("ID") int ID);

    @GET("updatelike.php")
    Call<String> UpdateLike(@Query("ID") int ID, @Query("IDBaiHat") int IDBaiHat);

    @GET("unlike.php")
    Call<String> UnLike(@Query("ID") int ID, @Query("IDBaiHat") int IDBaiHat);
}
