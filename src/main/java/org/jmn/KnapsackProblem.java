package org.jmn;

import java.util.ArrayList;
import java.util.List;

public class KnapsackProblem {

    public static void main(String[] args) {
        final int MAX_CAPACITY = 30;
        List<Item> items = List.of(
                new Item(2, 7),
                new Item(6, 3),
                new Item(8, 3),
                new Item(7, 5),
                new Item(3, 4),
                new Item(4, 7),
                new Item(6, 5),
                new Item(5, 4),
                new Item(10, 15),
                new Item(9, 10),
                new Item(8, 17),
                new Item(11, 3),
                new Item(12, 6),
                new Item(15, 11),
                new Item(6, 6),
                new Item(8, 14),
                new Item(13, 4),
                new Item(14, 8),
                new Item(15, 9),
                new Item(16, 10),
                new Item(13, 14),
                new Item(14, 17),
                new Item(15, 9),
                new Item(26, 24),
                new Item(13, 11),
                new Item(9, 17),
                new Item(25, 12),
                new Item(26, 14)
        );
        
        List<Item> sortedItems = new ArrayList<>(items.stream().sorted((i1, i2) -> Float.compare(i2.efficiency(), i1.efficiency())).toList());

        System.out.println("Items sorted by efficiency:");
        sortedItems.forEach(System.out::println);
        
        List<Item> knapsack = new ArrayList<>();
        
        int remainingCapacity = MAX_CAPACITY;
        while (remainingCapacity > 0 && !sortedItems.isEmpty()) {
            Item item = sortedItems.get(0);
            if (item.weight <= remainingCapacity) {
                knapsack.add(item);
                remainingCapacity -= item.weight;
            }
            sortedItems.remove(0);
        }

        System.out.println("\n\nKnapsack Items:");
        knapsack.forEach(System.out::println);

        System.out.println("\n\nTotal weight:");
        System.out.println(knapsack.stream().mapToInt(i -> i.weight).sum());
        
        System.out.println("\n\nTotal profit:");
        System.out.println(knapsack.stream().mapToInt(i -> i.value).sum());
    }
}

class Item {
    int weight;
    int value;

    public Item(int value, int weight) {
        this.weight = weight;
        this.value = value;
    }

    public float efficiency() {
        return (float) value / weight;
    }

    @Override
    public String toString() {
        return "* Item{" +
                "value=" + value +
                ", weight=" + weight +
                ", efficiency=" + efficiency() +
                '}';
    }
}
