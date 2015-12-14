package com.chiegaia.smarttourismtwdataprovider.Data.Poi.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hsuan-Chih Chuang on 12/10/15.
 */
public class Poi {

    @SerializedName("id")
    public String id;

    @SerializedName("type")
    public String type;

    @SerializedName("detail")
    public Object detail;

}
