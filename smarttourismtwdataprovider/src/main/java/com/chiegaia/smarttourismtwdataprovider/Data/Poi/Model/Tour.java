package com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model;

import com.chiegaia.smarttourismtwdataprovider.Data.General.Model.Picture;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hsuan-Chih Chuang on 12/10/15.
 */
public class Tour {

    @SerializedName("collection")
    public Integer collection;

    @SerializedName("relatedid")
    public Poi[] relatedid;

    @SerializedName("multidayrelatedid")
    public Object multidayrelatedid;

    @SerializedName("shareUrl")
    public String shareUrl;

    @SerializedName("pictures")
    public Picture[] pictures;

    @SerializedName("themes")
    public String[] themes;

    @SerializedName("description")
    public String description;

    @SerializedName("name")
    public String name;

    @SerializedName("id")
    public String id;

}
