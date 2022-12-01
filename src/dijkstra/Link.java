package src.dijkstra;

public class Link {
    int neighbour;
    Link next;

    public Link(int n, Link next) {
        this.neighbour = n;//pridano this
        this.next = next;//pridano this
    }
}
