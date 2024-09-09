package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SigninActivity extends AppCompatActivity {
    ImageView imglogo;
    EditText edtusername, edtpassword, edtretypepassword;
    Button btnsigngin;
    TextView  txtlogin;
    ArrayList<User> userArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        init();
    }

    private void init(){
        imglogo = findViewById(R.id.imageview_logosignin);
        edtusername = findViewById(R.id.edit_signin_username);
        edtpassword = findViewById(R.id.edit_signin_password);
        edtretypepassword = findViewById(R.id.edit_signin_password_replace);
        btnsigngin = findViewById(R.id.button_login);
        txtlogin = findViewById(R.id.textview_lognin);

        btnsigngin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtusername.getText().toString();
                String password = edtpassword.getText().toString();
                String retype = edtretypepassword.getText().toString();
                if(username.isEmpty() || password.isEmpty() || retype.isEmpty()){
                    Toast.makeText(SigninActivity.this, "Vui lòng nhập đủ tên đăng nhập, mật khẩu và mật khẩu nhắc lại", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(retype)) {
                    Toast.makeText(SigninActivity.this, "Mật khẩu và mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
                }
                else {
                    Dataservice dataservice = APIService.getservice();
                    Call<String> callback = dataservice.InsertUser(username,password);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            if(result.equals("checkedfalse")){
                                Toast.makeText(SigninActivity.this, "Tên người dùng đã tồn tại, vui lòng đổi tên", Toast.LENGTH_SHORT).show();
                            } else if (result.equals("Success")) {
                                Toast.makeText(SigninActivity.this, "Đăng kí thành công chuyển tới trang đăng nhập", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SigninActivity.this, "Đăng kí thất bại vui lòng làm lại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable throwable) {

                        }
                    });
                }
            }
        });
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}