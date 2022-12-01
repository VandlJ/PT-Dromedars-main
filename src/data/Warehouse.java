package src.data;

import java.util.LinkedList;

public class Warehouse implements Place{
    double x;//souradnice x v kartezskych souradnicich
    double y;//souradnice y v kartezskych souranicich
    int baskets;//pocet kosu, ktere jsou doplneny vzdy po urcitem case
    double timeToFill;//doba, za kterou jsou doplnen dany pocet kosu na sklad
    double timeToPutBasketOnCamel;//cas, za ktery se nalozi jeden kos na velblouda
    private LinkedList<CamelType> freeCamels;//spojovy seznam volnych velbloudu

    public Warehouse(double x, double y, int baskets, double timeToFill, double timeToPutBasketOnCamel) {
        this.x = x;
        this.y = y;
        this.baskets = baskets;
        this.timeToFill = timeToFill;
        this.timeToPutBasketOnCamel = timeToPutBasketOnCamel;
        this.freeCamels = new LinkedList<>();
    }

    public LinkedList<CamelType> getFreeCamels() {
        return freeCamels;
    }

    public void setFreeCamels(LinkedList<CamelType> freeCamels) {
        this.freeCamels = freeCamels;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getBaskets() {
        return baskets;
    }

    public void setBaskets(int baskets) {
        this.baskets = baskets;
    }

    public double getTimeToFill() {
        return timeToFill;
    }

    public void setTimeToFill(double timeToFill) {
        this.timeToFill = timeToFill;
    }

    public double getTimeToPutBasketOnCamel() {
        return timeToPutBasketOnCamel;
    }

    public void setTimeToPutBasketOnCamel(double timeToPutBasketOnCamel) {
        this.timeToPutBasketOnCamel = timeToPutBasketOnCamel;
    }

    @Override
    public String toString() {
        return "Warehouse{" + "x=" + x + ", y=" + y + ", baskets=" + baskets +
                ", timeToFill=" + timeToFill + ", timeToPutBasketOnCamel=" + timeToPutBasketOnCamel + '}';
    }
}