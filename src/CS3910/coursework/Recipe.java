package CS3910.coursework;

import java.util.ArrayList;
import java.util.Collections;

public class Recipe {

    private final Stock stock;
    private int remainingLength;
    private ArrayList<Integer> producedLengths;

    public Recipe(Stock stock) {
        this.stock = stock;
        remainingLength = stock.getLength();
        producedLengths = new ArrayList<Integer>();
    }

    public boolean add(int length) {
        if (length <= remainingLength) {
            remainingLength -= length;
            producedLengths.add(length);
            return true;
        }
        return false;
    }

    public boolean equivalent(Recipe recipe) {
        if (producedLengths.size() != recipe.getProducedLengths().size()) {
            return false;
        }
        // To not change the order of original recipes
        ArrayList<Integer> copyThis = new ArrayList<>(producedLengths);
        ArrayList<Integer> copyRecipe = new ArrayList<>(recipe.getProducedLengths());

        Collections.sort(copyThis);
        Collections.sort(copyRecipe);

        return copyThis.equals(copyRecipe);
    }


    public boolean isEmpty(){
        return producedLengths.isEmpty();
    }

    public ArrayList<Integer> getProducedLengths() {
        return producedLengths;
    }

    public int getTotalProducedLength(){
        return stock.getLength() - remainingLength;
    }

    public double getCost() {
        return stock.getCost();
    }

    @Override
    public String toString() {
        return "Input: " + stock.getLength() + ". Output: " + producedLengths + ". Cost: " + stock.getCost();
    }
}
