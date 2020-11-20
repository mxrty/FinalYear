package CS3910.practicals.week1;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class App {
    private static Graph graph;

    public static void main(String[] args) {
        App app = new App();
        CsvParser parser = new CsvParser();

        // Laptop: C:\Users\M\Projects\FinalYear\src\CS3910\practicals
        // Desktop: C:\Users\MS\IdeaProjects\FinalYear\src\CS3910\practicals
        graph = parser.generateGraphFromCsv("C:\\Users\\MS\\IdeaProjects\\FinalYear\\src\\CS3910\\practicals\\cities57_10.csv");

        // Lab sign off part 1
        RouteCalculator routeCalculator = new RouteCalculator();
        Route specifiedRoute = routeCalculator.createRoute(graph, "1,2,3,4,5,6,7,8,9,10");
        //System.out.println("Route: " + specifiedRoute.toString() + "\n" + "Cost: " + routeCalculator.getCostOfRoute(specifiedRoute));

        // Lab sign off part 2
        app.randomSearch(graph, 10);
    }

    public void randomSearch(Graph graph, int timeLimit) {
        Route bestSolution = new Route();
        double bestSolutionCost = Double.MAX_VALUE;
        RouteCalculator generator = new RouteCalculator();

        for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeLimit); stop > System.nanoTime(); ) {
            Route currentSolution = generator.generateRandomRoute(graph);
            double currentSolutionCost = generator.getCostOfRoute(currentSolution);
            System.out.println("Route: " + currentSolution.toString());
            System.out.println("Cost: " + currentSolutionCost);
            System.out.println("------");
            if (currentSolutionCost < bestSolutionCost) {
                bestSolution = currentSolution;
                bestSolutionCost = currentSolutionCost;
            }
        }

        System.out.println("*BEST*\n" + "Route: " + bestSolution.toString());
        System.out.println("Cost: " + bestSolutionCost);
    }
}
