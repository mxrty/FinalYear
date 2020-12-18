package CS3910.coursework.strategy;

import CS3910.coursework.Problem;
import CS3910.coursework.Recipe;
import CS3910.coursework.Solution;
import CS3910.coursework.Stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;

public class BaselineStrategy extends Strategy {

    private double globalBestSolutionCost = Double.MAX_VALUE;
    private Solution globalBestSolution;
    private int generations;
    private Random random = new Random();
    private Problem problem;
    private int repaired = 0;

    /*
    Representation
    --------------------------
    Fitness evaluation        : total cost of recipes (smaller = better)
    Recombination             : position based crossover
    Recombination probability : 100%
    Mutation                  : Change stock length used for randomly selected recipe
    Mutation probability      : 50%
    Parent selection          : tournament, size 2
    Survivor selection        : elitism
    Population size           : 100
    Initialisation            : random
    Termination               : 100 generations
     */

    public BaselineStrategy(Problem problem, int generations) {
        this.problem = problem;
        this.generations = generations;
    }

    @Override
    public Solution solve(ArrayList<Solution> initialPopulation) {
        if (!solveable(problem)) {
            return new Solution();
        }
        ArrayList<Solution> population = new ArrayList<>(initialPopulation);
        //System.out.println("Baseline:");
        evaluatePopulation(population);
        for (int i = 0; i < generations; i++) {
            ArrayList<Solution> parents = selectParents(population);
            ArrayList<Solution> offspring = produceOffspring(parents);
            mutateOffspring(offspring, 0.5);
            evaluatePopulation(offspring);
            population = selectNextGeneration(population, offspring);
        }
        //System.out.println("Repaired: " + repaired);
        return globalBestSolution;
    }

    private ArrayList<Solution> selectNextGeneration(ArrayList<Solution> prevGeneration, ArrayList<Solution> offspring) {
        ArrayList<Solution> nextGenerationCandidates = new ArrayList<>();
        nextGenerationCandidates.addAll(prevGeneration);
        nextGenerationCandidates.addAll(offspring);

        return getTopSolutions(nextGenerationCandidates, prevGeneration.size());
    }

    private void mutateOffspring(ArrayList<Solution> offspring, double probability) {
        for (Solution solution : offspring) {
            if (random.nextDouble() < probability) {
                int index = random.nextInt(solution.size());
                Recipe recipe = solution.getList().get(index);
                Stock stock = getRandomStock(problem.getStocks());
                while (stock.getLength() < recipe.getTotalProducedLength()) {
                    stock = getRandomStock(problem.getStocks());
                }
                Recipe mutatedRecipe = new Recipe(stock);
                for (int i : recipe.getProducedLengths()) {
                    mutatedRecipe.add(i);
                }
                solution.getList().set(index, mutatedRecipe);
            }
        }
    }

    private Stock getRandomStock(ArrayList<Stock> stocks) {
        return stocks.get(random.nextInt(stocks.size()));
    }

    private ArrayList<Solution> produceOffspring(ArrayList<Solution> parents) {
        ArrayList<Solution> offspring = new ArrayList<>();

        for (int i = 0; i < parents.size(); i += 2) {
            Solution[] children = positionBasedCrossover(parents.get(i), parents.get(i + 1));
            for (int j = 0; j < children.length; j++) {
                children[j] = validateAndRepairSolution(children[j]);
                offspring.add(children[j]);
            }
        }
        return offspring;
    }

    private void repairSolution(Solution brokenSolution, Hashtable<Integer, Integer> missingLengths) {
        repaired++;
        Recipe currentRecipe = new Recipe(getRandomStock(problem.getStocks()));
        for (int length : missingLengths.keySet()) {
            for (int i = 0; i < missingLengths.get(length); i++) {
                while (!currentRecipe.add(length)) {
                    if (!currentRecipe.isEmpty()) {
                        brokenSolution.add(currentRecipe);
                    }
                    currentRecipe = new Recipe(getRandomStock(problem.getStocks()));
                }
            }
        }
        if (!currentRecipe.isEmpty()) {
            brokenSolution.add(currentRecipe);
        }
    }

