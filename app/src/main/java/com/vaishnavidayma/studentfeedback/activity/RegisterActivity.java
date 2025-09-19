package com.vaishnavidayma.studentfeedback.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vaishnavidayma.studentfeedback.R;
import com.vaishnavidayma.studentfeedback.entity.Student;
import com.vaishnavidayma.studentfeedback.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {


    TextInputEditText editTextFirstName,editTextLastName,editTextEmail,editTextPassword,
            editTextConfirmPassword,editTextPRN;

    AutoCompleteTextView autoCompleteCourse,autoCompleteGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextPRN = findViewById(R.id.editTextPRN);
        autoCompleteCourse = findViewById(R.id.autoCompleteCourse);
        autoCompleteGroup = findViewById(R.id.autoCompleteGroup);

        loadCourses();

        autoCompleteCourse.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectedCourse = adapterView.getItemAtPosition(position).toString();
            loadGroups(selectedCourse);

        });

    }



    private  void loadCourses(){
        RetrofitClient.getInstance().getApi().getAllCourses().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful() && response.body() != null){
                    JsonObject json = response.body();
                    if(json.get("status").getAsString().equals("success")){
                        JsonArray data = json.getAsJsonArray("data");

                        List<String> courses =new ArrayList<>();
                        for (int i=0 ; i< data.size(); i++){
                            JsonObject obj = data.get(i).getAsJsonObject();
                            courses.add(obj.get("course_name").getAsString());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterActivity.this,
                                android.R.layout.simple_dropdown_item_1line,courses);

                        autoCompleteCourse.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Toast.makeText(RegisterActivity.this ,"Failed to load Courses", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void loadGroups(String courseName){
        Log.e("coursename" ,courseName);
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("course_name",courseName);

        RetrofitClient.getInstance().getApi().getGroupsByCourse(requestBody).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful() && response.body() != null);{
                    JsonObject json = response.body();
                    if(json.get("status").getAsString().equals("success")){
                        JsonArray data = json.getAsJsonArray("data");


                        List <String> groups = new ArrayList<>();
                        for (int i = 0 ; i< data.size (); i++){
                            JsonObject obj = data.get(i).getAsJsonObject();
                            groups.add(obj.get("group_name").getAsString());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterActivity.this,
                                android.R.layout.simple_dropdown_item_1line,groups);
                        autoCompleteGroup.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Toast.makeText(RegisterActivity.this, "Failed to load groups", Toast.LENGTH_SHORT).show();

            }
        });


}


    public void register(View view){
        Student student = new Student();
        student.setFirstName(editTextFirstName.getText().toString());
        student.setLastName(editTextLastName.getText().toString());
        student.setEmail(editTextEmail.getText().toString());
        student.setPassword(editTextPassword.getText().toString());
        student.setPrn_no(Integer.parseInt(editTextPRN.getText().toString()));
        student.setCourse_name(autoCompleteCourse.getText().toString());
        student.setGroup_name(autoCompleteGroup.getText().toString());


        RetrofitClient.getInstance().getApi().registerStudent(student).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(RegisterActivity.this,"Student Register SuccessFully" , Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Log.e("ERRError", "API call failed", throwable);
                Toast.makeText(RegisterActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }

}