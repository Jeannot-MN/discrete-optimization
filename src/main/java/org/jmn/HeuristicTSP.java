package org.jmn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeuristicTSP {

    static void printTour(Tour tour) {
        int i;
        for (i = 0; i < tour.nodes.size() - 1; i++) {
            System.out.print(tour.nodes.get(i).label + " -> ");
        }
        System.out.print(tour.nodes.get(i).label);

        System.out.println("  =========> Cost = " + tour.cost());
    }

    static Tour swap(Tour tour, int i, int j) {
        List<Node> nodes = new ArrayList<>(tour.nodes);
        while (i < j) {
            Node temp = nodes.get(i);
            nodes.set(i, nodes.get(j));
            nodes.set(j, temp);
            i++;
            j--;
        }
        return new Tour(nodes);
    }

    public static void main(String[] args) {
        Node city_1 = new Node("1", Map.ofEntries(
                Map.entry("1", 0f),
                Map.entry("2", 1.5f),
                Map.entry("3", 3f),
                Map.entry("4", 13f),
                Map.entry("5", 3.5f),
                Map.entry("6", 4.5f),
                Map.entry("7", 1.5f))
        );
        Node city_2 = new Node("2", Map.ofEntries(
                Map.entry("1", 1.5f),
                Map.entry("2", 0f),
                Map.entry("3", 1.5f),
                Map.entry("4", 1.3f),
                Map.entry("5", 13f),
                Map.entry("6", 13f),
                Map.entry("7", 2.3f))
        );
        Node city_3 = new Node("3", Map.ofEntries(
                Map.entry("1", 3f),
                Map.entry("2", 1.5f),
                Map.entry("3", 0f),
                Map.entry("4", 1.5f),
                Map.entry("5", 3f),
                Map.entry("6", 13f),
                Map.entry("7", 3f))
        );
        Node city_4 = new Node("4", Map.ofEntries(
                Map.entry("1", 13f),
                Map.entry("2", 1.3f),
                Map.entry("3", 1.5f),
                Map.entry("4", 0f),
                Map.entry("5", 1.5f),
                Map.entry("6", 13f),
                Map.entry("7", 20f))
        );
        Node city_5 = new Node("5", Map.ofEntries(
                Map.entry("1", 3.5f),
                Map.entry("2", 13f),
                Map.entry("3", 3f),
                Map.entry("4", 1.5f),
                Map.entry("5", 0f),
                Map.entry("6", 1.5f),
                Map.entry("7", 3.3f))
        );
        Node city_6 = new Node("6", Map.ofEntries(
                Map.entry("1", 4.5f),
                Map.entry("2", 13f),
                Map.entry("3", 13f),
                Map.entry("4", 13f),
                Map.entry("5", 1.5f),
                Map.entry("6", 0f),
                Map.entry("7", 1.5f))
        );
        Node city_7 = new Node("7", Map.ofEntries(
                Map.entry("1", 1.5f),
                Map.entry("2", 2.3f),
                Map.entry("3", 3f),
                Map.entry("4", 20f),
                Map.entry("5", 3.3f),
                Map.entry("6", 1.5f),
                Map.entry("7", 0f))
        );

        Tour initialTour = new Tour()
                .add(city_1)
                .add(city_3)
                .add(city_5)
                .add(city_7)
                .add(city_4)
                .add(city_6)
                .add(city_2)
                .add(city_1);

        printTour(initialTour);

        Tour optimalTour = initialTour;

        int i = 1;
        int j = i + 2;
        int n = initialTour.nodes.size() - 1;
        while (i < n - 2) {
            Tour newTour = swap(optimalTour, i, j - 1);
            printTour(newTour);

            if (newTour.cost() < optimalTour.cost()) {
                optimalTour = newTour;
                i = 1;
                j = i + 2;
            }

            j++;

            if (j >= n) {
                i++;
                j = i + 2;
            }
        }

        System.out.println("\n\n\nOptimal Tour is :");
        printTour(optimalTour);
    }
}

class Node {
    String label;
    Map<String, Float> edges;

    public Node(String label, Map<String, Float> edges) {
        this.label = label;
        this.edges = edges;
    }
}

class Tour {

    List<Node> nodes = new ArrayList<>();

    public Tour() {
    }

    public Tour(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Tour add(Node node) {
        this.nodes.add(node);
        return this;
    }

    float cost() {
        float cost = 0;

        for (int i = 0; i < nodes.size() - 1; i++) {
            cost += nodes.get(i).edges.get(nodes.get(i + 1).label);
        }
        return Math.round(cost * 10.0f) / 10.0f;
    }
}
