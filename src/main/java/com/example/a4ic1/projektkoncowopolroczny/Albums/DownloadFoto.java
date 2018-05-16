package com.example.a4ic1.projektkoncowopolroczny.Albums;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.a4ic1.projektkoncowopolroczny.Prefs;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mirus on 14.12.2016.
 */
public class DownloadFoto extends AsyncTask {
    private String result;
    private JSONArray allImagesJson = null; //obiekt JSONArray
    private ArrayList<ImageListData> lista = new ArrayList<>();
    private ArrayList<ImageListData> lista2 = new ArrayList<>();
    private LinearLayout viewPager;

    public DownloadFoto(LinearLayout viewPager) {
        this.viewPager=viewPager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {

        HttpPost httpPost = new HttpPost("http://node.chopey.tk/banan/download"); // URL_SERWERA proponuję zapisać w osobnej klasie np Settings w postaci stałej
        DefaultHttpClient httpClient = new DefaultHttpClient(); // klient http
        HttpResponse httpResponse = null; // obiekt odpowiedzi z serwera

        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            Log.e("heh",e.toString());
        }

        try {
            result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        } catch (IOException e) {
            Log.e("heh",e.toString());
        }

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("heh",e.toString());
        }

        try {
            allImagesJson = jsonObj.getJSONArray("resources");
            for (int i = 0; i < allImagesJson.length(); i++) {
                JSONObject object = allImagesJson.getJSONObject(i);
                String url = object.getString("url");
                String publicId = object.getString("public_id");
                float size = Float.parseFloat(object.getString("bytes"));
                if(publicId.charAt(0)=='m') {
                    lista.add(new ImageListData(url, publicId));
                    int j=lista.size()-1;
                    Log.e("heh","To jest lista: "+lista.get(j).getUrl()+" "+lista.get(j).getPublicId());
                }else if(publicId.charAt(0)=='l'){
                    lista2.add(new ImageListData(url,size));
                    int j=lista2.size()-1;
                    Log.e("heh","To jest lista2: "+lista2.get(j).getUrl()+" "+lista2.get(j).getBytes());
                }
            }
        } catch (JSONException e) {

            Log.e("heh",e.toString());
        }
        Log.e("heh","działa całość down");
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.e("heh",""+result.length());
        Log.e("heh","działa całość down: "+ result);
        for (int i = 0; i < lista.size(); i++) {
            new LoadImages(viewPager).execute(lista.get(i).getUrl(),lista.get(i).getPublicId(),lista2.get(i).getUrl(),lista2.get(i).getBytes());
        }
        Prefs.setListDataArrayList(lista);
        Prefs.setListDataArrayList2(lista2);
    }


}
