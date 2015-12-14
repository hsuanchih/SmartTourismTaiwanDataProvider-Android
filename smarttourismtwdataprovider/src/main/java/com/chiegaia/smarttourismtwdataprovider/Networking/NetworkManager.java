package com.chiegaia.smarttourismtwdataprovider.Networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.chiegaia.smarttourismtwdataprovider.SmartTourismDataProvider;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Hsuan-Chih Chuang on 12/3/15.
 */
public class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();

    /**
     * Reference to the application context
     */
    private Context mAppContext;

    /**
     * Reference to the ConnectivityManager instance
     */
    private ConnectivityManager mConnectivityManager;

    /**
     * References to Retrofit/OKHttpClient/HttpLoggingInterceptor instances
     */
    private final Retrofit mRetrofit;
    private final OkHttpClient mHttpClient;
    private HttpLoggingInterceptor mLogger;



    /** ------------------------- SmartTourismAPI Configuration ------------------------- */
    /** API Key, application-specific */
    private final String mAppKey;

    /** API Key, common */
    private final String mApiKey = "5uHMVH0nOLku77kdJE74eyNWLKKNTNCF";

    /** Base URL, web service */
    private final String mBaseURL = "http://iiidata-prod.apigee.net";

    /**
     * API Interface arbiter, holds a reference to each of the API interface instances
     * API interface instances are created on-demand
     */
    private final HashMap<String, Object> mAPIInterfaceInstances = new HashMap<>();

    /** Language of the information to be returned */
    private SmartTourismAPIContract.SupportedLanguage mLanguage = null;
    /** ---------------------------------------------------------------------------------- */



    /**
     * NetworkManager's constructor, initializes the application context
     *
     * @param c Application context allowing the NetworkManager instance to start
     *                   activities, access project resources, storage, and so forth
     */
    public NetworkManager(Context c) {

        mAppContext = c.getApplicationContext();
        mAppKey = mAppContext.getPackageName();

        mConnectivityManager = (ConnectivityManager) mAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        mHttpClient = new OkHttpClient();
        mHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder();
                requestBuilder.addHeader("apiKey", mAppKey);
                request = requestBuilder.build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseURL)
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Assigns the language of the information returned
     *
     * @param language  Language supported by SmartTourismAPI, as defined in SmartTourismAPIContract
     */
    public void setLanguage (SmartTourismAPIContract.SupportedLanguage language) {

        mLanguage = language;
    }

    /**
     * Sets the debug mode
     *
     * @param active Debug mode active/inactive
     */
    public void setDebug (boolean active) {

        if (active) {

            if (mLogger == null) {
                mLogger = new HttpLoggingInterceptor();
                mLogger.setLevel(HttpLoggingInterceptor.Level.BASIC);
            }

            if (!mHttpClient.interceptors().contains(mLogger)) {
                mHttpClient.interceptors().add(mLogger);
            }

        } else {

            if (mLogger != null) {
                if (mHttpClient.interceptors().contains(mLogger)) {
                    mHttpClient.interceptors().remove(mLogger);
                }
            }
        }
    }

    /**
     * Web service request invocation
     *
     * @param resultType  Result type as defined in SmartTourismAPIContract
     * @param pathParams  Path parameters as defined by the service request
     * @param queryParams Query parameters as defined by the service request
     * @param bodyParams  Body parameters as defined by the service request
     * @param callback    Service request callback
     */
    public void execRequestWithCallbackAndParams(
            SmartTourismAPIContract.ResultType resultType,
            String[] pathParams,
            Map<String, Object> queryParams,
            Map<String, Object> bodyParams,
            final SmartTourismDataProvider.Callback callback) {


        if (isOnline()) {

            Object interfaceInstance = getInterfaceInstance(resultType.getAPIType());
            Object[] paramList = composeParamList(pathParams, queryParams, bodyParams);
            String apiMethodName = resultType.getMethodName();

            try {

                Method method = interfaceInstance.getClass().getMethod(apiMethodName, getParamClasses(paramList));
                try {

                    Call call = (Call) method.invoke(interfaceInstance, paramList);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(retrofit.Response response, Retrofit retrofit) {
                            callback.onResponse(response);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            callback.onFailure(t);
                        }
                    });

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Gets the active network information managed by the ConnectivityManager
     *
     * @return NetworkInfo Object
     */
    private NetworkInfo getNetworkInfo() {

        return mConnectivityManager.getActiveNetworkInfo();
    }

    /**
     * Determines whether the device is currently online
     *
     * @return Connectivity (true/false)
     */
    private boolean isOnline() {

        NetworkInfo networkInfo = getNetworkInfo();
        boolean online = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        if (!online) {
            Log.i(NetworkManager.TAG, "No network connection.");
        }
        return online;
    }

    /**
     * Constrcuts an array of parameter classes based on the parameters passed in
     *
     * @param paramList Parameter list
     * @return          Array of parameter's classes
     */
    private Class[] getParamClasses(Object[] paramList) {

        ArrayList<Class> paramClasses = new ArrayList<>();
        for (Object param : paramList) {
            //System.out.println(param.getClass().getName());
            //System.out.println(Map.class.getName());
            paramClasses.add( param instanceof String ? String.class : Map.class );
        }
        return paramClasses.toArray(new Class[0]);
    }

    /**
     * Creates and loads the API interface instance on demand
     *
     * @param apiType API type as defined by the SmartTourismAPIContract
     * @return        API interface instance
     */
    private Object getInterfaceInstance(SmartTourismAPIContract.APIType apiType) {

        String interfaceName = apiType.toString();
        Object interfaceInstance = mAPIInterfaceInstances.get(interfaceName);
        if (interfaceInstance == null) {
            try {
                Class apiInterface = Class.forName(this.getClass().getPackage().getName() + ".API." + interfaceName);
                interfaceInstance =  mRetrofit.create(apiInterface);
                mAPIInterfaceInstances.put(interfaceName, interfaceInstance);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return interfaceInstance;
    }

    /**
     * Composes the API parameter list from the various types of parameters
     *
     * @param pathParams  Path parameters
     * @param queryParams Query parameters
     * @param bodyParams  Body parameters
     * @return            API parameter list
     */
    private Object[] composeParamList(String[] pathParams, Map<String, Object> queryParams, Map<String, Object> bodyParams) {

        final String apiKey = "apikey";
        ArrayList paramList = new ArrayList();

        paramList.add(
                mLanguage != null ? mLanguage.toString():
                SmartTourismAPIContract.SupportedLanguage.zh_tw.toString()
        );

        if ( pathParams != null && pathParams.length > 0 ) {
            paramList.addAll(Arrays.asList(pathParams));
        }

        if ( queryParams != null ) {
            queryParams.put(apiKey, mApiKey);
        } else {
            queryParams = new HashMap<String, Object>() {
                {
                    put(apiKey, mApiKey);
                }
            };
        }
        paramList.add(queryParams);

        if (bodyParams != null) {
            paramList.add(bodyParams);
        }

        return paramList.toArray();
    }

}
