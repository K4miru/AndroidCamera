package com.example.a4ic1.projektkoncowopolroczny.Albums;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by Mirus on 06.12.2016.
 */
public class UploadFoto extends AsyncTask {

    private ProgressDialog pDialog; // kontrolka wyswietlana podczas długotrwałych operacji
    private byte[] bytes;
    private String result;

    public UploadFoto(Context context,byte[] bytes) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("komunikat");
        this.bytes = bytes;
        pDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e("heh","działa");
        HttpPost httpPost = new HttpPost("http://node.chopey.tk/banan/upload"); // URL_SERWERA proponuję zapisać w osobnej klasie np Settings w postaci stałej
        //HttpPost httpPost = new HttpPost("http://node.chopey.tk/banan/upload"); // URL_SERWERA proponuję zapisać w osobnej klasie np Settings w postaci stałej
        httpPost.setEntity(new ByteArrayEntity(bytes)); // bytes - nasze zdjęcie przekonwertowane na byte[]
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
        Log.e("heh","działa całość");
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.e("heh","Wykonuje sie:");
        Log.e("heh", " "+result.length());
        Log.e("heh", " "+result.toString());
        pDialog.dismiss();
    }
}
