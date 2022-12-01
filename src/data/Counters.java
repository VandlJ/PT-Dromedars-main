package src.data;

public class Counters {

    private int camelCount;//pocet velbloudu
    private int oasisCount;//pocet oaz
    private int pathCount;//pocet cest
    private int warehousesCount;//pocet skladu
    private int requestCount;// pocet pozadavku
    private int placesCount;//pocet skladu + pocet oaz

    public Counters(){}

    public Counters(int camelCount, int oasisCount, int pathCount, int warehousesCount, int requestCount) {
        this.camelCount = camelCount;
        this.oasisCount = oasisCount;
        this.pathCount = pathCount;
        this.warehousesCount = warehousesCount;
        this.requestCount = requestCount;
    }

    public int getCamelCount() {
        return camelCount;
    }

    public void setCamelCount(int camelCount) {
        this.camelCount = camelCount;
    }

    public int getOasisCount() {
        return oasisCount;
    }

    public void setOasisCount(int oasisCount) {
        this.oasisCount = oasisCount;
    }

    public int getPathCount() {
        return pathCount;
    }

    public void setPathCount(int pathCount) {
        this.pathCount = pathCount;
    }

    public int getWarehousesCount() {
        return warehousesCount;
    }

    public void setWarehousesCount(int warehousesCount) {
        this.warehousesCount = warehousesCount;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public int getPlacesCount(){return (this.oasisCount + this.warehousesCount);}
}