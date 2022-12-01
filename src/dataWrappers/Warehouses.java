package src.dataWrappers;

import src.data.Counters;
import src.data.Warehouse;

import java.util.LinkedList;

public class Warehouses {
    LinkedList<Warehouse> warehouses;//spojovy seznam skladu
    Counters counters;//pocet

    public Warehouses(LinkedList<Warehouse> warehouses, Counters counters) {
        this.counters = counters;
        this.warehouses = warehouses;
    }
    public Warehouse getWarehouseById(int pos){
        return warehouses.get(pos-1);
    }
    public Warehouse getWarehouseByPosition(int pos){
        return warehouses.get(pos);
    }
}
