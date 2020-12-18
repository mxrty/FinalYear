package CS3910.coursework.strategy;

import CS3910.coursework.*;

import java.util.ArrayList;
import java.util.Random;

public class RandomStrategy {

    private Random random;

    public RandomStrategy() {
        random = new Random();
    }

    public Solution solve(Problem problem) {
        Solution solution = new Solution();

        if (!Strategy.solveable(problem)) {
            return solution;
        }

        ArrayList<Stock> stocks = problem.getStocks();
        for (Order order : problem.getOrders()) {
            createRandomRecipesForOrder(solution, stocks, order);
        }

        return solution;
    }

    private void createRandomRecipesForOrder(Solution solution, ArrayList<Stock> stocks, Order order) {
        int length = order.getLength();
        int quantity = order.getQuantity();
        Stock randomStock = getRandomStock(stocks);

        while (quantity > 0) {
            while (randomStock.getLength() < length) {
                randomStock = getRandomStock(stocks);
            }
            Recipe recipe = new Recipe(randomStock);
            while (recipe.add(length) && quantity > 0) {
                quantity--;
            }
            solution.add(recipe);
            randomStock = getRandomStock(stocks);
        }
    }

    private Stock getRandomStock(ArrayList<Stock> stocks) {
        return stocks.get(random.nextInt(stocks.size()));
    }

}
