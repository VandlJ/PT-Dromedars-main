package src.data;

public class Request {
    double tStart;//cas prichodu pozadavku
    int oasisID;//oaza, ktera zadala dany pozadavek
    int baskets;//pocet kosu, ktery ma byt dorucen
    double finalTime;//cas, za jak dlouho od prichodu pozadavku musi byt dany pozadavek vyrizen

    public Request(double tStart, int oasisID, int baskets, double finalTime) {
        this.tStart = tStart;
        this.oasisID = oasisID;
        this.baskets = baskets;
        this.finalTime = finalTime;
    }

    public double gettStart() {
        return tStart;
    }

    public void settStart(int tStart) {
        this.tStart = tStart;
    }

    public int getOasisID() {
        return oasisID;
    }

    public void setOasisID(int oasisID) {
        this.oasisID = oasisID;
    }

    public int getBaskets() {
        return baskets;
    }

    public void setBaskets(int baskets) {
        this.baskets = baskets;
    }

    public double getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(int finalTime) {
        this.finalTime = finalTime;
    }

    @Override
    public String toString() {
        return "Request{" +
                "tStart=" + tStart +
                ", oasisID=" + oasisID +
                ", baskets=" + baskets +
                ", finalTime=" + finalTime +
                '}';
    }
}