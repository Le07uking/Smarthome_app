package com.example.sagsm.smarthomestart;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.support.v7.view.menu.MenuItemImpl;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    public static ArrayList<Raum> raumListe = new ArrayList<Raum>();
    public static final int RESULT_OK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getApplicationContext(), "OnCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        raumListe.clear();
        raumListe.add(new Raum("Küche", true, true, 18.5));
        raumListe.add(new Raum("Wohnzimmer", true, true, 22.5));
        raumListe.add(new Raum("Schlafen Eltern", false, true, 16.3));
        raumListe.add(new Raum("Schlafen Kinder", false, true, 22.5));
        raumListe.add(new Raum("Badezimmer", true, false, 22.5));
        //Toast.makeText(getApplicationContext(), "Raume erstellt", Toast.LENGTH_SHORT).show();
        displayList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //RelativeLayout main_view = (RelativeLayout) findViewById(R.id.ma);
        switch(item.getItemId()){
            case R.id.menu_sortieren_licht:{
                //Toast.makeText(getApplicationContext(), "licht sortieren", Toast.LENGTH_SHORT).show();
                Collections.sort(raumListe, new SortLicht());
                break;
            }
            case R.id.menu_sortieren_name:{
                Collections.sort(raumListe, new SortRaum());
                break;
            }
            case R.id.menu_lichtaus:{
                for(int i=0; i < raumListe.size(); ++i){
                    raumListe.get(i).setLicht(false);
                }break;
            }
            case R.id.menu_lichtan:{
                for(int i=0; i < raumListe.size(); ++i){
                    raumListe.get(i).setLicht(true);
                }break;
            }
            default: break;
        }
        displayList();
        return true;
    }

    public void displayList(){
        String[] myStringArray = new String[raumListe.size()];
        for(int i=0; i<raumListe.size(); ++i)
            myStringArray[i] = raumListe.get(i).getRaumname();


        ListAdapter myAdapter = new RaumAdapter(this, myStringArray, raumListe);
        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setAdapter(myAdapter);

        myList.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String aktuellRaum = String.valueOf(parent.getItemAtPosition(position));
                        // Toast.makeText(MainActivity.this, aktuellRaum, Toast.LENGTH_SHORT).show();

                        Intent intentSenden = new Intent(MainActivity.this, details.class);
                        for(int i=0; i < raumListe.size(); ++i){
                            if(raumListe.get(i).getRaumname().equals(aktuellRaum)){
                                intentSenden.putExtra("RaumDatenSenden", raumListe.get(i));
                                startActivityForResult(intentSenden, RESULT_OK);
                                //startActivity(intent);
                            }
                        }
                    }
                }
        );
    }

    public static void addRaum(){
        //Test Beispiel
        String name = "TestRaum";
        boolean licht = true;
        boolean heizung = true;
        double tempe = 25.0;
        raumListe.add(new Raum(name, licht, heizung, tempe));
    }

    @Override
    public void onClick(View v) {}

    @Override
    protected void onResume(){
        super.onResume();
        //Toast.makeText(getApplicationContext(), "OnResume", Toast.LENGTH_SHORT).show();
        displayList();
    }


    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(getApplicationContext(), "OnStart", Toast.LENGTH_SHORT).show();
       //addRaum();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){
                Bundle extras = data.getExtras();
                String raumName = data.getStringExtra("resultValueRaum");
                boolean newLicht = data.getBooleanExtra("resultValueLicht", false);
                boolean newHeizung = data.getBooleanExtra("resultValueHeizung", false);
                double newTemperatur = data.getDoubleExtra("resultValueTemperatur", 20.0);

                // Serializable tmp = data.getSerializableExtra("resultValue");
                //Raum newRaumValue = (Raum) data.getParcelableExtra("resultValue");
                //Raum raum = (Raum) getIntent().getSerializableExtra("result");
                //Toast.makeText(getApplicationContext(), String.valueOf(raum.getTemperatur()), Toast.LENGTH_LONG).show();
                for (int i = 0; i < raumListe.size(); ++i) {
                    if(raumListe.get(i).getRaumname().equals(raumName)){
                        raumListe.get(i).setLicht(newLicht);
                        raumListe.get(i).setHeizung(newHeizung);
                        raumListe.get(i).setTemperatur(newTemperatur);
                     }
                }
        }
    }
}
