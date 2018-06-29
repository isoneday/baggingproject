package com.teamproject.plastikproject.network;


import com.teamproject.plastikproject.model.ModelGeoCoder;
import com.teamproject.plastikproject.model.ModelKategoriMakanan;
import com.teamproject.plastikproject.model.ModelRegister;
import com.teamproject.plastikproject.model.ModelUser;
import com.teamproject.plastikproject.model.ServerResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Blackswan on 9/12/2017.
 */

public interface RestApi {



    @GET("json")
    Call<ModelGeoCoder> get_adress(
            @Query("latlng") String latlng


    );

    @FormUrlEncoded
    @POST("Users")
    Call<ModelRegister> registerUser(
            @Field("email") String stremail,
            @Field("password") String strpassword,
            @Field("is_admin") String strisadmin,
            @Field("name") String strname
    );

//    @PUT("Users/UpdateUser")
//    @FormUrlEncoded
//    Call<Post> updatePost(@Path("_id") long id,
//                          @Field("user_name") String username,
//                          @Field("email") String email,
//                          @Field("is_admin") String isadmin);


    @FormUrlEncoded
    @POST("Users/login")
    Call<ModelUser> loginUser(
            @Field("email") String stremail,
            @Field("password") String strpassword
    );

    @FormUrlEncoded
    @POST("Users/logout")
    Call<ModelUser> logout(
            @Field("access_token") String access_token
    );

    @FormUrlEncoded
    @POST("deletedatamakanan.php/")
    Call<ModelUser> deletedata(
            @Field("vsidmakanan") String stridmakanan
    );


    @GET("ambildataCarikategorimakanan.php/")
    public Call<ModelKategoriMakanan> getDataCarikategoriMakanan();

    @Multipart
    @POST("uploadmakanan1.php")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("image") RequestBody name);


}
