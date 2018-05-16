package com.example.a4ic1.projektkoncowopolroczny;

import android.graphics.drawable.Drawable;

import com.example.a4ic1.projektkoncowopolroczny.Albums.ImageListData;
import com.example.a4ic1.projektkoncowopolroczny.Collage.ImageData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Mirus on 18.09.2016.
 */
public class Prefs {


    private static int X;

    public static int getY() {
        return Y;
    }

    public static void setY(int y) {
        Y = y;
    }

    public static int getX() {
        return X;
    }

    public static void setX(int x) {
        X = x;
    }

    private static int Y;
    private static File MyDir;
    private static File FolderDir;
    private static ArrayList<String> PhotoPaths;
    private static ArrayList<ImageData> ImageKolaz;
    private static String CheckedFolder="";
    public static boolean MyCheckBool=false;

    public static void setNetworkActivity(boolean networkActivity) {
        NetworkActivity = networkActivity;
    }

    public static boolean isNetworkActivity() {
        return NetworkActivity;
    }

    public static ArrayList<ImageListData> listDataArrayList;

    public static ArrayList<ImageListData> getListDataArrayList2() {
        return listDataArrayList2;
    }

    public static ArrayList<Drawable> listDrawable = new ArrayList<>();

    public static void setListDataArrayList2(ArrayList<ImageListData> listDataArrayList2) {
        Prefs.listDataArrayList2 = listDataArrayList2;
    }

    public static ArrayList<ImageListData> getListDataArrayList() {
        return listDataArrayList;
    }

    public static void setListDataArrayList(ArrayList<ImageListData> listDataArrayList) {
        Prefs.listDataArrayList = listDataArrayList;
    }

    public static ArrayList<ImageListData> listDataArrayList2;

    public static boolean NetworkActivity=false;

    public static ArrayList<ImageData> getImageKolaz() {
        return ImageKolaz;
    }

    public static void setImageKolaz(ArrayList<ImageData> image ) {
        ImageKolaz=image;
    }

    public static File getFolderDir() {
        return FolderDir;
    }

    public static void setFolderDir(String path ) {
        FolderDir=new File(MyDir, path);
    }

    public static ArrayList<String> getPhotoPaths() {
        return PhotoPaths;
    }

    public static void setPhotoPaths(ArrayList<String> paths ) {
        PhotoPaths=paths;
    }

    public static File getMyPref() {
        return MyDir;
    }

    public static void setMyPref(File dir ) {
        MyDir=dir;
    }

    public static boolean getMyCheckBool() {
        return MyCheckBool;
    }

    public static void setMyCheckBool(boolean bool ) {
        MyCheckBool=bool;
    }

    public static String getCheckedFolder() {
        return CheckedFolder;
    }

    public static void setCheckedFolder(String dir ) {
        CheckedFolder=dir;
    }
}

