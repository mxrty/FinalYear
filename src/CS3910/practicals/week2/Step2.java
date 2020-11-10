package CS3910.practicals.week2;

import CS3910.practicals.week1.Graph;
import CS3910.practicals.week1.Node;
import CS3910.practicals.week1.Route;
import CS3910.practicals.week1.RouteCalculator;

import java.util.concurrent.TimeUnit;

public class Step2 {

    // In seconds
    public final static int timeLimit = 20;

    public static void main(String[] args) {
        Step2 step2 = new Step2();
        Graph graph = step2.initialiseGraph();
        RouteCalculator routeCalculator = new RouteCalculator();
        Route route = routeCalculator.generateRandomRoute(graph);
        System.out.println("Original: " + route.toString());
        for (Route r : routeCalculator.generate2optNeighbourhood(route)) {
            System.out.println(r.toString());
        }
        //step2.generateRandomSolutions(step2.initialiseGraph(), timeLimit);
    }

    public Graph initialiseGraph() {
        Graph graph = new Graph();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        nodeA.addNeighbour(nodeB, 20);
        nodeA.addNeighbour(nodeC, 42);
        nodeA.addNeighbour(nodeD, 35);
        nodeB.addNeighbour(nodeA, 20);
        nodeB.addNeighbour(nodeC, 30);
        nodeB.addNeighbour(nodeD, 34);
        nodeC.addNeighbour(nodeA, 42);
        nodeC.addNeighbour(nodeB, 30);
        nodeC.addNeighbour(nodeD, 12);
        nodeD.addNeighbour(nodeA, 35);
        nodeD.addNeighbour(nodeB, 34);
        nodeD.addNeighbour(nodeC, 12);
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        return graph;
    }

    public void generateRandomSolutions(Graph graph, int timeLimit) {
        Route bestSolution = new Route();
        RouteCalculator generator = new RouteCalculator();

        for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeLimit); stop > System.nanoTime(); ) {
            Route currentSolution = generator.generateRandomRoute(graph);
            double bestSolutionCost = generator.getCostOfRoute(bestSolution);
            double currentSolutionCost = generator.getCostOfRoute(currentSolution);
            System.out.println("Route: " + currentSolution.toString());
            System.out.println("Cost: " + currentSolutionCost);
            System.out.println("------");
            if (currentSolutionCost < bestSolutionCost || bestSolutionCost <= 0) {
                bestSolution = currentSolution;
            }
        }

        System.out.println("Route: " + bestSolution.toString());
        System.out.println("Cost: " + generator.getCostOfRoute(bestSolution));
    }

}
