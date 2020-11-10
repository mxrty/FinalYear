package CS3910.practicals.week1;

import java.util.ArrayList;

public class Route {

    private ArrayList<Node> routeList;

    public Route() {
        routeList = new ArrayList<Node>();
    }

    public Route(ArrayList<Node> route) {
        this.routeList = route;
    }

    public void addStop(Node node) {
        if (!routeList.contains(node)) {
            routeList.add(node);
        }
    }

    public ArrayList<Node> getRouteList() {
        return routeList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Node node : routeList) {
            sb.append(node.getName());
        }

        return sb.toString();
    }

}