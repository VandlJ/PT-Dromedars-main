package src.data;

public class CamelType {
    String name;//nazev
    double minSpeed;//minimalni rychlost
    double maxSpeed;//maximalni rychlost
    double minDistance;//minimalni zvdalenost, jakou je schopny velbloud ujit
    double maxDistance;//maximalni zvdalenost, jakou je schopny velbloud ujit
    double timeToDrink;//cas, jak dlouho velbloud pije
    int maxWeight;//maximalni zatizeni velblouda
    double freq;//frekvence\pravděpodobnost s jakou se vygeneruje dany velbloud

    public CamelType(String name, double minSpeed, double maxSpeed, double minDistance, double maxDistance, double timeToDrink, int maxWeight, double freq) {
        this.name = name;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.timeToDrink = timeToDrink;
        this.maxWeight = maxWeight;
        this.freq = freq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
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

    public double getFreq() {
        return freq;
    }

    public void setFreq(double freq) {
        this.freq = freq;
    }

    @Override
    public String toString() {
        return "Camel{" +
                "name='" + name + '\'' +
                ", minSpeed=" + minSpeed +
                ", maxSpeed=" + maxSpeed +
                ", minDistance=" + minDistance +
                ", maxDistance=" + maxDistance +
                ", timeToDrink=" + timeToDrink +
                ", maxWeight=" + maxWeight +
                ", freq=" + freq +
                '}';
    }
}
