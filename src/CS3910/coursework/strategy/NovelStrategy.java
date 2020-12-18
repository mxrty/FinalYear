package CS3910.coursework.strategy;

import CS3910.coursework.Problem;
import CS3910.coursework.Recipe;
import CS3910.coursework.Solution;
import CS3910.coursework.Stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NovelStrategy extends Strategy {

    private double globalBestSolutionCost = Double.MAX_VALUE;
    private double averageRandomSolutionCost;
    private Solution globalBestSolution;
    private int generations;
    private Random random = new Random();
    private Problem problem;

    /*
    Representation
    --------------------------
    Fitness evaluation        : total cost of recipes (smaller = better)
    Recombination             : crossover of equivalent recipes
    Recombination probability : 100%
    Mutation                  : Select a subset of recipes from a solution, create a new set of recipes that produce the same length, replace subset with new set
    Mutation probability      : 50%
    Parent selection          : random selection of first parent, elitism for second parent; a number of second parent contenders are picked at random, where the number picked is larger the fitter the first parent is
    Survivor selection        : elitism, generational
    Population size           : 100
    Initialisation            : random
    Termination               : 100 generations
     */
    public NovelStrategy(Problem problem, int generations) {
        this.problem = problem;
        this.generations = generations;
    }

    @Override
    public Solution solve(ArrayList<Solution> initialPopulation) {
        if (!solveable(problem)) {
            return new Solution();
        }
        ArrayList<Solution> population = new ArrayList<>(initialPopulation);
        //System.out.println("Novel:");
        evaluatePopulation(population);
        for (int i = 0; i < generations; i++) {
            averageRandomSolutionCost = calculateAverageRandomSolutionCost(population);
            ArrayList<Solution> parents = selectParents(population);
            ArrayList<Solution> offspring = produceOffspring(parents);
            mutateOffspring(offspring, 0.5, 2);
            evaluatePopulation(offspring);
            population = selectNextGeneration(population, offspring);
        }
        return globalBestSolution;
    }

    private double calculateAverageRandomSolutionCost(ArrayList<Solution> population) {
        double sum = 0;
        for (Solution solution : population) {
            sum += solution.getCost();
        }
        return sum / population.size();
    }

    /*
    Returns a list of even size, where every 2 parent solutions are a mating pair.
    Mating pairs consist of a randomly selected 'female', and the 'male' winner of a randomly selected competition
    The competition size depends on the fitness of the female, the fitter she is, the more males in competition.
    If a female is below average the competition size = 1, if she is above average, competition size=2-5 depending on how above average
     */
    private ArrayList<Solution> selectParents(ArrayList<Solution> population) {
        ArrayList<Solution> parents = new ArrayList<>();
        for (int i = 0; i < population.size() / 2; i++) {
            int femaleIndex = random.nextInt(population.size());
            Solution parentFemale = population.get(femaleIndex);
            double femDiffFromAvg = averageRandomSolutionCost - parentFemale.getCost();
            int tournamentSize;
            if (femDiffFromAvg < 0) {
                tournamentSize = 1;
            } else {
                double interpolation = (femDiffFromAvg) / (averageRandomSolutionCost - globalBestSolutionCost);
                tournamentSize = ((int) Math.round(interpolation * 3)) + 2;
            }

            int maleIndex = femaleIndex;
            ArrayList<Integer> maleIndices = new ArrayList<>();
            // Pick males randomly
            for (int j = 0; j < tournamentSize; j++) {
                //Ensures parents are unique
                while (femaleIndex == maleIndex || maleIndices.contains(maleIndex)) {
                    maleIndex = random.nextInt(population.size());
                }
                maleIndices.add(maleIndex);
            }
            int bestMaleIndex = maleIndices.get(0);
            // Select best male
            for (int j = 1; j < maleIndices.size(); j++) {
                if (population.get(maleIndices.get(j)).getCost() < population.get(bestMaleIndex).getCost()) {
                    bestMaleIndex = maleIndices.get(j);
                }
            }
            Solution parentMale = population.get(bestMaleIndex);

            parents.add(parentFemale);
            parents.add(parentMale);
        }

        return parents;
    }

    private ArrayList<Solution> produceOffspring(ArrayList<Solution> parents) {
        ArrayList<Solution> offspring = new ArrayList<>();

        for (int i = 0; i < parents.size(); i += 2) {
            Solution[] children = recipeCrossover(parents.get(i), parents.get(i + 1));
            for (int j = 0; j < children.length; j++) {
                offspring.add(children[j]);
            }
        }
        return offspring;
    }

    /*
    Crossover:
    - Take a set of recipes from parentA, find a set of recipes from parentB that produces the same lengths, swap sets
     */
    private Solution[] recipeCrossover(Solution parentA, Solution parentB) {
        Solution[] children = new Solution[2];
        ArrayList<Recipe> childARecipes = new ArrayList<>(parentA.getList());
        ArrayList<Recipe> childBRecipes = new ArrayList<>(parentB.getList());

        ArrayList<Integer> equivalentIndexes = new ArrayList<>();

        for (Recipe recipeA : childARecipes) {
            int equivalentIndex = -1;
            for (int i = 0; i < childBRecipes.size(); i++) {
                if (recipeA.equivalent(childBRecipes.get(i)) && !equivalentIndexes.contains(i)) {
                    equivalentIndex = i;
                    break;
                }
            }
            equivalentIndexes.add(equivalentIndex);
        }

        ArrayList<Recipe> spliceA = new ArrayList<>();
        ArrayList<Recipe> spliceB = new ArrayList<>();

        for (int i = 0; i < equivalentIndexes.size(); i++) {
            int parentBIndex = equivalentIndexes.get(i);
            if (parentBIndex > -1) {
                spliceA.add(childARecipes.get(i));
                spliceB.add(childBRecipes.get(parentBIndex));
            }
        }

        for(Recipe recipe : spliceA){
            childARecipes.remove(recipe);
        }
        for(Recipe recipe : spliceB){
            childBRecipes.remove(recipe);
        }

        childARecipes.addAll(spliceB);
        childBRecipes.addAll(spliceA);

        children[0] = new Solution(childARecipes);
        children[1] = new Solution(childBRecipes);

        return children;
    }

    private void mutateOffspring(ArrayList<Solution> offspring, double probability, int mutationSize) {
        for (Solution solution : offspring) {
            if (random.nextDouble() < probability) {
                ArrayList<Recipe> recipesToMutate = new ArrayList<>();
                for (int i = 0; i < mutationSize; i++) {
                    recipesToMutate.add(solution.removeRecipe(random.nextInt(solution.size())));
                }
                solution.addAll(recreateRecipes(recipesToMutate));
            }
        }
    }

    private ArrayList<Recipe> recreateRecipes(ArrayList<Recipe> recipes) {
        ArrayList<Recipe> recreatedRecipes = new ArrayList<>();
        ArrayList<Integer> subOrder = new ArrayList<>();
        for (Recipe recipe : recipes) {
            subOrder.addAll(recipe.getProducedLengths());
        }
        Collections.shuffle(subOrder);
        Recipe currentRecipe = new Recipe(getRandomStock(problem.getStocks()));
        for (int i : subOrder) {
            while (!currentRecipe.add(i)) {
                if (!currentRecipe.isEmpty()) {
                    recreatedRecipes.add(currentRecipe);
                }
                currentRecipe = new Recipe(getRandomStock(problem.getStocks()));
            }
        }

        if (!currentRecipe.isEmpty()) {
            recreatedRecipes.add(currentRecipe);
        }

        return recreatedRecipes;
    }


    private Stock getRandomStock(ArrayList<Stock> stocks) {
        return stocks.get(random.nextInt(stocks.size()));
    }

    private ArrayList<Solution> selectNextGeneration(ArrayList<Solution> prevGeneration, ArrayList<Solution> offspring) {
        ArrayList<Solution> nextGeneration = new ArrayList<>();

        int fromPrevGeneration = prevGeneration.size() / 5;
        nextGeneration.addAll(getTopSolutions(prevGeneration, fromPrevGeneration));
        nextGeneration.addAll(getTopSolutions(offspring, prevGeneration.size() - fromPrevGeneration));

        return nextGeneration;
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
