package com.vaishnavidayma.studentfeedback.utils;

import com.google.gson.JsonObject;
import com.vaishnavidayma.studentfeedback.entity.Student;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    public static final String BASEURL = "http://192.168.1.102:4000";
    @POST ("/student/login")
    public Call<JsonObject> loginStudent(@Body Student student);

    @POST ("/student/newRegister")
    public Call<JsonObject> registerStudent(@Body Student student);

    @GET ("/course/allCourses")
    public Call<JsonObject> getAllCourses();

    @POST("/group/groupbycourse/course_name")
    Call<JsonObject> getGroupsByCourse(@Body JsonObject courseName);

    @GET("/feedback/activeFeedback")
    Call<JsonObject> getActiveFeedback(
            @Header("Authorization") String token
    );


    @POST("/feedback/fillFeedback2")
    Call<JsonObject> submitFeedback(@Header("Authorization") String token, @Body JsonObject body);

    @GET("/feedback/checkFeedback2")
    Call<JsonObject> checkFeedback(@Header("Authorization") String token);

    @GET("/student/studentbyid")
    Call<JsonObject> getStudentById(@Header("Authorization") String token);


}
