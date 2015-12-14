package com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model;

import com.chiegaia.smarttourismtwdataprovider.Data.General.Model.Picture;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hsuan-Chih Chuang on 12/4/15.
 */
public class Theme {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("pictures")
    public Picture[] pictures;

    @SerializedName("poiList")
    public Poi[] poiList;

    @SerializedName("subthemeList")
    public Theme[] subthemeList;

    @SerializedName("type")
    public Integer type;

}
