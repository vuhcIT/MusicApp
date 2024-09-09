package com.example.musicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class User {

@SerializedName("ID")
@Expose
private String id;
@SerializedName("Username")
@Expose
private String username;
@SerializedName("Password")
@Expose
private String password;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

}