package org.jmn;

import java.util.*;

public class VariableFlippingAlgorithm {
    static int n = 16; // Number of variables
    static int[] v = {6, 8, 3, 4, 5, 9, 11, 12, 6, 8, 13, 15, 16, 13, 9, 25}; // Values
    static int[] w = {3, 5, 4, 7, 4, 10, 3, 6, 8, 14, 4, 9, 10, 11, 17, 12}; // Weights
    static int W = 25; // Maximum weight
    static int R = 50; // Penalty multiplier

    public static void main(String[] args) {
        int[] x0 = generateRandomSolution(); // Initial solution x0
        int fX0 = calculateValue(x0);
        int[] xmax = x0.clone();
        int fXMax = fX0;
        boolean flag = true;
        int Pass = 1;

        while (flag) {
            flag = false;
            int epoch = 0;
            List<Integer> F = new ArrayList<>();
            List<Integer> L = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                F.add(i); // Initialize F with all variable indices
            }

            while (epoch < n) {
                epoch++;
                // Step 3.1.2: Calculate xj for each j in F by flipping xj and find f(xj)
                int bestFIndex = -1;
                int bestFValue = Integer.MIN_VALUE;

                for (int j : F) {
                    int[] xj = flipVariable(x0, j); // Flip the j-th variable in x0
                    int fXj = calculateValue(xj);

                    // Step 3.1.3: Find the best f(xj)
                    if (fXj > bestFValue) {
                        bestFValue = fXj;
                        bestFIndex = j;
                    }
                }

                // Update xt, F and L based on the best flip
                x0 = flipVariable(x0, bestFIndex); // xt = arg max f(xj)
                F.remove((Integer) bestFIndex); // Remove bestFIndex from F
                L.add(bestFIndex); // Add bestFIndex to L
            }

            // Step 3.2: Find the best solution from locked variables (L)
            int fXBestEpoch = calculateValue(x0);
            if (fXBestEpoch > fXMax) {
                xmax = x0.clone();
                fXMax = fXBestEpoch;
                flag = true; // Set flag to true for the next pass
                Pass++;
            }
        }

        System.out.println("Best solution found: " + Arrays.toString(xmax));
        System.out.println("Value of the best solution: " + fXMax);
    }

    // Generate a random initial solution
    static int[] generateRandomSolution() {
        Random random = new Random();
        int[] solution = new int[n];
        for (int i = 0; i < n; i++) {
            solution[i] = random.nextInt(2); // Each variable is either 0 or 1
        }
        return solution;
    }

    // Flip the j-th variable in the solution x
    static int[] flipVariable(int[] x, int j) {
        int[] xFlipped = x.clone();
        xFlipped[j] = 1 - xFlipped[j]; // Flip 0 to 1 or 1 to 0
        return xFlipped;
    }

    // Calculate the value of the solution x, with penalty if weight exceeds W
    static int calculateValue(int[] x) {
        int value = 0, weight = 0;
        for (int i = 0; i < n; i++) {
            value += x[i] * v[i];
            weight += x[i] * w[i];
        }

        int penalty = weight > W ? (weight - W) * R : 0;
        return value - penalty;
    }
}

