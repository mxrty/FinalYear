package CS3910.coursework.strategy;

import CS3910.coursework.Order;
import CS3910.coursework.Problem;
import CS3910.coursework.Recipe;
import CS3910.coursework.Stock;

import java.util.ArrayList;

public abstract class Strategy {

    public abstract ArrayList<Recipe> solve(Problem problem);

    boolean solveable(Problem problem) {
        int maxLength = 0;
        for (Stock stock : problem.getStocks()) {
            int length = stock.getLength();
            if (length > maxLength) maxLength = length;
        }

        for (Order order : problem.getOrders()) {
            int orderLength = order.getLength();
            if (orderLength > maxLength) return false;
        }

        return true;
    }
}
