package com.vaishnavidayma.studentfeedback.utils;

import com.google.gson.JsonObject;
import com.vaishnavidayma.studentfeedback.entity.Student;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    public static final String BASEURL = "http://192.168.1.100:4000";
    @POST ("/student/login")
    public Call<JsonObject> loginStudent(@Body Student student);

    @GET ("/course/allCourses")
    public Call<JsonObject> getAllCourses();


    @POST ("/student/newRegister")
    public Call<JsonObject> registerStudent(@Body Student student);


    @POST("/group/groupbycourse/course_name")
    Call<JsonObject> getGroupsByCourse(@Body JsonObject courseName);
}
