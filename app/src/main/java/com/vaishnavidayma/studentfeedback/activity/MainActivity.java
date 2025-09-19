package com.vaishnavidayma.studentfeedback.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.vaishnavidayma.studentfeedback.R;
import com.vaishnavidayma.studentfeedback.entity.Student;
import com.vaishnavidayma.studentfeedback.utils.Constants;
import com.vaishnavidayma.studentfeedback.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editTextEmail,editTextPassword;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail= findViewById(R.id.editTextEmaill);
        editTextPassword= findViewById(R.id.editTextPassword);
        textView= findViewById(R.id.textView4);



    }




    public void login(View view){
        Student student = new Student();
        student.setEmail(editTextEmail.getText().toString());
        student.setPassword(editTextPassword.getText().toString());
        RetrofitClient.getInstance().getApi().loginStudent(student).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsString().equals("success")){
                    Toast.makeText(MainActivity.this, "Login SucessFully",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Log.e("LoginError", "API call failed", throwable);
                Toast.makeText(MainActivity.this, "Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}