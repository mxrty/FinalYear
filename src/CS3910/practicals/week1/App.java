package CS3910.practicals.week1;

import java.util.Map;

public class App {
    private static Graph graph;

    public static void main(String[] args) {
//        graph = new Graph();
//
//        Node nodeA = new Node("A");
//        Node nodeB = new Node("B");
//        Node nodeC = new Node("C");
//        Node nodeD = new Node("D");
//
//        nodeA.addNeighbour(nodeB, 20);
//        nodeA.addNeighbour(nodeC, 42);
//        nodeA.addNeighbour(nodeD, 35);
//        nodeB.addNeighbour(nodeA, 20);
//        nodeB.addNeighbour(nodeC, 30);
//        nodeB.addNeighbour(nodeD, 34);
//        nodeC.addNeighbour(nodeA, 42);
//        nodeC.addNeighbour(nodeB, 30);
//        nodeC.addNeighbour(nodeD, 12);
//        nodeD.addNeighbour(nodeA, 35);
//        nodeD.addNeighbour(nodeB, 34);
//        nodeD.addNeighbour(nodeC, 12);
//
//        graph.addNode(nodeA);
//        graph.addNode(nodeB);
//        graph.addNode(nodeC);
//        graph.addNode(nodeD);
//
//        graph.printNodes();
//
//        //Generate random route
//        RouteCalculator gen = new RouteCalculator();
//        Route randRoute = gen.generateRandomRoute(graph);
//        System.out.println("Random route " + randRoute.toString() + " costs " + gen.getCostOfRoute(randRoute));

        CsvParser parser = new CsvParser();
        graph = parser.generateGraphFromCsv("C:\\Users\\MS\\IdeaProjects\\FinalYear\\src\\CS3910\\practicals\\ulysses16.csv");
        graph.printNodes();
    }
}
