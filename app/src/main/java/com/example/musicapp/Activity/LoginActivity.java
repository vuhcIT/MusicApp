package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ImageView imglogo, imggoogle;
    EditText edtusername, edtpassword;
    Button btnlogin;
    TextView txtsocial, txtsignin;

    ArrayList<User> manguser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        Getdatauser();
    }
    private void init(){
        imglogo = findViewById(R.id.imageview_logo);
        imggoogle = findViewById(R.id.imageview_google);
        edtusername = findViewById(R.id.edit_login_username);
        edtpassword = findViewById(R.id.edit_login_password);
        btnlogin = findViewById(R.id.button_login);
        txtsignin =findViewById(R.id.textview_signin);
        txtsocial = findViewById(R.id.textview_social);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtusername.getText().toString();
                String password = edtpassword.getText().toString();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    // Thực hiện đăng nhập
                    loginUser(username, password);
                }
            }
        });
        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Getdatauser(){
        Dataservice dataservice = APIService.getservice();
        Call<List<User>> callback = dataservice.GetUser();
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                manguser =(ArrayList<User>) response.body();
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {

            }
        });
    }

    private void loginUser(String username, String password){
        boolean isAuthenticated = false;
        int userId = 0;
        for(User user : manguser){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                isAuthenticated = true;
                userId = Integer.parseInt(user.getId());
                break;
            }
        }
        if(isAuthenticated){
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            saveUserIdToSharedPreferences(userId);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            // Đăng nhập không thành công
            Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserIdToSharedPreferences(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id", userId);
        editor.apply();
    }
}