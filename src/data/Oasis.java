package src.data;

public class Oasis implements Place {
    double x;//souradnice x v kartezskych souradnicich
    double y;//souradnice y v kartezskych souradnicich

    int nearestWarehouse;//TODO - nejblizsi sklad k dane oaze

    public Oasis(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getNearestWarehouse(){return nearestWarehouse;}

    public void setNearestWarehouse(int x) {this.x = nearestWarehouse;}

    @Override
    public String toString() {
        return "Oasis{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}