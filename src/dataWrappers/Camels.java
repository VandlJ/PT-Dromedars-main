package src.dataWrappers;

import src.data.Camel;
import src.data.CamelType;
import src.data.Counters;

import java.util.LinkedList;
import java.util.Random;

public class Camels {
    Counters counters;//pocet
    LinkedList<CamelType> camels;//spojovy seznam velbloudu
    Double[] camelsPpt;//seznam pravdepodobnosti danych druhu velbloudu
    
    public Camels(LinkedList<CamelType> camels, Counters counters) {
        this.camels = camels;
        this.counters = counters;
        setUp();
    }

    public LinkedList<CamelType> getCamels() {
        return camels;
    }

    public CamelType getCamelByPos(int i){
        return camels.get(i);
    }

    private void setUp() {
        camelsPpt = new Double[camels.size()];//nove pole doublu, velikost poctu velbloudu, pole seznamu ppst
        //pokud mam ppst velbloudu 0.3, 0.3 a 0.4, pak jednotlive bunky pole jsou 0.3, 0.6, 1 - to je vyplneno nize
        for(int i = 0; i < camels.size(); i++) {
            if(i == 0){
                camelsPpt[i] = camels.get(i).getFreq();
            }
            else {
                camelsPpt[i] = camelsPpt[i - 1] + camels.get(i).getFreq();
            }
        }
        makeCheck();
    }

    private void makeCheck() {//kontroluje, zda je soucet vsech ppst 1
        double ppt = 0;
        double eps = 0.001;
        ppt = camelsPpt[camelsPpt.length-1];
        if(ppt > (1+eps) || ppt < (1-eps)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Probability of camels is not correctly set up");
                System.exit(-1);
            }
        }
    }

    public Camel generateCamel(int warehousePos){//generator velblouda
        Random r = new Random();
        CamelType newCamelType = null;
        double camelType = r.nextDouble();
        for (int i = 0; i < camelsPpt.length; i++){
            if(camelsPpt[i] >= camelType){
                newCamelType = camels.get(i);
                break;
            }
        }
        if(newCamelType != null) {
            Camel c = new Camel(newCamelType.getName(), getSpeed(newCamelType), getDistance(newCamelType), newCamelType.getTimeToDrink(), newCamelType.getMaxWeight(),0 );
            return c;
        }
        return  null;
    }

    private double getDistance(CamelType camelType) {//normalni rozdeleni - gaussova krivka
        Random r = new Random();
        return ((double)camelType.getMinDistance() + (double)camelType.getMaxDistance())/2 + r.nextGaussian() * ((double)camelType.getMinDistance() - (double)camelType.getMaxDistance())/4;

    }

    private double getSpeed(CamelType camelType) {//roznomerne rozdeleni
        Random r = new Random();
        double value = r.nextDouble()*(camelType.getMaxSpeed() - camelType.getMinSpeed() + 1);
        return camelType.getMinSpeed() + (value);
    }

}