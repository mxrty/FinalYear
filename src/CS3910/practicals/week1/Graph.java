package CS3910.practicals.week1;

import java.util.Hashtable;
import java.util.Map;

public class Graph {
    private final Hashtable<String, Node> nodes;

    public Graph() {
        nodes = new Hashtable<String, Node>();
    }

    public void addNode(Node node) {
        nodes.put(node.getName(), node);
    }

    public Hashtable<String, Node> getNodes() {
        return nodes;
    }

    public void printNodes() {
        for (Object node : nodes.values().toArray()) {
            for (Object entry : ((Node) node).getNeighbours().entrySet()) {
                entry = entry;
                Node key = (Node) ((Map.Entry) entry).getKey();
                Double value = (Double) ((Map.Entry) entry).getValue();
                System.out.println("Node " + ((Node) node).getName() + " has a path to Node " + key.getName()
                        + " with a weight of " + value);
            }
        }
    }
}
