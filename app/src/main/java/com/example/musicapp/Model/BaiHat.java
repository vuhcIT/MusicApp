package com.example.musicapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaiHat implements Parcelable {

@SerializedName("IDBaiHat")
@Expose
private String iDBaiHat;
@SerializedName("TenBaiHat")
@Expose
private String tenBaiHat;
@SerializedName("HinhBaiHat")
@Expose
private String hinhBaiHat;
@SerializedName("TenCaSi")
@Expose
private String tenCaSi;
@SerializedName("Link")
@Expose
private String link;
@SerializedName("Like")
@Expose
private String like;

    protected BaiHat(Parcel in) {
        iDBaiHat = in.readString();
        tenBaiHat = in.readString();
        hinhBaiHat = in.readString();
        tenCaSi = in.readString();
        link = in.readString();
        like = in.readString();
    }

    public static final Creator<BaiHat> CREATOR = new Creator<BaiHat>() {
        @Override
        public BaiHat createFromParcel(Parcel in) {
            return new BaiHat(in);
        }

        @Override
        public BaiHat[] newArray(int size) {
            return new BaiHat[size];
        }
    };

    public String getIDBaiHat() {
return iDBaiHat;
}

public void setIDBaiHat(String iDBaiHat) {
this.iDBaiHat = iDBaiHat;
}

public String getTenBaiHat() {
return tenBaiHat;
}

public void setTenBaiHat(String tenBaiHat) {
this.tenBaiHat = tenBaiHat;
}

public String getHinhBaiHat() {
return hinhBaiHat;
}

public void setHinhBaiHat(String hinhBaiHat) {
this.hinhBaiHat = hinhBaiHat;
}

public String getTenCaSi() {
return tenCaSi;
}

public void setTenCaSi(String tenCaSi) {
this.tenCaSi = tenCaSi;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

public String getLike() {
return like;
}

public void setLike(String like) {
this.like = like;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(iDBaiHat);
        dest.writeString(tenBaiHat);
        dest.writeString(hinhBaiHat);
        dest.writeString(tenCaSi);
        dest.writeString(link);
        dest.writeString(like);
    }
}