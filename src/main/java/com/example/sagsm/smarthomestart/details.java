package com.example.sagsm.smarthomestart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.graphics.Color;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class details extends AppCompatActivity {
    private Raum raum;
    public static final int RESULT_OK = 2;
    public ImageButton buttonLicht;
    public ImageButton buttonHeizung;
    private boolean lichtStatus = false;
    private boolean heizungStatus = false;
    public SeekBar seekBarControl;
    public TextView TextViewSeekValue;
    private boolean heizungOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getApplicationContext(), "OnCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_details);

        //Buttons für Heizung und Licht initialisieren und Weißen Hintergrund geben
        buttonLicht = (ImageButton) findViewById(R.id.imageButtonLicht);
        buttonHeizung = (ImageButton) findViewById(R.id.imageButtonHeizung);
        buttonHeizung.setBackgroundColor(Color.WHITE);
        buttonLicht.setBackgroundColor(Color.WHITE);

        //Raumobjekt von MainActivity Initialisieren
        Intent intent = getIntent();
        if(intent == null){return;}
        raum = (Raum) intent.getSerializableExtra("RaumDatenSenden");

        //Raumname im Fenstertitel anzeigen
        setTitle(raum.getRaumname());

        final TextView raumtext = (TextView) findViewById(R.id.textViewTest);
        String RaumInfo = "Einstellungen:";
        raumtext.setText(RaumInfo);

        //Initialisieren der Licht und Heizungs Buttons
        if(raum.isLicht()){
            buttonLicht.setImageResource(R.mipmap.lampean150);
            lichtStatus = true;
        }else {
            buttonLicht.setImageResource(R.mipmap.lampeaus150);
            lichtStatus = false;}
        if(raum.isHeizung()){
            buttonHeizung.setImageResource(R.mipmap.heizungan150);
            heizungStatus = true;
        }else {
            buttonHeizung.setImageResource(R.mipmap.heizungaus150);
            heizungStatus = false;}

        //Seekbar für Temperatur Einstellen, Anzeigen und abspeichern
        seekBarControl = (SeekBar) findViewById(R.id.seekBarTemperatur);
        TextViewSeekValue = (TextView) findViewById(R.id.textViewTempValue);

        setSeekbarEnable();

        double progressValue = raum.getTemperatur()*10; //für anzeige im dezimal bereich
        seekBarControl.setProgress(((int)progressValue)-140);  //Seekbar 0 - 260
        TextViewSeekValue.setText(String.valueOf(raum.getTemperatur()));

        //Farbeinstellung für Temperaturanzeige fals heizung = AN
        heizungOn = raum.isHeizung();
        if(heizungOn) {
            if (progressValue < 200) {
                TextViewSeekValue.setTextColor(Color.rgb(0, 0, 200));
            } else TextViewSeekValue.setTextColor(Color.rgb(200, 0, 0));
        }

        //Seekbar Listener
        seekBarControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            double newProgressValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                newProgressValue =(((double)progress/10.0)+14.0);  //für anzeige im decimal bereich
                TextViewSeekValue.setText(String.valueOf(newProgressValue) + "°C");
                if(newProgressValue < 20.0){
                    TextViewSeekValue.setTextColor(Color.rgb(0, 0, 200));
                    //seekBarControl.setDrawingCacheBackgroundColor(Color.rgb(0,0,200));
                    //seekBarControl.setBackgroundColor(Color.rgb(0, 0, 200));
                }else {
                    TextViewSeekValue.setTextColor(Color.rgb(200, 0, 0));
                    //seekBarControl.setBackgroundColor(Color.rgb(200, 0, 0));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                raum.setTemperatur(newProgressValue);  //Neuer Wert bei Stop speichern
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Toast.makeText(getApplicationContext(), "OnResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(getApplicationContext(), "OnStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //Toast.makeText(getApplicationContext(), "OnPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Toast.makeText(getApplicationContext(), "OnStop", Toast.LENGTH_SHORT).show();
    }

    public void backOnClick(View view){
        Intent intentAntwort = new Intent();
        Bundle extras = new Bundle();
        extras.putString("resultValueRaum", raum.getRaumname());
        extras.putBoolean("resultValueLicht", raum.isLicht());
        extras.putBoolean("resultValueHeizung", raum.isHeizung());
        extras.putDouble("resultValueTemperatur", raum.getTemperatur());
        intentAntwort.putExtras(extras);
        setResult(RESULT_OK, intentAntwort);
        finish();
    }

    public void onClickLampe(View view){
        if(lichtStatus){
            raum.setLicht(false);
            buttonLicht.setImageResource(R.mipmap.lampeaus150);
            lichtStatus = false;
        }else{
            raum.setLicht(true);
            buttonLicht.setImageResource(R.mipmap.lampean150);
            lichtStatus = true;
        }
    }

    public void onClickHeizung(View view){
        if(heizungStatus){
            raum.setHeizung(false);
            buttonHeizung.setImageResource(R.mipmap.heizungaus150);
            heizungStatus = false;

        }else{
            raum.setHeizung(true);
            buttonHeizung.setImageResource(R.mipmap.heizungan150);
            heizungStatus = true;
            if(raum.getTemperatur() < 20.0){
                TextViewSeekValue.setTextColor(Color.rgb(0, 0, 200));
            }else TextViewSeekValue.setTextColor(Color.rgb(200, 0, 0));
        }
        setSeekbarEnable();
    }

    public void setSeekbarEnable(){
        if(!heizungStatus){
            seekBarControl.setEnabled(false);
            TextViewSeekValue.setEnabled(false);
            TextViewSeekValue.setTextColor(Color.rgb(120, 120, 120));
            heizungOn = false;
        }else if(heizungStatus){
            seekBarControl.setEnabled(true);
            TextViewSeekValue.setEnabled(true);}
    }
}
