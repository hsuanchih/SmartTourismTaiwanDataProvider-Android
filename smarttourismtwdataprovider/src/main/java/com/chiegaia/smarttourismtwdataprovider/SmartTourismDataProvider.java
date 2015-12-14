package com.chiegaia.smarttourismtwdataprovider;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.Activity;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.Attraction;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.County;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.FoodAndDrink;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.Theme;
import com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model.Tour;
import com.chiegaia.smarttourismtwdataprovider.Networking.NetworkManager;
import com.chiegaia.smarttourismtwdataprovider.Networking.SmartTourismAPIContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Response;

/**
 * Created by Hsuan-Chih Chuang on 12/10/15.
 */
public class SmartTourismDataProvider {

    private static final String TAG = SmartTourismDataProvider.class.getSimpleName();

    /**
     * DataProvider singleton
     */
    private static SmartTourismDataProvider sDataProvider;

    /**
     * Reference to NetworkManager instance
     */
    private final NetworkManager mNetworkManager;


    /**
     * SmartTourismDataProvider Callback Interface implementation
     *
     * @param <T> Response data type
     */
    public interface Callback<T> {

        // Successful HTTP response.
        void onResponse(Response<T> response);

        // Invoked when a network or unexpected exception occurred during the HTTP request.
        void onFailure(Throwable t);

    }


    /**
     * Obtains the SmartTourismDataProvider singleton
     *
     * @param c The caller's context will be used to retrieve the application context
     * @return  The SmartTourismDataProvider singleton
     */
    public synchronized static SmartTourismDataProvider getInstance(Context c) {

        if (sDataProvider == null) {
            sDataProvider = new SmartTourismDataProvider(c);
        }
        return sDataProvider;
    }


    /**
     * SmartTourismDataProvider's private constructor, initializes the application context
     *
     * @param c Application context allowing the NetworkManager instance to start
     *          activities, access project resources, storage, and so forth
     */
    private SmartTourismDataProvider(Context c) {

        mNetworkManager = new NetworkManager(c);
    }


    /**
     * Private wrapper - web service request invocation
     *
     * @param resultType   Result type as defined in SmartTourismAPIContract
     * @param pathParams   Path parameters as defined by the service request
     * @param queryParams  Query parameters as defined by the service request
     * @param bodyParams   Body parameters as defined by the service request
     * @param callback     Service request callback
     */
    private void execRequestWithCallbackAndParams(
            SmartTourismAPIContract.ResultType resultType,
            String[] pathParams,
            Map<String, Object> queryParams,
            Map<String, Object> bodyParams,
            Callback callback)
    {
        mNetworkManager.execRequestWithCallbackAndParams(
                resultType,
                pathParams,
                queryParams,
                bodyParams,
                callback
        );
    }


    /**
     * Specifies the language of the information returned
     *
     * @param language  Language supported by SmartTourismAPI, as defined in SmartTourismAPIContract
     */
    public void setLanguage (SmartTourismAPIContract.SupportedLanguage language) {

        mNetworkManager.setLanguage(language);
    }



    /**
     * --------------------------------------------------------------------------------
     *                   SmartTourismAPI Interface Implementation
     * --------------------------------------------------------------------------------
     */

