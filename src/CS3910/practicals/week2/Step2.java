package CS3910.practicals.week2;

import CS3910.practicals.week1.Graph;
import CS3910.practicals.week1.Node;
import CS3910.practicals.week1.Route;
import CS3910.practicals.week1.RouteCalculator;

import java.util.concurrent.TimeUnit;

public class Step2 {

    // In seconds
    public final static int timeLimit = 3;

    private static RouteCalculator routeCalculator;

    public static void main(String[] args) {
        Step2 step2 = new Step2();
        Graph graph = step2.initialiseGraph();
        routeCalculator = new RouteCalculator();
        // 1. Random initialisation
        Route route = routeCalculator.generateRandomRoute(graph);

        System.out.println("Original: " + route.toString());
        for (Route r : routeCalculator.generate2optNeighbourhood(route)) {
            System.out.println(r.toString());
        }

        // Lab sign off part 3
        step2.localSearch(route, graph, timeLimit);
    }

    public void localSearch(Route initial, Graph graph, int timeLimit) {
        Route currentSolution = initial;
        Route bestSolution = currentSolution;
        double currentSolutionCost = routeCalculator.getCostOfRoute(currentSolution);
        double bestSolutionCost = currentSolutionCost;


        // 2. CPU-time-based termination
        for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeLimit); stop > System.nanoTime(); ) {
            Route bestNeighbour = routeCalculator.findBestNeighbour(routeCalculator.generate2optNeighbourhood(currentSolution));
            if (routeCalculator.getCostOfRoute(bestNeighbour) < currentSolutionCost) {
                currentSolution = bestNeighbour;
            } else {
                // If local minima found generate new random route
                currentSolution = routeCalculator.generateRandomRoute(graph);
            }
            currentSolutionCost = routeCalculator.getCostOfRoute(currentSolution);

            System.out.println("Route: " + currentSolution.toString() + "\n" + "Cost: " + currentSolutionCost + "\n" + "------");

            if (currentSolutionCost < bestSolutionCost || bestSolutionCost <= 0) {
                bestSolution = currentSolution;
                bestSolutionCost = routeCalculator.getCostOfRoute(bestSolution);
            }
        }

        System.out.println("*BEST*\n" + "Route: " + bestSolution.toString());
        System.out.println("Cost: " + bestSolutionCost);
    }

    public static Graph initialiseGraph() {
        Graph graph = new Graph();

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addNeighbour(nodeB, 20);
        nodeA.addNeighbour(nodeC, 42);
        nodeA.addNeighbour(nodeD, 35);
        nodeA.addNeighbour(nodeE, 31);
        nodeA.addNeighbour(nodeF, 21);

        nodeB.addNeighbour(nodeA, 20);
        nodeB.addNeighbour(nodeC, 30);
        nodeB.addNeighbour(nodeD, 34);
        nodeB.addNeighbour(nodeE, 13);
        nodeB.addNeighbour(nodeF, 22);

        nodeC.addNeighbour(nodeA, 42);
        nodeC.addNeighbour(nodeB, 30);
        nodeC.addNeighbour(nodeD, 12);
        nodeC.addNeighbour(nodeE, 7);
        nodeC.addNeighbour(nodeF, 8);

        nodeD.addNeighbour(nodeA, 35);
        nodeD.addNeighbour(nodeB, 34);
        nodeD.addNeighbour(nodeC, 12);
        nodeD.addNeighbour(nodeE, 15);
        nodeD.addNeighbour(nodeF, 11);

        nodeE.addNeighbour(nodeA, 31);
        nodeE.addNeighbour(nodeB, 22);
        nodeE.addNeighbour(nodeC, 7);
        nodeE.addNeighbour(nodeD, 15);
        nodeE.addNeighbour(nodeF, 17);

        nodeF.addNeighbour(nodeA, 21);
        nodeF.addNeighbour(nodeB, 13);
        nodeF.addNeighbour(nodeC, 8);
        nodeF.addNeighbour(nodeD, 11);
        nodeF.addNeighbour(nodeE, 17);

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        return graph;
    }
}
