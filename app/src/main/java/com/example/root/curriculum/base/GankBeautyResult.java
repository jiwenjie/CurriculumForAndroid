package com.example.root.curriculum.base;

import com.example.root.curriculum.bean.WelfarePhotoInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankBeautyResult {

    public boolean error;
    public @SerializedName("results")
    List<WelfarePhotoInfo> beauties;
}
