package CS3910.practicals.week1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CsvParser {

    public CsvParser() {
    }

    public Graph generateGraphFromCsv(String filePath) {
        Graph graph = new Graph();

        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(filePath));
            String row;
            while ((row = csvReader.readLine()) != null) {
                // Add all nodes to graph
                String[] data = row.split(",");
                try {
                    String city = data[0];
                    double x = Double.parseDouble(data[1]);
                    double y = Double.parseDouble(data[2]);
                    graph.addNode(new Node(city, x, y));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add edges between nodes
        for (Node node : graph.getNodes().values()) {
            for (Node neighbour : graph.getNodes().values()) {
                if (!node.equals(neighbour)) {
                    double diffX = Math.abs(node.getX() - neighbour.getX());
                    double diffY = Math.abs(node.getY() - neighbour.getY());
                    double weight = Math.sqrt((diffX * diffX) + (diffY * diffY));
                    node.addNeighbour(neighbour, weight);
                }
            }
        }

        return graph;
    }
}
