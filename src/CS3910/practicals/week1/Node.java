package CS3910.practicals.week1;

import java.util.HashMap;

public class Node {
    private final String name;
    private final HashMap<Node, Double> neighbours;
    private double x;
    private double y;

    public Node(String name) {
        this.name = name;
        neighbours = new HashMap();
    }

    public Node(String name, double x, double y) {
        this.name = name;
        neighbours = new HashMap();
        this.x = x;
        this.y = y;
    }

    public void addNeighbour(Node neighbour, double weight) {
        neighbours.put(neighbour, weight);
    }

    public String getName() {
        return name;
    }

    public HashMap getNeighbours() {
        return neighbours;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
