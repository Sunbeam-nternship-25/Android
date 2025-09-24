package com.vaishnavidayma.studentfeedback.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vaishnavidayma.studentfeedback.R;
import com.vaishnavidayma.studentfeedback.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {


    Toolbar toolbar;

    TextView textView1,textView2,textView3,textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        userdata();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent intent = new Intent(ProfileActivity.this, FeedbackActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_profile) {
            return true;
        }
        else if (id == R.id.action_logout) {
            SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            prefs.edit().clear().apply();  // clear saved token or user data
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void userdata(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs" ,MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token",null);
        Log.e("TOKEN",token);

        if(token !=null){
            RetrofitClient.getInstance().getApi().getStudentById("Bearer "+token).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        JsonObject res = response.body();

                        if (res.has("status") && res.get("status").getAsString().equals("success")) {
                            if (res.has("data")) {
                                if (res.get("data").isJsonArray()) {
                                    JsonArray dataArray = res.getAsJsonArray("data");
                                    if (dataArray.size() > 0) {
                                        JsonObject student = dataArray.get(0).getAsJsonObject();
                                        displayStudentData(student);
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (res.get("data").isJsonObject()) {
                                    JsonObject student = res.getAsJsonObject("data");
                                    displayStudentData(student);
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Invalid data format", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ProfileActivity.this, "No data received", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to load details", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, "Response not successful", Toast.LENGTH_SHORT).show();
                    }
                }

                // Helper method to avoid code repetition
                private void displayStudentData(JsonObject student) {
                    String name = student.get("first_name").getAsString() + " " + student.get("last_name").getAsString();
                    String prn = student.get("prn_no").getAsString();
                    String email = student.get("email").getAsString();
                    String course = student.get("course_name").getAsString() + " (" + student.get("group_name").getAsString() + ")";

                    textView1.setText(name);
                    textView2.setText(prn);
                    textView3.setText(email);
                    textView4.setText(course);
                }


                @Override
                public void onFailure(Call<JsonObject> call, Throwable throwable) {
                    Log.e("FeedbackAPI", "Error: ", throwable);
                    Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}