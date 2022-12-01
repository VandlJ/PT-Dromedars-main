package src.data;

public class Camel {
    String name;//nazev
    double speed;//rychlost
    double distanceWithoutDrink;//cas, jak dlouho vydrzi velbloud bez nabiti
    double timeToDrink;//cas, jak dlouho velbloud pije
    int maxWeight;//maximalni zatizeni velblouda
    int currentLoad;//aktualni zatizeni velblouda

    public Camel(String name, double speed, double distanceWithoutDrink, double timeToDrink, int maxWeight, int currentLoad) {
        this.name = name;
        this.speed = speed;
        this.distanceWithoutDrink = distanceWithoutDrink;
        this.timeToDrink = timeToDrink;
        this.maxWeight = maxWeight;
        this.currentLoad = currentLoad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDistanceWithoutDrink() {
        return distanceWithoutDrink;
    }

    public void setDistanceWithoutDrink(double distanceWithoutDrink) {
        this.distanceWithoutDrink = distanceWithoutDrink;
    }

    public double getTimeToDrink() {
        return timeToDrink;
    }

    public void setTimeToDrink(int timeToDrink) {
        this.timeToDrink = timeToDrink;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int currentLoad) {
        if(currentLoad < maxWeight) {
            this.currentLoad = currentLoad;
        }else{
            try {
                throw new Exception("Cannot be loaded more than camel can take");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