    private Solution[] positionBasedCrossover(Solution parentA, Solution parentB) {
        Solution[] children = new Solution[2];

        final int smallerLength = parentA.size() > parentB.size() ? parentB.size() : parentA.size();

        final int spliceLength = random.nextInt(smallerLength / 2) + 1;
        final int spliceStart = random.nextInt(smallerLength - spliceLength);
        final int spliceEnd = spliceStart + spliceLength;

        Solution childA = new Solution(parentA);
        Solution childB = new Solution(parentB);

        //Store splice before removing
        ArrayList<Recipe> childASplice = new ArrayList<>(childA.getList().subList(spliceStart, spliceEnd));
        childA.getList().subList(spliceStart, spliceEnd).clear();
        ArrayList<Recipe> childBSplice = new ArrayList<>(childB.getList().subList(spliceStart, spliceEnd));
        childB.getList().subList(spliceStart, spliceEnd).clear();

        childA.addAll(childBSplice);
        childB.addAll(childASplice);

        children[0] = childA;
        children[1] = childB;

        return children;
    }

    private Solution validateAndRepairSolution(Solution solution) {
        Hashtable<Integer, Integer> lengthsRequired = new Hashtable<>(problem.getOrdersTable());
        for (Recipe recipe : solution.getList()) {
            for (int length : recipe.getProducedLengths()) {
                int quantity = lengthsRequired.get(length) - 1;
                lengthsRequired.put(length, quantity);
            }
        }
        for (int i : lengthsRequired.values()) {
            if (i > 0) {
                repairSolution(solution, lengthsRequired);
                return solution;
            }
        }
        return solution;
    }

    private ArrayList<Solution> selectParents(ArrayList<Solution> population) {
        ArrayList<Solution> parents = new ArrayList<>();
        for (int i = 0; i < population.size() / 2; i++) {
            //0-1 is parentA candidates indices, 2-3 is parentB candidates indices
            ArrayList<Integer> parentCandidates = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                int index = random.nextInt(population.size());
                while (parentCandidates.contains(index)) {
                    index = random.nextInt(population.size());
                }
                parentCandidates.add(index);
            }

            Solution parentA1 = population.get(parentCandidates.get(0));
            Solution parentA2 = population.get(parentCandidates.get(1));

            Solution parentA = parentA1.getCost() > parentA2.getCost() ? parentA2 : parentA1;

            Solution parentB1 = population.get(parentCandidates.get(2));
            Solution parentB2 = population.get(parentCandidates.get(3));

            Solution parentB = parentB1.getCost() > parentB2.getCost() ? parentB2 : parentB1;

            parents.add(parentA);
            parents.add(parentB);
        }

        return parents;
    }

    private void evaluatePopulation(ArrayList<Solution> population) {
        Solution bestSolution = population.get(0);
        double bestCost = bestSolution.getCost();

        for (int i = 1; i < population.size(); i++) {
            Solution current = population.get(i);
            double currentCost = current.getCost();
            if (currentCost < bestCost) {
                bestSolution = current;
                bestCost = currentCost;
            }
        }
        //System.out.print("Best solution for this generation: " + bestSolution + " with a cost of " + bestCost);
        if (bestCost < globalBestSolutionCost) {
            globalBestSolution = new Solution(bestSolution);
            globalBestSolutionCost = bestCost;
            //System.out.println(". This is the new lowest cost solution!!!");
        } else {
            //System.out.print("\n");
        }
    }

    /*
    Returns the top (quantity) solutions from the list in descending order of cost
     */
    private ArrayList<Solution> getTopSolutions(ArrayList<Solution> list, int quantity) {
        ArrayList<Solution> topSolutions = new ArrayList<>(list);

        // Sort in ascending order
        Collections.sort(topSolutions, (solution1, solution2) -> {
            double solution1Cost = solution1.getCost();
            double solution2Cost = solution2.getCost();
            if (solution1Cost < solution2Cost) {
                return -1;
            } else if (solution1Cost > solution2Cost) {
                return 1;
            } else {
                return 0;
            }
        });

        topSolutions.subList(quantity, list.size()).clear();

        return topSolutions;
    }
}
