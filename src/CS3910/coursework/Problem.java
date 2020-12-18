package CS3910.coursework;

import java.util.ArrayList;
import java.util.Hashtable;

public class Problem {
    private ArrayList<Stock> stocks;
    private ArrayList<Order> orders;

    public Problem() {
        //inititialisePerformanceProblem1();
        inititialiseOptimisationProblem1();
        //inititialiseOptimisationProblem2();
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public Hashtable<Integer,Integer> getOrdersTable(){
        Hashtable<Integer,Integer> table = new Hashtable<>();

        for(Order order : orders){
            table.put(order.getLength(), order.getQuantity());
        }

        return table;
    }

    public void inititialisePerformanceProblem1() {
        stocks = new ArrayList<Stock>();
        stocks.add(new Stock(120, 12));
        stocks.add(new Stock(115, 11.5));
        stocks.add(new Stock(110, 11));
        stocks.add(new Stock(105, 10.5));
        stocks.add(new Stock(100, 10));

        orders = new ArrayList<Order>();
        orders.add(new Order(3, 5));
        orders.add(new Order(4, 2));
        orders.add(new Order(5, 1));
        orders.add(new Order(6, 2));
        orders.add(new Order(7, 4));
        orders.add(new Order(8, 2));
        orders.add(new Order(9, 1));
        orders.add(new Order(10, 3));
        orders.add(new Order(3, 5));
        orders.add(new Order(4, 2));
        orders.add(new Order(5, 1));
        orders.add(new Order(6, 2));
        orders.add(new Order(7, 4));
        orders.add(new Order(8, 2));
        orders.add(new Order(9, 1));
        orders.add(new Order(10, 3));
        orders.add(new Order(3, 5));
        orders.add(new Order(4, 2));
        orders.add(new Order(5, 1));
        orders.add(new Order(6, 2));
        orders.add(new Order(7, 4));
        orders.add(new Order(8, 2));
        orders.add(new Order(9, 1));
        orders.add(new Order(10, 3));
        orders.add(new Order(3, 5));
        orders.add(new Order(4, 2));
        orders.add(new Order(5, 1));
        orders.add(new Order(6, 2));
        orders.add(new Order(7, 4));
        orders.add(new Order(8, 2));
        orders.add(new Order(9, 1));
        orders.add(new Order(10, 3));
        orders.add(new Order(3, 5));
        orders.add(new Order(4, 2));
        orders.add(new Order(5, 1));
        orders.add(new Order(6, 2));
    }

    /*
    Solution cost: 1240
     */
    public void inititialiseOptimisationProblem1() {
        stocks = new ArrayList<Stock>();
        stocks.add(new Stock(10, 100));
        stocks.add(new Stock(13, 130));
        stocks.add(new Stock(15, 150));

        orders = new ArrayList<Order>();
        orders.add(new Order(3, 5));
        orders.add(new Order(4, 2));
        orders.add(new Order(5, 1));
        orders.add(new Order(6, 2));
        orders.add(new Order(7, 4));
        orders.add(new Order(8, 2));
        orders.add(new Order(9, 1));
        orders.add(new Order(10, 3));
    }

    /*
    Solution cost: 3998
     */
    public void inititialiseOptimisationProblem2() {
        stocks = new ArrayList<Stock>();
        stocks.add(new Stock(4300, 86));
        stocks.add(new Stock(4250, 85));
        stocks.add(new Stock(4150, 83));
        stocks.add(new Stock(3950, 79));
        stocks.add(new Stock(3800, 68));
        stocks.add(new Stock(3700, 66));
        stocks.add(new Stock(3550, 64));
        stocks.add(new Stock(3500, 63));

        orders = new ArrayList<Order>();
        orders.add(new Order(2350, 2));
        orders.add(new Order(2250, 4));
        orders.add(new Order(2200, 4));
        orders.add(new Order(2100, 15));
        orders.add(new Order(2050, 6));
        orders.add(new Order(2000, 11));
        orders.add(new Order(1950, 6));
        orders.add(new Order(1900, 15));
        orders.add(new Order(1850, 13));
        orders.add(new Order(1700, 5));
        orders.add(new Order(1650, 2));
        orders.add(new Order(1350, 9));
        orders.add(new Order(1300, 3));
        orders.add(new Order(1250, 6));
        orders.add(new Order(1200, 10));
        orders.add(new Order(1150, 4));
        orders.add(new Order(1100, 8));
        orders.add(new Order(1050, 3));
    }
}
