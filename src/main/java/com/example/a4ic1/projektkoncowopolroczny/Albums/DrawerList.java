package com.example.a4ic1.projektkoncowopolroczny.Albums;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a4ic1.projektkoncowopolroczny.MainActivity;
import com.example.a4ic1.projektkoncowopolroczny.Prefs;
import com.example.a4ic1.projektkoncowopolroczny.R;

import java.util.ArrayList;

/**
 * Created by 4ic1 on 2016-11-18.
 */
public class DrawerList extends ArrayAdapter<String> {

    ArrayList<String> lista;
    ArrayList<Integer> listaint;
    Context context2;

    public DrawerList(Context context, int resource, ArrayList<String> objects, ArrayList<Integer> ints) {
        super(context, resource, objects);
        lista = objects;
        listaint = ints;
        context2 = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.drawer_list, null);

        TextView textList = (TextView) convertView.findViewById(R.id.textList);
        textList.setText(lista.get(position));


        ImageView imageList = (ImageView) convertView.findViewById(R.id.imageList);
        imageList.setImageResource(listaint.get(position));


//

        return convertView;
    }
}
