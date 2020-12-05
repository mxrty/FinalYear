package CS3910.coursework;

import java.util.ArrayList;

public class Recipe {

    private final Stock stock;
    private int remainingLength;
    private ArrayList<Integer> producedLengths;

    public Recipe(Stock stock) {
        this.stock = stock;
        remainingLength = stock.getLength();
        producedLengths = new ArrayList<Integer>();
    }

    private boolean add(int length) {
        if (length <= remainingLength) {
            remainingLength -= length;
            producedLengths.add(length);
            return true;
        }
        return false;
    }

    public double getCost() {
        return stock.getCost();
    }

    @Override
    public String toString() {
        return "Input: " + stock.getLength() + ". Output: " + producedLengths + ". Cost: " + stock.getCost();
    }
}
