package com.example.nn_native

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiSet {
    @FormUrlEncoded
    @POST("signup.php")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("mobile") mobile: String,
        @Field("address") address: String
    ): Call<SignupResponseModel>
}
