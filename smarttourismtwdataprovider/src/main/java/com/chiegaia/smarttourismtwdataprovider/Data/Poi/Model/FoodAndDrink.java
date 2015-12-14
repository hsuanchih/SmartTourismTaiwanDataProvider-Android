package com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model;

import com.chiegaia.smarttourismtwdataprovider.Data.General.Model.Picture;
import com.chiegaia.smarttourismtwdataprovider.Data.General.Model.RedirectItem;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hsuan-Chih Chuang on 12/13/15.
 */
public class FoodAndDrink {

    @SerializedName("id")
    public String id;

    @SerializedName("themes")
    public String[] themes;

    @SerializedName("description")
    public String description;

    @SerializedName("pictures")
    public Picture[] pictures;

    @SerializedName("videos")
    public String[] videos;

    @SerializedName("redirectList")
    public RedirectItem[] redirectList;

    @SerializedName("location")
    public Object location;

    @SerializedName("tels")
    public String[] tels;

    @SerializedName("address")
    public String address;

    @SerializedName("opentime")
    public String opentime;

    @SerializedName("avgrank")
    public Double avgrank;

    @SerializedName("parking")
    public String parking;

    @SerializedName("price")
    public Integer price;

    @SerializedName("note")
    public String note;

    @SerializedName("website")
    public String website;

    @SerializedName("transport")
    public String transport;

    @SerializedName("county")
    public String county;

    @SerializedName("collection")
    public Integer collection;

    @SerializedName("shareUrl")
    public String shareUrl;

    @SerializedName("name")
    public String name;

}
