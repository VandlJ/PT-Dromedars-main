package src.main;

import src.data.*;
import src.dataWrappers.*;
import src.dijkstra.Dijkstra;
import src.dijkstra.Graph;
import src.dijkstra.Link;
import src.dijkstra.Node;
import src.parser.Parser;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.util.Set;
import java.util.Queue;

import static src.dijkstra.Dijkstra.*;

public class Main {
    private static Counters counters = new Counters();
    private static Warehouses warehouses; //sklady
    private static Camels camels; //velbloudi
    private static Oases oases; //aozy
    private static Paths paths; //cesty
    private static Requests requests; //pozadavky
    private static LinkedList<Place> places; //spojovy seznam mist
    private static LinkedList<Request> requestsInProcess;


    public static void main(String[] args) {
        System.out.println("Program started\n");

        startUp(args[0]); //nacteni ze souboru - args[0] je cesta do souboru
        ArrayList<ArrayList<Integer>> map = initMap(); //matice sousednosti
        //System.out.println("tady " + Arrays.toString(map.toArray()));
        int time = 0;
        int count = 1;
        int nearestWarehouse;
        while (true) {
            if ((requests.getReqToDo() + requests.getReqInAction()) == 0) { //všechny pozadavky vyrizene a zadny v behu
                break;
            }
            for (int j = 0; j < counters.getRequestCount(); j++) {
                Request actualRequest = requests.getRequestById(j); //beru pozadavek
                if (actualRequest.gettStart() == time) { //kdyz prisel pozadavek v aktualnim case
                    System.out.println("Cas: " + actualRequest.gettStart() + ", Pozadavek: " + count +
                            ", Oaza: " + actualRequest.getOasisID() + ", Pocet kosu: " + actualRequest.getBaskets() +
                            ", Deadline: " + (actualRequest.gettStart() + actualRequest.getFinalTime()));
                    count++;
                    requests.reqToDoDecrement(); //potrebne udelat -1
                    requests.setReqInActionIncrement(); //v akci +1
                    //requestsInProcess.add(actualRequest);
                    //requests.getRequests().removeFirst();
                    //System.out.println("Cas : " +actualRequest.gettStart() + ", Velbloud : " + superbloud.getName() +
                    //  ", Sklad : " + warehouses.getWarehouseByPosition(0) + ", ");
                    while (true) {
                        Camel camel = camels.generateCamel(0);//generuji velblouda, ktery pujde cestu
                        if (counters.getWarehousesCount() > 1) {
                            nearestWarehouse = nearestWarehouseFound(map, actualRequest.getOasisID(), camel.getDistanceWithoutDrink());
                            if(nearestWarehouse == -1){
                                System.out.println("Zadna cesta nebyla nalezena.");
                            }
                        } else {
                            nearestWarehouse = 0;
                        }
                        int[] result = createGraph(camel.getDistanceWithoutDrink(), map, nearestWarehouse);
                        if (result[actualRequest.getOasisID() + counters.getWarehousesCount() - 1] == -1) {
                            continue;
                        }

                        System.out.println("Cas: " + actualRequest.gettStart() + ", Velbloud: " + camel.getName() + ", Sklad: "
                                + nearestWarehouse + ", Nalozeno kosu: " + actualRequest.getBaskets() + ", Odchod v: "
                                + (actualRequest.gettStart() + ((actualRequest.getBaskets())
                                * warehouses.getWarehouseByPosition(nearestWarehouse).getTimeToPutBasketOnCamel())));

                        double distance = dijkstra(map, actualRequest.getOasisID(), nearestWarehouse, camel.getDistanceWithoutDrink()); //hledani nejkratsi cesty
                        double walkTimeD = distance / (camel.getSpeed()); //jak dlouho pujde jednu cestu nez napiti
                        int walkTime = roundingDoubleUp(walkTimeD);
                        double timeInWarehouse0 = (walkTime * 2) + actualRequest.gettStart() + (actualRequest.getBaskets()
                                * warehouses.getWarehouseByPosition(nearestWarehouse).getTimeToPutBasketOnCamel() * 2); //v jakem casu se vrati do skladu
                        int timeInWarehouse = roundingDoubleUp(timeInWarehouse0); //zaokrouhleni casu nahoru
                        double vylozeno_v = (walkTime + actualRequest.gettStart() + (actualRequest.getBaskets()
                                * 2 * warehouses.getWarehouseByPosition(nearestWarehouse).getTimeToPutBasketOnCamel()));

                        System.out.println("Cas: " + (walkTime + actualRequest.gettStart() + (actualRequest.getBaskets()
                                * warehouses.getWarehouseByPosition(nearestWarehouse).getTimeToPutBasketOnCamel()))
                                + ", Velbloud: " + camel.getName() + ", Oaza: " + actualRequest.getOasisID()
                                + ", Vylozeno kosu: " + actualRequest.getBaskets() + ", Vylozeno v: " + (walkTime
                                + actualRequest.gettStart() + (actualRequest.getBaskets()
                                * 2 * warehouses.getWarehouseByPosition(nearestWarehouse).getTimeToPutBasketOnCamel()))
                                + ", Casova rezerva: " + (((actualRequest.gettStart() + actualRequest.getFinalTime())/2)
                                - vylozeno_v));

                        System.out.println("Cas: " + timeInWarehouse + ", Velbloud: " + camel.getName()
                                + ", Navrat do skladu: " + timeInWarehouse + "\n");
                        break;
                    }
                    requests.setReqInActionDecrement();
                }
            }
            time++;
        }

        //dijkstra(map);
        System.out.println();
        System.out.println("Program ended");
    }

