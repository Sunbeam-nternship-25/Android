package com.vaishnavidayma.studentfeedback.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class FeedbackActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView textView1,textView2,textView3,textView4;

    RadioGroup radioGroupQ1;
    RadioGroup radioGroupQ2;
    RadioGroup radioGroupQ3;
    RadioGroup radioGroupQ4;
    RadioGroup radioGroupQ5;

    EditText etComments;

    View feedbackForm;

    TextView messageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView1= findViewById(R.id.textView1);
        textView2= findViewById(R.id.textView2);
        textView3= findViewById(R.id.textView3);
        textView4= findViewById(R.id.textView4);
        radioGroupQ1= findViewById(R.id.radioGroupQ1);
        radioGroupQ2= findViewById(R.id.radioGroupQ2);
        radioGroupQ3= findViewById(R.id.radioGroupQ3);
        radioGroupQ4= findViewById(R.id.radioGroupQ4);
        radioGroupQ5= findViewById(R.id.radioGroupQ5);
        etComments = findViewById(R.id.etComments);
        feedbackForm = findViewById(R.id.feedbackForm);
        messageText= findViewById(R.id.messageText);

        checkFeedback();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            Intent intent = new Intent(FeedbackActivity.this,ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_logout) {
            SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            prefs.edit().clear().apply();  // clear saved token or user data
            Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void checkFeedback(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", null);

        if(token != null){
            RetrofitClient.getInstance().getApi().checkFeedback("Bearer "+token).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful() && response.body() != null){
                        JsonObject res = response.body();
                        if(res.get("status").getAsString().equals("success")){

                            JsonObject data = res.getAsJsonObject("data");
                            System.out.println(data);
                            boolean submitted = data.get("submitted").getAsBoolean();
                            boolean active = data.get("active").getAsBoolean();

                            System.out.println(submitted);
                            System.out.println(active);

                            if (!active){
                                feedbackForm.setVisibility(View.GONE);
                                messageText.setVisibility(View.VISIBLE);
                                messageText.setText("No active feedback schedule is available right now.");
                            }else if(submitted){
                                feedbackForm.setVisibility(View.GONE);
                                messageText.setVisibility(View.VISIBLE);
                                messageText.setText("You already submitted feedback.");

                            }else {
                                activeFeedback();
                                feedbackForm.setVisibility(View.VISIBLE);
                                messageText.setVisibility(View.GONE);


                            }
                        }


                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable throwable) {

                }
            });

        }






    }


    public void activeFeedback(){

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", null);
        Log.e("token",token);


        if (token != null) {
            RetrofitClient.getInstance().getApi().getActiveFeedback("Bearer "+token).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful() && response.body() !=null){
                        JsonObject res = response.body();

                        if(res.get("status").getAsString().equals("success")){
                            JsonArray data = res.getAsJsonArray("data");
                            if(data.size() >0){
                                JsonObject feedback = data.get(0).getAsJsonObject();

                                String moduleType = feedback.get("module_type_name").getAsString();
                                String module = feedback.get("module_name").getAsString();
                                String faculty = feedback.get("first_name").getAsString() + " " + feedback.get("last_name").getAsString();
                                String course = feedback.get("course_name").getAsString() + "(" + feedback.get("group_name").getAsString()+ ")";

                                textView1.setText(moduleType);
                                textView2.setText(module);
                                textView3.setText(faculty);
                                textView4.setText(course);
                            }
                        }
                        else {
                            Toast.makeText(FeedbackActivity.this, "No Active Feedback", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FeedbackActivity.this, "Failed to load details", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable throwable) {
                    Log.e("FeedbackAPI", "Error: ", throwable);
                    Toast.makeText(FeedbackActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}