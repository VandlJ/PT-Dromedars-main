package src.dataWrappers;

import src.data.Counters;
import src.data.Path;

import java.util.LinkedList;

public class Paths {
    LinkedList<Path> paths; //spojovy seznam cest
    Counters counters; //pocet
    public Paths(LinkedList<Path> paths, Counters counters) {
        this.paths = paths;
        this.counters = counters;
    }

    public LinkedList<Path> getPaths() {
        return paths;
    }
}