    static void addEdge(ArrayList<ArrayList<Integer>> map, int v, int u) {
//        map.get(u).add(v);
        if(map.get(v).contains(u) || map.get(u).contains(v))
            return;

        map.get(v).add(u);
    }

    static void printGraph(ArrayList<ArrayList<Integer>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            System.out.println("\ntypek: " + i);
            System.out.println("potomek: ");
            for (int j = 0; j < adj.get(j).size(); j++) {
                System.out.println(" -> " + adj.get(i).get(j));
            }
            System.out.println();
        }
    }

    private static ArrayList<ArrayList<Integer>> initMap() { //seznam sousedu
        int V = counters.getPlacesCount();
        ArrayList<ArrayList<Integer>> map = new ArrayList<>(V);

        for (int i = 0; i < V; i++) {
            map.add(new ArrayList<>());
        }

        for (Path p : paths.getPaths()) {
            addEdge(map, (p.getFrom()-1), (p.getTo()-1));
            addEdge(map, (p.getTo()-1), (p.getFrom()-1));
        }

        //printGraph(map);
        return map;
    }

    private static double dijkstra(ArrayList<ArrayList<Integer>> map, int oasisID, int nearestWarehouse, double distanceWithoutDrink) {
        //hledani nejkratsi cesty

        Node[] vertex = new Node[counters.getPlacesCount()];

        for (int i = 0; i < counters.getPlacesCount(); i++) {
            int j = i + 1;
            String name = String.valueOf(j);
            Node node = new Node(name);
            vertex[i] = node;
        }

        for (int i = 0; i < map.size(); i++) {
            ArrayList<Integer> adjList = map.get(i);
            for (int j = 0; j < adjList.size(); j++) {
                double x1 = places.get(i).getX();
                double y1 = places.get(i).getY();
                double x2 = places.get(j).getX();
                double y2 = places.get(j).getY();
                double a = Math.abs(x1 - x2);
                double b = Math.abs(y1 - y2);
                double c = Math.sqrt((a * a) + (b * b));
                if ((distanceWithoutDrink > c) || (Math.abs(distanceWithoutDrink - c) < 0.00001)) {
                    vertex[i].addDestination(vertex[j], c);
                    vertex[j].addDestination(vertex[i], c);
                }
            }
        }

        Graph graph = new Graph();

        for (int i = 0; i < vertex.length; i++) {
            graph.addNode(vertex[i]);
        }

        graph = Dijkstra.calculateShortestPathFromSource(graph, vertex[nearestWarehouse]);

        //printPaths(List.of(vertex));

        LinkedList<Node> places = new LinkedList<>();
        int warehouseIndex = oasisID + counters.getWarehousesCount() - 1;
        //places = getPlaces(vertex[warehouseIndex]);
        /*for (int k = 0; k < places.size(); k++) {
            System.out.println(places.get(k).getName());
        }*/
        //printPaths(List.of(vertex));
        //getPlaces(List.of(vertex));
        //System.out.println(printValue(vertex[warehouseIndex]));
        double distance = printValue(vertex[warehouseIndex]);
        return distance;
    }

    private static int nearestWarehouseFound(ArrayList<ArrayList<Integer>> map, int oasis, double distanceWithoutDrink) {
        Node[] vertexes = new Node[counters.getPlacesCount()];
        for (int i = 0; i < counters.getPlacesCount(); i++) {
            int j = i + 1;
            String name = String.valueOf(j);
            Node nodes = new Node(name);
            vertexes[i] = nodes;
        }
        double longestPath = 0;
        for (int i = 0; i < map.size(); i++) {
            ArrayList<Integer> adjList = map.get(i);
            for (int j = 0; j < adjList.size(); j++) {
                double x1 = places.get(i).getX();
                double y1 = places.get(i).getY();
                double x2 = places.get(j).getX();
                double y2 = places.get(j).getY();
                double a = Math.abs(x1 - x2);
                double b = Math.abs(y1 - y2);
                double c = Math.sqrt((a * a) + (b * b));
                if ((distanceWithoutDrink > c) || (Math.abs(distanceWithoutDrink - c) < 0.000000000001)) {
                    vertexes[i].addDestination(vertexes[j], c);
                    vertexes[j].addDestination(vertexes[i], c);
                    if(c > longestPath) {
                        longestPath = c;
                    }
                }
            }
        }

        Graph graph = new Graph();
        int warehouseVertexCount = counters.getWarehousesCount();

        for (int i = 0; i < vertexes.length; i++) {
            graph.addNode(vertexes[i]);
        }
        while(true) {
            if(warehouseVertexCount == 0){
                System.out.println("Zadna cesta nebyla nalezena.");
                return -1;
            }
            graph = Dijkstra.calculateShortestPathFromSource(graph, vertexes[oasis - 1]);

            //printPaths(Arrays.asList(vertexes));

            double distance = printValue(vertexes[0]);
            int nearestWarehouse = 0;
            for (int k = 1; k < counters.getWarehousesCount(); k++) {
                double d = printValue(vertexes[k]);
                if ((d < distance) && (d > 0)) {
                    distance = d;
                    nearestWarehouse = k;
                }
            }
            boolean rightnessOfWay = false;
            for (int d = 0; d < counters.getCamelCount(); d++) {
                if (camels.getCamelByPos(d).getMaxDistance() > longestPath) {
                    rightnessOfWay = true;
                }
            }
            if (rightnessOfWay) {
                return nearestWarehouse;
            }
            else {
                graph.removeNode(vertexes[nearestWarehouse]);
                warehouseVertexCount--;
                System.out.println(warehouseVertexCount);
            }
        }
    }

    private static int roundingDoubleUp(double time) { //zaokrouhlovani doublu nahoru
        int helpITime = (int) time;
        double epsilon = 0.00001;
        int timeInt;
        if (Math.abs(time - helpITime) < epsilon) {
            timeInt = helpITime;
        } else {
            timeInt = helpITime + 1;
        }
        return timeInt;
    }

    private static double pathLength(Node vertex) {
        return 1;
    }

    private static void startUp(String path) { //nacteni souboru a spojove seznamy dat
        Parser p = new Parser(path);
        counters = p.getCounters();
        warehouses = new Warehouses(p.getWarehouses(), counters);
        camels = new Camels(p.getCamels(), counters);
        oases = new Oases(p.getOases(), counters);
        paths = new Paths(p.getPaths(), counters);
        requests = new Requests(p.getRequests(), counters);
        places = new LinkedList<>();
        requestsInProcess = new LinkedList<>();
        for (int i = 0; i < counters.getWarehousesCount(); i++) {
            places.add(warehouses.getWarehouseByPosition(i));
        }
        for (int j = 0; j < counters.getOasisCount(); j++) {
            places.add(oases.getOasisByPosition(j));
        }
    }

    public static int[] createGraph(double distanceWithoutDrink, ArrayList<ArrayList<Integer>> map, int s){
        Graph g2 = new Graph();
        g2.initialize(counters.getPlacesCount());
        for (int i = 0; i < map.size(); i++) {
            ArrayList<Integer> adjList = map.get(i);
            for (int j = 0; j < adjList.size(); j++) {
                double x1 = places.get(i).getX();
                double y1 = places.get(i).getY();
                double x2 = places.get(j).getX();
                double y2 = places.get(j).getY();
                double a = Math.abs(x1 - x2);
                double b = Math.abs(y1 - y2);
                double c = Math.sqrt((a * a) + (b * b));
                if ((distanceWithoutDrink > c) || (Math.abs(distanceWithoutDrink - c) < 0.000000000001)) {
                    g2.addEdge(i, j);
                }
            }
        }
        int[] result = g2.BFSDistance(s);
        return result;
    }
}

