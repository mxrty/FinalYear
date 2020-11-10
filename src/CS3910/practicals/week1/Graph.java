package CS3910.practicals.week1;

import java.util.Hashtable;
import java.util.Map;

public class Graph {
    private Hashtable<String, Node> nodes;

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
                entry = (Map.Entry<Node, Integer>) entry;
                Node key = (Node) ((Map.Entry) entry).getKey();
                Double value = (Double) ((Map.Entry) entry).getValue();
                System.out.println("Node " + ((Node) node).getName() + " has a path to Node " + key.getName()
                        + " with a weight of " + value);
            }
        }
    }
}


//package CS3910.practicals.week1;
//
//import java.util.HashSet;
//import java.util.Map;
//
//public class Graph {
//    private HashSet<Node> nodes;
//
//    public Graph() {
//        nodes = new HashSet<Node>();
//    }
//
//    public void addNode(Node node) {
//        if (!nodes.contains(node)) {
//            nodes.add(node);
//        }
//    }
//
//    public HashSet<Node> getNodes() {
//        return nodes;
//    }
//
//    public void printNodes() {
//        for (Object node : nodes.toArray()) {
//            for (Object entry : ((Node) node).getNeighbours().entrySet()) {
//                entry = (Map.Entry<Node, Integer>) entry;
//                Node key = (Node) ((Map.Entry) entry).getKey();
//                Double value = (Double) ((Map.Entry) entry).getValue();
//                System.out.println("Node " + ((Node) node).getName() + " has a path to Node " + key.getName()
//                        + " with a weight of " + value);
//            }
//        }
//    }
//}
