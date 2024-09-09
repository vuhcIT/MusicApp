package com.example.musicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LuotThich {

@SerializedName("IDUser")
@Expose
private String iDUser;
@SerializedName("IDBaiHat")
@Expose
private String iDBaiHat;

public String getIDUser() {
return iDUser;
}

public void setIDUser(String iDUser) {
this.iDUser = iDUser;
}

public String getIDBaiHat() {
return iDBaiHat;
}

public void setIDBaiHat(String iDBaiHat) {
this.iDBaiHat = iDBaiHat;
}

}