package com.vaishnavidayma.studentfeedback.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vaishnavidayma.studentfeedback.R;

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

    }

 
}