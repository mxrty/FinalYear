package CS3910.coursework;

public class Stock {

    private final int length;
    private final double cost;

    public Stock(int length, double cost) {
        this.length = length;
        this.cost = cost;
    }

    public int getLength() {
        return length;
    }

    public double getCost() {
        return cost;
    }
    
}