    /**
     * Queries a complete list of counties
     *
     * @param callback Callback method
     */
    public void getCountyList(@NonNull Callback<List<County>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.CountyList,
                null,
                null,
                null,
                callback
        );
    }

    /**
     * Queries a complete list of themes
     *
     * @param callback Callback method
     */
    public void getThemeList(@NonNull Callback<List<Theme>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.ThemeList,
                null,
                null,
                null,
                callback
        );
    }

    /**
     * Gets theme info for a particular theme ID
     *
     * @param themeID  ID for theme of interest,
     *                 A list of themeID's can be obtained via the
     *                 getThemeList(Callback<List<Theme>> callback) query
     * @param callback Callback method
     */
    public void getThemeByID(@NonNull final String themeID, @NonNull Callback<Theme> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.Theme,
                new String[]{ themeID },
                null,
                null,
                callback
        );
    }

    /**
     * Gets theme info for a particular theme ID relative to specific location
     *
     * @param themeID   ID for theme of interest,
     *                  A list of themeID's can be obtained via the
     *                  getThemeList(Callback<List<Theme>> callback) query
     * @param latitude  Latitude (Geo-coord)
     * @param longitude Longitude (Geo-coord)
     * @param callback  Callback method
     */
    public void getThemeByID(@NonNull String themeID, @NonNull final Double latitude, @NonNull final Double longitude, @NonNull Callback<Theme> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.Theme,
                new String[]{ themeID },
                new HashMap<String, Object>() {
                    {
                        put("latitude", latitude);
                        put("longitude", longitude);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Finds a particular activity by activity ID
     *
     * @param activityID ID for activty of interest,
     *                   A list of activityID's can be ontained via the
     *                   getActivityList(@NonNull Callback<List<Activity>> callback) query
     * @param callback   Callback method
     */
    public void getActivityByID(@NonNull String activityID, @NonNull Callback<Activity> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.Activity,
                new String[]{ activityID },
                null,
                null,
                callback
        );
    }

    /**
     * Queries a complete list of activities
     *
     * @param callback Callback method
     */
    public void getActivityList(@NonNull Callback<List<Activity>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.ActivityList,
                new String[]{ SmartTourismAPIContract.ResourceURI.none.stringValue() },
                null,
                null,
                callback
        );
    }

    /**
     * Gets the top ten activities
     *
     * @param callback Callback method
     */
    public void getTopTenActivities(@NonNull Callback callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.ActivityList,
                new String[] { SmartTourismAPIContract.ResourceURI.findTopTen.stringValue() },
                null,
                null,
                callback
        );
    }

    /**
     * Gets a list of activities for a county
     *
     * @param countyID ID for county of interest,
     *                 A list of countyID's can be obtained via the
     *                 getCountyList(Callback<List<County>> callback) query
     * @param callback Callback method
     */
    public void getActivitiesByCountyID(@NonNull final String countyID, @NonNull Callback<List<Activity>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.ActivityList,
                new String[] { SmartTourismAPIContract.ResourceURI.findByCounty.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("countyid", countyID);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of activities for a theme
     *
     * @param themeID  ID for theme of interest,
     *                 A list of themeID's can be obtained via the
     *                 getThemeList(Callback<List<Theme>> callback) query
     * @param callback Callback Method
     */
    public void getActivitiesByThemeID(@NonNull final String themeID, @NonNull Callback<List<Activity>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.ActivityList,
                new String[]{SmartTourismAPIContract.ResourceURI.findByTheme.stringValue()},
                new HashMap<String, Object>() {
                    {
                        put("themeid", themeID);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of activities specified by a group of criteria
     *
     * @param themeID   ID for theme of interest,
     *                  A list of themeID's can be obtained via the
     *                  getThemeList(Callback<List<Theme>> callback) query
     * @param countyID  ID for county of interest,
     *                  A list of countyID's can be obtained via the
     *                  getCountyList(Callback<List<County>> callback) query
     * @param latitude  Latitude (Geo-coord)
     * @param longitude Longitude (Geo-coord)
     * @param offset    Entry offset
     * @param count     Number of entries
     * @param callback  Callback method
     */
    public void getActivitiesByCriteria(final String themeID, final String countyID, final Double latitude, final Double longitude, final Integer offset, final Integer count, @NonNull Callback<List<Activity>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.ActivityList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByCriteria.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("themeid", themeID);
                        put("countyid", countyID);
                        put("latitude", latitude);
                        put("longitude", longitude);
                        put("offset", offset);
                        put("count", count);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of activities within a the specified geofence
     *
     * @param latitude  Latitude (Geo-coord)
     * @param longitude Longitude (Geo-coord)
     * @param distance  Radius measured from the specified Geo-coordinates in meters
     * @param callback  Callback method
     */
    public void getActivitiesInGeofence(@NonNull final Double latitude, @NonNull final Double longitude, @NonNull final Integer distance, @NonNull Callback<List<Activity>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.ActivityList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByNearBy.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("latitude", latitude);
                        put("longitude", longitude);
                        put("distance", distance);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of activities as specified by rank
     *
     * @param rank     Rank, nominal range (0-5)
     * @param callback Callback method
     */
    public void getActivitiesByRank(@NonNull final Integer rank, @NonNull Callback<List<Activity>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.ActivityList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByRank.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("rank", rank);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of activities for a specific date
     *
     * @param date     Date, string-formatted as "yyyy-MM-dd"
     * @param callback Callback method
     */
    public void getActivitiesByDate(@NonNull final String date, @NonNull Callback<List<Activity>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.ActivityList,
                new String[] { SmartTourismAPIContract.ResourceURI.findByDate.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("Date", date);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Finds a particular tour by tourID
     *
     * @param tourID   ID for tour of interest,
     *                 A list of tourID's can be obtained via the
     *                 getTourList(Callback<List<Tour>> callback) query
     * @param callback
     */
    public void getTourByID(@NonNull String tourID, @NonNull Callback<Tour> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.Tour,
                new String[]{ tourID },
                null,
                null,
                callback
        );
    }

    /**
     * Queries a list of tours
     *
     * @param callback Callback method
     */
    public void getTourList(@NonNull Callback<List<Tour>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.TourList,
                new String[] { SmartTourismAPIContract.ResourceURI.none.stringValue() },
                null,
                null,
                callback
        );
    }

    /**
     * Gets a list of tours by attraction ID
     *
     * @param attractionID Attraction ID
     * @param callback     Callback method
     */
    public void getToursByAttractionID(@NonNull final String attractionID, @NonNull Callback<List<Tour>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.TourList,
                new String[]{SmartTourismAPIContract.ResourceURI.findByAttraction.stringValue()},
                new HashMap() {
                    {
                        put("attractionid", attractionID);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of tours by theme ID
     *
     * @param themeID  Theme ID
     * @param callback Callback method
     */
    public void getToursByThemeID(@NonNull final String themeID, @NonNull Callback<List<Tour>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.TourList,
                new String[]{SmartTourismAPIContract.ResourceURI.findByTheme.stringValue()},
                new HashMap() {
                    {
                        put("themeid", themeID);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Finds a particular attraction by attraction ID
     *
     * @param attractionID Attraction ID
     * @param callback     Callback method
     */
    public void getAttractionByID(@NonNull String attractionID, @NonNull Callback<Attraction> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.Attraction,
                new String[]{ attractionID },
                null,
                null,
                callback
        );
    }

    /**
     * Gets a list of attractions by theme ID
     *
     * @param themeID  Theme ID
     * @param callback Callback method
     */
    public void getAttractionsByThemeID(@NonNull final String themeID, @NonNull Callback<List<Attraction>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.AttractionList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByTheme.stringValue() },
                new HashMap() {
                    {
                        put("themeid", themeID);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of attractions as specified by rank
     *
     * @param rank     Rank, nominal range (0-5)
     * @param callback Callback method
     */
    public void getAttractionsByRank(@NonNull final Integer rank, @NonNull Callback<List<Attraction>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.AttractionList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByRank.stringValue() },
                new HashMap() {
                    {
                        put("rank", rank);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of attractions for a county
     *
     * @param countyID ID for county of interest,
     *                 A list of countyID's can be obtained via the
     *                 getCountyList(Callback<List<County>> callback) query
     * @param callback Callback method
     */
    public void getAttractionsByCountyID(@NonNull final String countyID, @NonNull Callback<List<Attraction>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.AttractionList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByCounty.stringValue() },
                new HashMap() {
                    {
                        put("countyid", countyID);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets the top ten attractions
     *
     * @param callback Callback method
     */
    public void getTopTenAttractions(@NonNull Callback<List<Attraction>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.AttractionList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findTopTen.stringValue() },
                null,
                null,
                callback
        );
    }

    /**
     * Gets a list of attractions specified by a group of criteria
     *
     * @param themeID   ID for theme of interest,
     *                  A list of themeID's can be obtained via the
     *                  getThemeList(Callback<List<Theme>> callback) query
     * @param countyID  ID for county of interest,
     *                  A list of countyID's can be obtained via the
     *                  getCountyList(Callback<List<County>> callback) query
     * @param latitude  Latitude (Geo-coord)
     * @param longitude Longitude (Geo-coord)
     * @param offset    Entry offset
     * @param count     Number of entries
     * @param callback  Callback method
     */
    public void getAttractionsByCriteria(final String themeID, final String countyID, final Double latitude, final Double longitude, final Integer offset, final Integer count, @NonNull Callback<List<Attraction>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.AttractionList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByCriteria.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("themeid", themeID);
                        put("countyid", countyID);
                        put("latitude", latitude);
                        put("longitude", longitude);
                        put("offset", offset);
                        put("count", count);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of attractions within a the specified geofence
     *
     * @param latitude  Latitude (Geo-coord)
     * @param longitude Longitude (Geo-coord)
     * @param distance  Radius measured from the specified Geo-coordinates in meters
     * @param callback  Callback method
     */
    public void getAttractionsInGeofence(@NonNull final Double latitude, @NonNull final Double longitude, @NonNull final Integer distance, @NonNull Callback<List<Attraction>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.AttractionList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByNearBy.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("latitude", latitude);
                        put("longitude", longitude);
                        put("distance", distance);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Finds a particular food & drink venue by ID
     *
     * @param foodAndDrinkID FoodAndDrink ID
     * @param callback       Callback method
     */
    public void getFoodAndDrinkByID(@NonNull final String foodAndDrinkID, @NonNull Callback<FoodAndDrink> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.FoodAndDrink,
                new String[]{ foodAndDrinkID },
                null,
                null,
                callback
        );
    }

    /**
     * Gets a list of food & drink venues by theme ID
     *
     * @param themeID  Theme ID
     * @param callback Callback method
     */
    public void getFoodAndDrinkByThemeID(@NonNull final String themeID, @NonNull Callback<List<FoodAndDrink>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.FoodAndDrinkList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByTheme.toString() },
                new HashMap() {
                    {
                        put("themeid", themeID);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of food & drink venues by county ID
     *
     * @param countyID County ID
     * @param callback Callback method
     */
    public void getFoodAndDrinkByCountyID(@NonNull final String countyID, @NonNull Callback<List<FoodAndDrink>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.FoodAndDrinkList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByCounty.stringValue() },
                new HashMap() {
                    {
                        put("countyid", countyID);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets the top ten food & drink venues
     *
     * @param callback Callback method
     */
    public void getTopTenFoodAndDrink(@NonNull Callback<List<FoodAndDrink>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.FoodAndDrinkList,
                new String[] { SmartTourismAPIContract.ResourceURI.findTopTen.stringValue() },
                null,
                null,
                callback
        );
    }

    /**
     * Gets a list of food & drink venues specified by a group of criteria
     *
     * @param themeID   ID for theme of interest,
     *                  A list of themeID's can be obtained via the
     *                  getThemeList(Callback<List<Theme>> callback) query
     * @param countyID  ID for county of interest,
     *                  A list of countyID's can be obtained via the
     *                  getCountyList(Callback<List<County>> callback) query
     * @param latitude  Latitude (Geo-coord)
     * @param longitude Longitude (Geo-coord)
     * @param offset    Entry offset
     * @param count     Number of entries
     * @param callback  Callback method
     */
    public void getFoodAndDrinkByCriteria(final String themeID, final String countyID, final Double latitude, final Double longitude, final Integer offset, final Integer count, @NonNull Callback<List<FoodAndDrink>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.FoodAndDrinkList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByCriteria.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("themeid", themeID);
                        put("countyid", countyID);
                        put("latitude", latitude);
                        put("longitude", longitude);
                        put("offset", offset);
                        put("count", count);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of food & drink venues within a the specified geofence
     *
     * @param latitude  Latitude (Geo-coord)
     * @param longitude Longitude (Geo-coord)
     * @param distance  Radius measured from the specified Geo-coordinates in meters
     * @param callback  Callback method
     */
    public void getFoodAndDrinkInGeofence(@NonNull final Double latitude, @NonNull final Double longitude, @NonNull final Integer distance, @NonNull Callback<List<FoodAndDrink>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.FoodAndDrinkList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByNearBy.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("latitude", latitude);
                        put("longitude", longitude);
                        put("distance", distance);
                    }
                },
                null,
                callback
        );
    }

    /**
     * Gets a list of food & drink venues as specified by rank
     *
     * @param rank     Rank, nominal range (0-5)
     * @param callback Callback method
     */
    public void getFoodAndDrinkByRank(@NonNull final Integer rank, @NonNull Callback<List<FoodAndDrink>> callback) {

        execRequestWithCallbackAndParams(
                SmartTourismAPIContract.ResultType.FoodAndDrinkList,
                new String[]{ SmartTourismAPIContract.ResourceURI.findByRank.stringValue() },
                new HashMap<String, Object>() {
                    {
                        put("rank", rank);
                    }
                },
                null,
                callback
        );
    }

}
