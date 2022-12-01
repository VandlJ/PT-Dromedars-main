package src.parser;

import src.data.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Parser {
    private int inComment;//jsou v komentari nebo ne?
    private int position;//aktualni pozice v souboru
    private LinkedList<Warehouse> warehouses;
    private LinkedList<Path> paths;
    private LinkedList<Oasis> oases;
    private LinkedList<CamelType> camels;
    private LinkedList<Request> requests;
    private Counters counters;

    public Parser(String pathToFile) {
        inComment = 0;
        position = 0;
        warehouses = new LinkedList<>();
        oases = new LinkedList<>();
        camels = new LinkedList<>();
        requests = new LinkedList<>();
        paths = new LinkedList<>();
        parseFile(pathToFile);
    }

    private void parseFile(String pathToFile) {
        int prevChar = 0;//predchozi znak
        StringBuilder line = new StringBuilder("");//kam ukladam data ze souboru, ktere chci pouzit
        try {
            BufferedReader r = new BufferedReader(new FileReader(pathToFile));//nacteni souboru
            int ch;
            while((ch = r.read())!=-1) {//pokud -1 - konec souboru
                if(ch == 55357) {//znak velblouda
                    r.read();//nactu dalsi znak
                    inComment++;//jsem v komentari +1
                    continue;//na zacatek while
                }
                if(ch == 55356) {//znak pouste
                    r.read();//nactu dalsi znak
                    inComment--;//jsem v komentari -1
                    line.append(' ');//pridam mezeru
                    continue;//na zacatek while
                }
                if(inComment == 0) {//= nejsem v komentari
                    if (ch == 10) {//konec radky
                        ch = ' ';
                    }
                    if(line.length() == 0 && (ch == ' ' || ch == 9) ) {//delka 0 a zaroven mezera nebo tab
                        continue;//nenacitej
                    } else if((ch == ' ' || ch == '\t')&&(prevChar == ' ' || prevChar == 9)) {//mezera nebo tab a predchozi mezera nebo tab
                        continue;//nenacitej
                    }
                    else {
                        if(ch == 9) {//tabulator
                            line.append(' ');//pridej mezeru
                        }
                        else {
                            line.append((char) ch);//pridej dany znak
                        }
                        prevChar = ch;
                    }
                }
            }
            r.close();
        } catch (IOException e) {
            System.out.println("File not found; program ended");
        }
        workWithLine(removeWhiteSpaces(line.toString()));
    }

    private void workWithLine(String line) {//prace s daty
        System.out.println("Input: " + line + "\n");
        String [] splitted = line.split(" ");//rozdeleni podle mezer
        try {
            counters = new Counters();
            int idx = 0;
            //---------------------------warehouses-----------------------
            int warehousesCount = Integer.parseInt(splitted[idx]);
            idx++;
            int basicPos = 0;
            int arguments = 5;
            for(int i = 0; i < warehousesCount; i++) {
                buildWarehouse(Double.parseDouble(splitted[idx+basicPos]), Double.parseDouble(splitted[idx+basicPos+1]), Integer.parseInt(splitted[idx+basicPos+2]), Double.parseDouble(splitted[idx+basicPos+3]), Double.parseDouble(splitted[idx+basicPos+4]));
                basicPos+=arguments;
            }
            idx += warehousesCount * arguments;
            counters.setWarehousesCount(warehousesCount);

            //-----------------------oasis----------------------------
            int oasisCount = Integer.parseInt(splitted[idx]);
            idx++;
            basicPos = 0;
            arguments = 2;
            for (int i = 0; i < oasisCount; i++) {
                buildOasis(Double.parseDouble(splitted[idx + basicPos]), Double.parseDouble(splitted[idx + basicPos + 1]));
                basicPos += arguments;
            }
            idx += oasisCount * arguments;
            counters.setOasisCount(oasisCount);

            //------------------------paths--------------------------
            int pathCount = Integer.parseInt(splitted[idx]);
            idx++;
            basicPos = 0;
            arguments = 2;
            for (int i = 0; i < pathCount; i++) {
                buildPath(Integer.parseInt(splitted[idx + basicPos]), Integer.parseInt(splitted[idx + basicPos + 1]));
                basicPos += arguments;
            }
            idx += pathCount * arguments;
            counters.setPathCount(pathCount);

            //-----------------------camels--------------------------
            int dromedarsCount = Integer.parseInt(splitted[idx]);
            idx++;
            basicPos = 0;
            arguments = 8;
            for (int i = 0; i < dromedarsCount; i++) {
                buyCamel(splitted[idx+basicPos], Double.parseDouble(splitted[idx + basicPos+1]), Double.parseDouble(splitted[idx + basicPos+2]), Double.parseDouble(splitted[idx + basicPos+3]), Double.parseDouble(splitted[idx + basicPos+4]), Double.parseDouble(splitted[idx + basicPos+5]), Integer.parseInt(splitted[idx+basicPos+6]), Double.parseDouble(splitted[idx+basicPos+7]));
                basicPos += arguments;
            }
            idx += dromedarsCount * arguments;
            counters.setCamelCount(dromedarsCount);

            //------------------------requests--------------------
            int requestsCount = Integer.parseInt(splitted[idx]);
            idx++;
            basicPos = 0;
            arguments = 4;
            for(int i = 0; i < requestsCount; i++) {
                makeOrder(Double.parseDouble(splitted[idx + basicPos]), Integer.parseInt(splitted[idx + basicPos+1]), Integer.parseInt(splitted[idx + basicPos+2]), Double.parseDouble(splitted[idx + basicPos+3]));
                basicPos += arguments;
            }
            counters.setRequestCount(requestsCount);

        } catch (Exception e) {
            System.out.println("Wrong format file" + e.getLocalizedMessage() + " ---" + e.getMessage() );
        }
    }

    private String removeWhiteSpaces(String toString) {//odstraneni vicenasobnych mezer
        int lineLen = toString.length();
        String newString = toString.replace("  "," ");
        while(lineLen != newString.length()) {
            if(newString.charAt(0) == ' ') {
                newString=newString.substring(1);
            }
            lineLen = newString.length();
            newString = newString.replace("  ", " ");
        }
        return newString;
    }

    private void makeOrder(double tStart, int oasisID, int baskets, double finalTime) {//vytvoreni seznamu pozadavku
        Request r = new Request(tStart, oasisID, baskets, finalTime);
        requests.add(r);
    }

    private void buyCamel(String name, double minSpeed, double maxSpeed, double minDistance, double maxDistance, double timeToDrink, int maxHeight, double freq) {//vytvoreni seznamu typu velbloudu
        CamelType c = new CamelType(name, minSpeed, maxSpeed, minDistance, maxDistance, timeToDrink, maxHeight, freq);
        camels.add(c);
    }

    private void buildPath(int x, int y) {//vytvoreni seznamu cest
        Path p = new Path(x, y);
        paths.add(p);
    }

    private void buildOasis(double x, double y) {//vytvoreni seznamu oaz
        Oasis o = new Oasis(x, y);
        oases.add(o);
    }

    private void buildWarehouse(double x, double y, int baskets, double timeFill, double timeLoad) {//vytvoreni seznamu skladu
        Warehouse w = new Warehouse(x, y, baskets, timeFill, timeLoad);
        warehouses.add(w);
    }

    public LinkedList<Warehouse> getWarehouses() {
        return warehouses;
    }

    public LinkedList<Path> getPaths() {
        return paths;
    }

    public LinkedList<Oasis> getOases() {
        return oases;
    }

    public LinkedList<CamelType> getCamels() {
        return camels;
    }

    public LinkedList<Request> getRequests() {
        return requests;
    }

    public Counters getCounters() {
        return counters;
    }
}