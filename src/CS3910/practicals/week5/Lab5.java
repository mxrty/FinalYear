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
        Lab5 lab = new Lab5(100, 100);
    }

    public Lab5(int populationSize, int generations) {
        CsvParser parser = new CsvParser();
        graph = parser.generateGraphFromCsv("C:\\Users\\MS\\IdeaProjects\\FinalYear\\src\\CS3910\\practicals\\ulysses16.csv");
        //graph = Step2.initialiseGraph();
        routeCalculator = new RouteCalculator();
        random = new Random();

        //Initialise global bests
        globalBestRoute = new Route();
        globalBestRouteCost = Double.MAX_VALUE;

        population = initialisePopulation(populationSize);
        //TODO: Check fitness function (same output every generation)
        evaluatePopulation(population);
        for (int i = 0; i < generations; i++) {
            ArrayList<Route> selectedParents = parentSelection(population);
            ArrayList<Route> offspring = recombinePairsOfParents(selectedParents);
            // TODO: Maintain population size
            ArrayList<Route> offspring2 = recombinePairsOfParents(selectedParents);
            offspring.addAll(offspring2);
            ArrayList<Route> mutatedOffspring = offspringMutation(offspring);
            evaluatePopulation(mutatedOffspring);
            population = survivorSelection(mutatedOffspring);
        }

        System.out.println("End off algorithm after " + generations + " generations");
        System.out.println("Best route for this run: " + globalBestRoute.toString() + " with a cost of " + globalBestRouteCost);
    }

    public ArrayList<Route> initialisePopulation(int populationSize) {
        ArrayList<Route> population = new ArrayList<Route>();
        for (int i = 0; i < populationSize; i++) {
            population.add(routeCalculator.generateRandomRoute(graph));
        }
        return population;
    }

    // Tournament selection
    public ArrayList<Route> parentSelection(ArrayList<Route> population) {
        ArrayList<Route> newPopulation = new ArrayList<Route>();
        int popSize = population.size();
        for (int i = 0; i < popSize; i += 2) {
            // Only select if i has neighbour to avoid ArrayOutOfBoundsException
            if (i + 1 < popSize) {
                Route parentA = population.get(i);
                Route parentB = population.get(i + 1);
                newPopulation.add(routeCalculator.getCostOfRoute(parentA) < routeCalculator.getCostOfRoute(parentB) ? parentA : parentB);
            } else {
                newPopulation.add(population.get(i));
            }
        }
        return newPopulation;
    }

    public ArrayList<Route> survivorSelection(ArrayList<Route> population) {
        return population;
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
            }
        }
        System.out.println("Best route for this generation: " + bestRoute.toString() + " with a fitness of " + bestCost);
        if (bestCost < globalBestRouteCost) {
            globalBestRoute = bestRoute;
            globalBestRouteCost = bestCost;
            System.out.println("A new lowest cost route has been discovered!" + "\n" + "Route " + globalBestRoute.toString() + " with a cost of " + globalBestRouteCost);
        }
    }

    // TODO: Run in uniform random distribution
    public ArrayList<Route> recombinePairsOfParents(ArrayList<Route> parents) {
        ArrayList<Route> offspring = new ArrayList<Route>();

        int popSize = parents.size();
        for (int i = 0; i < popSize; i += 2) {
            // Only select if i has neighbour to avoid ArrayOutOfBoundsException
            if (i + 1 < popSize) {
                Route[] recombinedParents = order1Crossover(parents.get(i), parents.get(i + 1));
                offspring.add(recombinedParents[0]);
                offspring.add(recombinedParents[1]);
            }
        }

        return offspring;
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
}
