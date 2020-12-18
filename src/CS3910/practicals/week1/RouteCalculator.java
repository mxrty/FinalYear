package CS3910.practicals.week1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class RouteCalculator {

    public RouteCalculator() {
    }

    // routeString to be provided in csv format
    public Route createRoute(Graph graph, String routeString) {
        String[] routeArr = routeString.split(",");
        Route route = new Route();
        Hashtable<String, Node> graphNodes = graph.getNodes();
        for (String city : routeArr) {
            if (graphNodes.containsKey(city)) {
                route.addStop(graphNodes.get(city));
            }
        }
        return route;
    }

    public double getCostOfRoute(Route route) {
        ArrayList<Node> routeList = route.getRouteList();
        double sum = 0;
        if (routeList.size() == 0) {
            return sum;
        }
        Node first = routeList.get(0);

        for (int i = 0; i < routeList.size() - 1; i++) {
            sum += (double) routeList.get(i).getNeighbours().get(routeList.get(i + 1));
        }

        sum += (double) routeList.get(routeList.size() - 1).getNeighbours().get(first);

        return sum;
    }

    public Route generateRandomRoute(Graph graph) {

        ArrayList<Node> listToShuffle = new ArrayList<Node>();
        Route route = new Route();

        for (Node node : graph.getNodes().values()) {
            listToShuffle.add(node);
        }

        Collections.shuffle(listToShuffle);

        for (Node node : listToShuffle) {
            route.addStop(node);
        }

        return route;
    }

    // 3. 2-opt neighbourhood
    public ArrayList<Route> generate2optNeighbourhood(Route route) {
        ArrayList<Route> neighbourhood = new ArrayList<Route>();
        ArrayList<Node> routeList = route.getRouteList();
        for (int i = 0; i < routeList.size(); i++) {
            for (int j = 0; j < routeList.size(); j++) {
                if (i != j) {
                    ArrayList<Node> swappedRoute = (ArrayList<Node>) routeList.clone();
                    Collections.swap(swappedRoute, i, j);
                    neighbourhood.add(new Route(swappedRoute));
                }
            }
        }

        return neighbourhood;
    }

    // 4. Best neighbour step function
    public Route findBestNeighbour(ArrayList<Route> neighbourhood) {
        Route bestNeighbour = neighbourhood.get(0);
        double bestNeighbourCost = getCostOfRoute(bestNeighbour);

        for (int i = 1; i < neighbourhood.size(); i++) {
            Route current = neighbourhood.get(i);
            if (bestNeighbourCost > getCostOfRoute(current)) {
                bestNeighbour = current;
            }
        }

        return bestNeighbour;
    }
}
