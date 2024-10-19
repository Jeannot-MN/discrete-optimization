package org.jmn;

import java.util.*;

public class FiducciaMattheysesKnapsack {

    // Constants for the problem
    static int W = 25; // Total capacity
    static int n = 16; // Number of items
    static int[] v = {6, 8, 3, 4, 5, 9, 11, 12, 6, 8, 13, 15, 16, 13, 9, 25}; // values
    static int[] w = {3, 5, 4, 7, 4, 10, 3, 6, 8, 14, 4, 9, 10, 11, 17, 12}; // weights

    public static void main(String[] args) {
        // Random Initial solution x0
        int[] x0 = new int[n];
        for (int i = 0; i < n; i++) {
            x0[i] = Math.random() < 0.5 ? 0 : 1;
        }

        // Calculate f(x0) and initialize xmax
        int[] xmax = Arrays.copyOf(x0, n);
        int fmax = calculateF(xmax);

        // FM Algorithm variables
        int flag = 1;
        int pass = 1;
        
        while (flag == 1) {
            flag = 0;
            int epoch = 0;
            Set<Integer> F = new HashSet<>();
            for (int i = 0; i < n; i++) {
                F.add(i);
            }
            Set<Integer> L = new HashSet<>();

            while (epoch < n && !F.isEmpty()) {
                // Initialize best index and value in each epoch
                int bestIndex = -1;
                int bestValue = Integer.MIN_VALUE;

                // Flip each free variable and calculate f(x)
                for (int j : F) {
                    int[] flippedX = flipBit(x0, j);
                    int fValue = calculateF(flippedX);

                    if (fValue > bestValue) {
                        bestValue = fValue;
                        bestIndex = j;
                    }
                }

                // Ensure bestIndex is valid before proceeding
                if (bestIndex != -1) {
                    // Update x0 with the best found solution
                    x0[bestIndex] = 1 - x0[bestIndex]; // Flip the best index
                    F.remove(bestIndex); // Move to locked set
                    L.add(bestIndex);
                    epoch++;
                } else {
                    break; // Exit if no valid index was found
                }
            }

            // Evaluate the best solution at the end of the epoch
            int fCurrent = calculateF(x0);
            if (fCurrent > fmax) {
                xmax = Arrays.copyOf(x0, n);
                fmax = fCurrent;
                flag = 1;
                pass++;
            }
        }

        // Output the best solution found
        System.out.println("Best solution found: " + Arrays.toString(xmax));
        System.out.println("Maximum value: " + fmax);
    }

    
    public static int calculateF(int[] x) {
        int value =0, phi = 0, weight = 0;
        for (int i = 0; i < n; i++) {
            value += x[i] * v[i];
            weight += x[i] * w[i];
        }

        phi = weight > W ? weight - W : 0;

        return value - 1000 * phi;
    }

    public static int[] flipBit(int[] x, int j) {
        int[] newX = Arrays.copyOf(x, n);
        newX[j] = 1 - newX[j];
        return newX;
    }
}
