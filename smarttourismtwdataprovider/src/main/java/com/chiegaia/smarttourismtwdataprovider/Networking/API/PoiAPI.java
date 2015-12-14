package com.chiegaia.smarttourismtwdataprovider.Networking.API;

import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.Activity;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.Attraction;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.County;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.FoodAndDrink;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.Theme;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.Tour;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by Hsuan-Chih Chuang on 12/3/15.
 */
public interface PoiAPI {

    @GET("/SmartTourism/county/")
    Call<List<County>> getCountyList(@Header("language") String language, @QueryMap Map<String, Object> queryParams);

    @GET("/SmartTourism/theme/{themeID}/")
    Call<Theme> getTheme(@Header("language") String language, @Path("themeID") String themeID, @QueryMap Map<String, Object> queryParams);

    @GET("/SmartTourism/theme/")
    Call<List<Theme>> getThemeList(@Header("language") String language, @QueryMap Map<String, Object> queryParams);

    @GET("/SmartTourism/activity/{activityID}")
    Call<Activity> getActivity(@Header("language") String language, @Path("activityID") String activityID, @QueryMap Map<String, Object> queryParams);

    @GET("/SmartTourism/activity/{resourceURI}")
    Call<List<Activity>> getActivityList(@Header("language") String language, @Path("resourceURI") String resourceURI, @QueryMap Map<String, Object> queryParams);

    @GET("SmartTourism/tour/{tourID}")
    Call<Tour> getTour(@Header("language") String language, @Path("tourID") String tourID, @QueryMap Map<String, Object> queryParams);

    @GET("SmartTourism/tour/{resourceURI}")
    Call<List<Tour>> getTourList(@Header("language") String language, @Path("resourceURI") String resourceURI, @QueryMap Map<String, Object> queryParams);

    @GET("SmartTourism/attraction/{attractionID}")
    Call<Attraction> getAttraction(@Header("language") String language, @Path("attractionID") String attractionID, @QueryMap Map<String, Object> queryParams);

    @GET("SmartTourism/attraction/{resourceURI}")
    Call<List<Attraction>> getAttractionList(@Header("language") String language, @Path("resourceURI") String resourceURI, @QueryMap Map<String, Object> queryParams);

    @GET("SmartTourism/foodanddrink/{foodDrinkID}")
    Call<FoodAndDrink> getFoodAndDrink(@Header("language") String language, @Path("foodDrinkID") String foodDrinkID, @QueryMap Map<String, Object> queryParams);

    @GET("SmartTourism/foodanddrink/{resourceURI}")
    Call<List<FoodAndDrink>> getFoodAndDrinkList(@Header("language") String language, @Path("resourceURI") String resourceURI, @QueryMap Map<String, Object> queryParams);

}
