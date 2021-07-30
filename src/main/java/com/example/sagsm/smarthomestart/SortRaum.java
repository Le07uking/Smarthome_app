package com.example.sagsm.smarthomestart;

import java.util.Comparator;

/**
 * Created by sagsm on 07.05.2016.
 */
public class SortRaum  implements Comparator<Raum>{
    @Override
    public int compare(Raum r1, Raum r2){
        if(r1.getRaumname().hashCode() < r2.getRaumname().hashCode())
            return -1;
        if(r1.getRaumname().hashCode() > r2.getRaumname().hashCode())
            return 1;
        return 0;
    }
}
