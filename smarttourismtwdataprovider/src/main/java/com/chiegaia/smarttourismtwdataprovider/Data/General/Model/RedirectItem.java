package com.chiegaia.smarttourismtwdataprovider.Data.General.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hsuan-Chih Chuang on 12/13/15.
 */
public class RedirectItem {

    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    @SerializedName("platformList")
    PlatformUrl[] platformList;

}
