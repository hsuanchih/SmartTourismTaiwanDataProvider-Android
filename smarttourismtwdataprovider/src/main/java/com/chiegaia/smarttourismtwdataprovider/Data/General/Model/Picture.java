package com.chiegaia.smarttourismtwdataprovider.Data.General.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hsuan-Chih Chuang on 12/10/15.
 */
public class Picture {

    @SerializedName("url")
    public String url;

    @SerializedName("metas")
    public PictureMeta[] metas;

    @SerializedName("description")
    public String description;
}
