package org.jmn;

import java.util.Map;

public class HeuristicTSP {

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
                .from(city_1)
                .to(city_3)
                .to(city_5)
                .to(city_7)
                .to(city_4)
                .to(city_6)
                .to(city_2)
                .to(city_1);

        System.out.println("Initial tour cost: " + initialTour.cost);
    
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

class TourNode {
    Node node;
    TourNode next;
    
    public TourNode(Node node, TourNode next) {
        this.node = node;
        this.next = next;
    }
}
class Tour {
    TourNode start;
    private TourNode lastNode;
    
    float cost;
    
    public Tour from(Node node){
        start = new TourNode(node, null);
        lastNode = start;
        return this;
    }
    
    public Tour to(Node node){
        lastNode.next = new TourNode(node, null);
        cost += lastNode.node.edges.get(node.label);
        lastNode = lastNode.next;
        return this;
    }
}
