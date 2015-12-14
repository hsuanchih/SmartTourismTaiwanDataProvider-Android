package com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model;

import com.chiegaia.smarttourismtwdataprovider.Data.General.Model.Picture;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hsuan-Chih Chuang on 12/10/15.
 */
public class Activity {

    @SerializedName("id")
    public String id;

    @SerializedName("address")
    public String address;

    @SerializedName("avgrank")
    public Double avgrank;

    @SerializedName("collection")
    public Integer collection;

    @SerializedName("county")
    public String county;

    @SerializedName("currency")
    public String currency;

    @SerializedName("price")
    public Integer price;

    @SerializedName("description")
    public String description;

    @SerializedName("starttime")
    public String starttime;

    @SerializedName("endtime")
    public String endtime;

    @SerializedName("note")
    public String note;

    @SerializedName("organizer")
    public String organizer;

    @SerializedName("parking")
    public String parking;

    @SerializedName("pictures")
    public Picture[] pictures;

    @SerializedName("presenter")
    public String presenter;

    @SerializedName("location")
    public Object location;

    @SerializedName("registration")
    public String registration;

    @SerializedName("target")
    public String target;

    @SerializedName("tels")
    public String[] tels;

    @SerializedName("transport")
    public String transport;

    @SerializedName("videos")
    public String[] videos;

    @SerializedName("website")
    public String website;

    @SerializedName("themes")
    public String[] themes;

    @SerializedName("shareUrl")
    public String shareUrl;

    @SerializedName("name")
    public String name;

}
