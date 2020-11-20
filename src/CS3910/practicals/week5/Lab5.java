package CS3910.practicals.week5;

import CS3910.practicals.week1.*;
import CS3910.practicals.week2.Step2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Lab5 {
    private RouteCalculator routeCalculator;
    private Graph graph;
    private ArrayList<Route> population;
    private Route globalBestRoute;
    private double globalBestRouteCost;
    private Random random;

    /*
    Representation
    --------------------------
    Fitness evaluation        : length of tour (smaller = better)
    Recombination             : order 1 crossover
    Recombination probability : 100%
    Mutation                  : Swap
    Mutation probability      : 70%
    Parent selection          : tournament, size 2
    Survivor selection        : elitism and generational
    Population size           : 100
    Initialisation            : random
    Termination               : 100 generations
     */
    public static void main(String[] args) {
        // Lab sign off part 4
        Lab5 lab = new Lab5(100, 100);
    }

    public Lab5(int populationSize, int generations) {
        CsvParser parser = new CsvParser();
        graph = parser.generateGraphFromCsv("C:\\Users\\MS\\IdeaProjects\\FinalYear\\src\\CS3910\\practicals\\cities57_10.csv");
        routeCalculator = new RouteCalculator();
        random = new Random();

        //Initialise global bests
        globalBestRoute = new Route();
        globalBestRouteCost = Double.MAX_VALUE;

        // INITIALISE population with random candidate solutions
        population = initialisePopulation(populationSize);
        // EVALUATE each candidate
        evaluatePopulation(population);
        // REPEAT until termination condition satisfied
        for (int i = 0; i < generations; i++) {
            ArrayList<Route> mutatedOffspring = selectParentsAndProduceOffspring(population);
            evaluatePopulation(mutatedOffspring);
            survivorSelection(population, mutatedOffspring);
        }

        System.out.println("********* End off algorithm after " + generations + " generations *********");
        System.out.println("Best route for this run: " + globalBestRoute.toString() + " with a cost of " + globalBestRouteCost);
    }

    public ArrayList<Route> initialisePopulation(int populationSize) {
        ArrayList<Route> population = new ArrayList<Route>();
        for (int i = 0; i < populationSize; i++) {
            population.add(routeCalculator.generateRandomRoute(graph));
        }
        return population;
    }

    /*
    1. Tournament selection of parents
    2. Recombine selected parents to produce offspring
    3. Mutate offspring
    4. Add offspring to population
     */
    public ArrayList<Route> selectParentsAndProduceOffspring(ArrayList<Route> population) {
        ArrayList<Route> offspring = new ArrayList<Route>();
        for (int i = 0; i < population.size() / 2; i++) {
            Route parentA = population.get(random.nextInt(population.size()));
            Route parentB = population.get(random.nextInt(population.size()));

            Route[] children = order1Crossover(parentA, parentB);
            offspring.add(children[0]);
            offspring.add(children[1]);
        }

        return offspringMutation(offspring);
    }

    /*
     1. Pick top 10% parents
     2. Pick top children until populationSize is met
     */
    public void survivorSelection(ArrayList<Route> parents, ArrayList<Route> offspring) {
        int eliteQuota = parents.size() / 10;
        ArrayList<Route> eliteParents = pickTopNRoutesFromList(parents, eliteQuota);
        ArrayList<Route> newGeneration = pickTopNRoutesFromList(offspring, parents.size() - eliteQuota);
        population = eliteParents;
        population.addAll(newGeneration);
    }

    public ArrayList<Route> offspringMutation(ArrayList<Route> population) {
        ArrayList<Route> mutatedOffspring = new ArrayList<Route>();
        for (int i = 0; i < population.size(); i++) {
            Route candidate = population.get(i);
            int probability = random.nextInt(10);
            // 1-6 = 70% | 7-9 = 30%
            if (probability < 7) {
                candidate = routeCalculator.generate2optNeighbourhood(candidate).get(0);
            }
            mutatedOffspring.add(candidate);
        }
        return mutatedOffspring;
    }

    public void evaluatePopulation(ArrayList<Route> population) {
        double bestCost = Double.MAX_VALUE;
        Route bestRoute = population.get(0);
        for (int i = 0; i < population.size(); i++) {
            Route current = population.get(i);
            double currentCost = routeCalculator.getCostOfRoute(current);
            if (currentCost < bestCost) {
                bestRoute = current;
                bestCost = currentCost;
            }
        }
        System.out.print("Best route for this generation: " + bestRoute.toString() + " with a fitness of " + bestCost);
        if (bestCost < globalBestRouteCost) {
            globalBestRoute = bestRoute;
            globalBestRouteCost = bestCost;
            System.out.println(". This is the new lowest cost route!!!");
        } else {
            System.out.print("\n");
        }
    }

    public Route[] order1Crossover(Route parentA, Route parentB) {
        Route[] recombinedParents = new Route[2];
        final int routeLength = parentA.size();
        ArrayList<Node> routeA = parentA.getRouteList();
        ArrayList<Node> routeB = parentB.getRouteList();

        final int spliceLength = random.nextInt(routeLength / 2) + 1;
        final int spliceStart = random.nextInt(routeLength - spliceLength);
        final int spliceEnd = spliceStart + spliceLength;

        ArrayList<Node> childARoute = new ArrayList<Node>();
        ArrayList<Node> childBRoute = new ArrayList<Node>();

        childARoute.addAll(routeA.subList(spliceStart, spliceEnd));
        childBRoute.addAll(routeB.subList(spliceStart, spliceEnd));

        int cityIndex = 0;
        for (int i = 0; i < routeLength; i++) {
            cityIndex = (spliceEnd + i + 1) % routeLength;
            Node cityAtIndexInRouteA = routeA.get(cityIndex);
            Node cityAtIndexInRouteB = routeB.get(cityIndex);

            if (!childARoute.contains(cityAtIndexInRouteB)) {
                childARoute.add(cityAtIndexInRouteB);
            }

            if (!childBRoute.contains(cityAtIndexInRouteA)) {
                childBRoute.add(cityAtIndexInRouteA);
            }

            Collections.rotate(childARoute, spliceStart);
            Collections.rotate(childBRoute, spliceStart);
        }

        recombinedParents[0] = new Route(childARoute);
        recombinedParents[1] = new Route(childBRoute);

        return recombinedParents;
    }

    public ArrayList<Route> pickTopNRoutesFromList(ArrayList<Route> list, int topN) {
        ArrayList<Route> topRoutes = new ArrayList<Route>();

        // Sort in ascending order
        Collections.sort(list, (route1, route2) -> {
            double route1Cost = routeCalculator.getCostOfRoute(route1);
            double route2Cost = routeCalculator.getCostOfRoute(route2);
            if (route1Cost < route2Cost) {
                return -1;
            } else if (route1Cost > route2Cost) {
                return 1;
            } else {
                return 0;
            }
        });

        for (int i = 0; i < topN; i++) {
            double cost = routeCalculator.getCostOfRoute(list.get(i));

            topRoutes.add(list.get(i));
        }

        return topRoutes;
    }
}
