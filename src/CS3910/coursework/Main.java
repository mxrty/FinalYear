package CS3910.coursework;

import CS3910.coursework.strategy.BaselineStrategy;
import CS3910.coursework.strategy.NovelStrategy;
import CS3910.coursework.strategy.Strategy;

import java.util.ArrayList;

public class Main {
    public Problem problem;
    public Strategy strategy;

    /*
    Initialise problem with random solution
    Pass initial solution to strategy
        Each solution iteration modifies recipes
    Evaluate terminal solution
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.initialise();
        main.executeStrategy();
    }

    public void initialise() {
        problem = new Problem();
        strategy = new BaselineStrategy();
        //strategy = new NovelStrategy();
    }

    public void executeStrategy() {
        ArrayList<Recipe> result = strategy.solve(problem);
        if (!result.isEmpty()) {
            double totalCost = 0;
            for (Recipe recipe : result) {
                totalCost += recipe.getCost();
                System.out.println(recipe);
            }
            System.out.println("Total cost of recipes: " + totalCost);
        } else {
            System.out.println("Could not solve given problem.");
        }
    }
}
