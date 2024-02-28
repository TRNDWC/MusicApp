package com.example.baseproject.ApiService

import com.example.baseproject.data.model.Album
import com.example.baseproject.data.model.Artist
import com.example.baseproject.data.model.ChartModel
import com.example.baseproject.data.model.TrackData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.deezer.com/")
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

    @GET("chart/")
    fun getChart(): Call<ChartModel>

    @GET("album/{id}")
    fun getAlbum(@Path("id") id: String): Call<Album>

    @GET("artist/{id}/top?limit=50")
    fun getTopTracks(@Path("id") id: String): Call<TrackData>

    @GET("search/{id}")
    fun getArtist(@Path("id") id: String): Call<Artist>
}

//package com.example.baseproject.api
//
//import com.example.baseproject.model.Data
//import com.google.gson.GsonBuilder
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.RequestBody
//import org.json.JSONObject
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.Body
//import retrofit2.http.GET
//import retrofit2.http.Header
//import retrofit2.http.POST
//
//interface ApiService {
//
//    companion object {
//        fun create(): ApiService {
//            val retrofit = Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("https://api.imgflip.com/")
//                .build()
//            return retrofit.create(ApiService::class.java)
//        }
//    }
//
//    @GET("get_memes")
//    fun getMemes(): Call<Data>
//
//}
//
//interface FCMService {
//
//    companion object {
//        fun create(): FCMService {
//            val retrofit = Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("https://fcm.googleapis.com/")
//                .build()
//            return retrofit.create(FCMService::class.java)
//        }
//    }
//
//    @POST("/fcm/send")
//    fun sendNotification(@Header("Authorization") auth: String, @Header("Content-Type") contentType: String, @Body body: RequestBody): Call<String>
//}