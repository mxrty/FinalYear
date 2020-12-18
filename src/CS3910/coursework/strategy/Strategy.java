package CS3910.coursework.strategy;

import CS3910.coursework.*;

import java.util.ArrayList;

public abstract class Strategy {

    public abstract Solution solve(ArrayList<Solution> initialPopulation);

    static boolean solveable(Problem problem) {
        int maxLength = 0;
        for (Stock stock : problem.getStocks()) {
            int length = stock.getLength();
            if (length > maxLength) maxLength = length;
        }

        for (Order order : problem.getOrders()) {
            int orderLength = order.getLength();
            if (orderLength > maxLength) {
                return false;
            }
        }

        return true;
    }
}
