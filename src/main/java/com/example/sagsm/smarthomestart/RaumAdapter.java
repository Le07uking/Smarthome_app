package com.example.sagsm.smarthomestart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by sagsm on 06.05.2016.
 */
public class RaumAdapter extends ArrayAdapter<String>{
    public  ArrayList<Raum> raumList;

    public RaumAdapter(Context context, String[] raume,  ArrayList<Raum> raumListe) {
        super(context, R.layout.custom_row, raume);
        this.raumList = raumListe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater raumeInflater = LayoutInflater.from(getContext());
        View customView = raumeInflater.inflate(R.layout.custom_row, parent, false);

        String itemRaumName = getItem(position);
        TextView raumName = (TextView) customView.findViewById(R.id.textViewRaum);
        ImageView lichtImage = (ImageView) customView.findViewById(R.id.imageViewLampe);
        TextView dec_temperatur = (TextView) customView.findViewById(R.id.textViewTemperatur);

        boolean lightOn = false;
        boolean heizungOn = false;
        double temperatur = 0.0;
        for(int i=0; i < raumList.size(); ++i){
            if(raumList.get(i).getRaumname().equals(itemRaumName)){
                if(raumList.get(i).isLicht())
                    lightOn = true;
                temperatur = raumList.get(i).getTemperatur();
                if(!raumList.get(i).isHeizung()){
                    heizungOn = false;
                }else if(raumList.get(i).isHeizung()){
                    heizungOn = true;
                }
            }
        }

        raumName.setText(itemRaumName);
        dec_temperatur.setText(String.valueOf(temperatur) + "°C");

        if(heizungOn) {
            if (temperatur < 20.0) {
                dec_temperatur.setTextColor(Color.rgb(0, 0, 200));
            } else dec_temperatur.setTextColor(Color.rgb(200, 0, 0));
        }else if(!heizungOn){
            dec_temperatur.setTextColor(Color.rgb(120, 120, 120));
        }
        if(lightOn) {
            lichtImage.setImageResource(R.mipmap.lampe);//hier if bedingung für lampe an/aus
        }else lichtImage.setImageResource(R.mipmap.lampeaus);

        return customView;
    }
}
