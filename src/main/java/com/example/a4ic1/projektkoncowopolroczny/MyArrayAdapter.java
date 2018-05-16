package com.example.a4ic1.projektkoncowopolroczny;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by 4ic1 on 2016-09-16.
 */
public class MyArrayAdapter extends ArrayAdapter<String> {

    ArrayList<String>lista;
    Context context2;

    public MyArrayAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        lista = objects;
        context2 = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //inflater - klasa konwertująca xml na kod javy
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.camerapliki, null);
        //szukam kontrolki w layoucie

        TextView tv1 = (TextView) convertView.findViewById(R.id.text3);
        tv1.setText(lista.get(position));


        ImageView iv1 = (ImageView) convertView.findViewById(R.id.imageViewDel);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context2);
                alert.setTitle("Uwaga!");
                alert.setMessage("Czy chcesz usunąć folder z jego zawartością?");
//ok
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        MainActivity my = new MainActivity();
                        my.DeleteFolder(lista.get(position));
                        Prefs.setCheckedFolder("");
                    }

                });

//no
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                    }
                });
//
                alert.show();

                //((CameraActivity)getContext()).tost(lista.get(position));
                //((MainActivity)getContext()).DeleteFolder();
            }
        });

        return convertView;
    }
}
