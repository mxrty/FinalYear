package CS3910.coursework;

import java.util.ArrayList;

public class Solution {

    private ArrayList<Recipe> solution;

    public Solution() {
        solution = new ArrayList<>();
    }

    public Solution(Solution solution) {
        this.solution = new ArrayList<>(solution.getList());
    }

    public Solution(ArrayList<Recipe> recipes){
        this.solution = recipes;
    }

    public void add(Recipe recipe) {
        solution.add(recipe);
    }

    public void addAll(ArrayList<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            this.add(recipe);
        }
    }

    public Recipe removeRecipe(int index) {
        Recipe removedRecipe = solution.remove(index);
        return removedRecipe;
    }

    public double getCost() {
        double cost = 0;
        for (Recipe recipe : solution) {
            cost += recipe.getCost();
        }
        return cost;
    }

    public int size() {
        return solution.size();
    }

    public ArrayList<Recipe> getList() {
        return solution;
    }

    public void printSolution(String title) {
        System.out.println(title + " solution:");
        if (!solution.isEmpty()) {
            double totalCost = 0;
            for (Recipe recipe : solution) {
                totalCost += recipe.getCost();
                System.out.println(recipe);
            }
            System.out.println("Total cost of recipes: " + totalCost);
        } else {
            System.out.println("Could not solve given problem.");
        }
        System.out.println("----------------------");
    }
}
