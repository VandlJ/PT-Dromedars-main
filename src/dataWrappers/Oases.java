package src.dataWrappers;

import src.data.Counters;
import src.data.Oasis;

import java.util.LinkedList;

public class Oases {
    LinkedList<Oasis> oasisLinkedList;//spojovy seznam oaz
    Counters counters;//pocet
    public Oases(LinkedList<Oasis> oases, Counters counters) {
        this.counters = counters;
        this.oasisLinkedList = oases;
    }

    /**
     * Get oasis from on position in linked list
     * @param pos id of osis
     * @return Oasis
     */
    public Oasis getOasisById(int pos){
        return oasisLinkedList.get(pos-counters.getWarehousesCount()-1);
    }

    public Oasis getOasisByPosition(int pos) {return oasisLinkedList.get(pos);}
}