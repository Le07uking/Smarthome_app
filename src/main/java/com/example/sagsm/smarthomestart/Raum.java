package com.example.sagsm.smarthomestart;

import java.io.Serializable;

/**
 * Created by sagsm on 06.05.2016.
 */
public class Raum implements Serializable{
    private String raumname;
    private boolean licht;
    private boolean heizung;
    private double temperatur;

    public Raum(String raumname, boolean licht, boolean heizung, double temperatur) {
        this.raumname = raumname;
        this.licht = licht;
        this.heizung = heizung;
        this.temperatur = temperatur;
    }

    //Setter Methoden:
    public void setRaumname(String raumname) {
        this.raumname = raumname;}
    public void setTemperatur(double temperatur) {
        this.temperatur = temperatur;}
    public void setHeizung(boolean heizung) {
        this.heizung = heizung;}
    public void setLicht(boolean licht) {
        this.licht = licht;}

    //Getter Methoden:
    public String getRaumname() {
        return raumname;}
    public boolean isLicht() {
        return licht;}
    public boolean isHeizung() {
        return heizung;}
    public double getTemperatur() {
        return temperatur;}
}
