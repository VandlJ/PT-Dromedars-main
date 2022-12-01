package src.dijkstra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Graph {
    Link[] edges;
    private LinkedList<Node> vertex = new LinkedList<>();

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public void removeNode(Node nodeA) {nodes.remove(nodeA);}

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
    public LinkedList<Node> getVertex(){return vertex;}

    public void addEdge(int start, int end) {
        Link n = new Link(end, edges[start]);
        Link n2 = new Link(start, edges[end]);
        edges[start] = n;
        edges[end] = n2;
    }
    public ArrayList<Integer> neighbours(int v) {
        ArrayList<Integer> result = new ArrayList<>();
        Link n = edges[v];
        while (n != null) {
            result.add(n.neighbour);
            n = n.next;
        }
        return result;
    }
    public int[] BFSDistance(int s) {
        int[] result = new int[edges.length];
        for (int i = 0;i<edges.length;i++)
            result[i] = -1;
        result[s] = 0;
        int[] mark = new int[edges.length];
        mark[s] = 1;
        LinkedList<Object> q = new LinkedList<>();
        q.add(s);
        while(!q.isEmpty()) {
            int v = (int) q.pop();
            ArrayList<Integer> nbs = neighbours(v);
            for(int i = 0;i<nbs.size();i++) {
                int n = nbs.get(i);
                if (mark[n] == 0) {
                    mark[n] = 1;
                    q.add(n);
                    result[n] = result[v]+1;
                }
            }
            mark[v] = 2;
        }
        return result;
    }
    public void initialize(int vertexCount) {
        this.edges = new Link[vertexCount];
    }
}

