package com.example.a4ic1.projektkoncowopolroczny.Albums;

/**
 * Created by Mirus on 14.12.2016.
 */
public class ImageListData{
    public String getUrl() {
        return url;
    }

    public String getPublicId() {
        return publicId;
    }

    private String url;

    public float getBytes() {
        return bytes;
    }

    private float bytes;
    private String publicId;
    public ImageListData(String url, String publicId) {
        this.url =url;
        this.publicId = publicId;
    }

    public ImageListData(String url, float bytes) {
        this.url = url;
        this.bytes = bytes;
    }
}
