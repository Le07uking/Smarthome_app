package com.example.sagsm.smarthomestart;

import java.util.Comparator;

/**
 * Created by sagsm on 07.05.2016.
 */
public class SortLicht implements Comparator<Raum> {
    @Override
    public int compare(Raum r1, Raum r2){
        if(r1.isLicht() && !r2.isLicht())
            return -1;
        if(!r1.isLicht() && r2.isLicht())
            return 1;
        return 0;
    }
}
