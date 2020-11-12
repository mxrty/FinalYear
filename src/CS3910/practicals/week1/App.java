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
        graph = parser.generateGraphFromCsv("C:\\Users\\M\\Projects\\FinalYear\\src\\CS3910\\practicals\\ulysses16.csv");

        // Lab sign off part 1
        RouteCalculator routeCalculator = new RouteCalculator();
        Route specifiedRoute = routeCalculator.createRoute(graph, "1,2,4,3,6,5,7,8,10,9");
        System.out.println("Route: " + specifiedRoute.toString() + "\n" + "Cost: " + routeCalculator.getCostOfRoute(specifiedRoute));

        // Lab sign off part 2
        app.randomSearch(graph, 3);
    }

    public void randomSearch(Graph graph, int timeLimit) {
        Route bestSolution = new Route();
        double bestSolutionCost = 0;
        RouteCalculator generator = new RouteCalculator();

        for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeLimit); stop > System.nanoTime(); ) {
            Route currentSolution = generator.generateRandomRoute(graph);
            double currentSolutionCost = generator.getCostOfRoute(currentSolution);
            System.out.println("Route: " + currentSolution.toString());
            System.out.println("Cost: " + currentSolutionCost);
            System.out.println("------");
            if (currentSolutionCost < bestSolutionCost || bestSolutionCost <= 0) {
                bestSolution = currentSolution;
                bestSolutionCost = currentSolutionCost;
            }
        }

        System.out.println("*BEST*\n" + "Route: " + bestSolution.toString());
        System.out.println("Cost: " + bestSolutionCost);
    }
}
