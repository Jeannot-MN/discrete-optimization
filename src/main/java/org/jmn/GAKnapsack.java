package org.jmn;

import java.util.Random;

public class GAKnapsack {

    // Constants for the problem
    static int W = 25;
    static int n = 16;
    static int N = 20;
    static int m = 6;
    static int max_iteration = 30;
    static int R = 50;
    static double pc = 1.0;
    static double pu = 0.001;

    static int[] v = {6, 8, 3, 4, 5, 9, 11, 12, 6, 8, 13, 15, 16, 13, 9, 25}; // Values
    static int[] w = {3, 5, 4, 7, 4, 10, 3, 6, 8, 14, 4, 9, 10, 11, 17, 12}; // Weights
    
    static int[] x = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; // Solution
    
    static int[] y = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    static int[] temp = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    
    static int[][] child = new int[m][n+1];
    
    
    static int[][] population = new int[N][n+1];
    
    public static void main(String[] args) {
        // Initialize the population
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            population[i] = generateRandomSolution(n, random);
        }
        
        // sort population by value
        sortPopulation(population);
        
        //print population
        System.out.println("Initial Sorted Population: ");
        printPopulation();

        // Genetic Algorithm loop
        int[] p= new int[2];
        int n1 = 0, n2;
        for (int iteration = 0; iteration < max_iteration; iteration++) {
            for (int k = 0; k < m; k+=2) {
                for (int j = 0; j < 2; j++) {
                    n1 = n2 = 0;
                    while (n1 == n2) {
                        n1 = random.nextInt(N);
                        n2 = random.nextInt(N);
                    }
                    
                    p[j] = population[n1][n] > population[n2][n] ? n1 : n2;
                }

                // Crossover Operation
                for (int j = 0; j < n; j++) {
                    x[j] = population[p[0]][j];
                    y[j] = population[p[1]][j];
                    temp[j] = x[j];
                }
                
                n1 = n2 = 0;
                while (n1 == n2) {
                    n1 = random.nextInt(n);
                    n2 = random.nextInt(n);
                }
                
                if (n1 < n2) {
                    for (int l = n1; l <= n2; l++) {
                        x[l] = y[l];
                        y[l] = temp[l];
                    }
                } else {
                    for (int l = n2; l <= n1; l++) {
                        x[l] = y[l];
                        y[l] = temp[l];
                    }
                }
                
                // Mutation Operation
                for (int j = 0; j < n; j++) {
                    if(random.nextDouble() < pu) {
                        x[j] = 1 - x[j];
                    }
                    
                    if(random.nextDouble() < pu) {
                        y[j] = 1 - y[j];
                    }
                }
                
                for (int j = 0; j < n; j++) {
                    child[k][j] = x[j];
                    child[k+1][j] = y[j];
                }
                child[k][n] = calculateValue(x);
                child[k+1][n] = calculateValue(y);
            }
            
            //Elitism process
            for(int j=0; j<m; j++) {
                for (int l=0; l <=n; l++) {
                    population[j][l] = child[j][l];
                }
            }
        }
        
        // Sort population by value
        sortPopulation(population);

        System.out.println("\n\nFinal Sorted Population: ");
        printPopulation();
    }

    private static void printPopulation() {
        for (int i = 0; i < N; i++) {
            int totalWeight = 0;
            int totalValue = 0;
            for (int j = 0; j < n; j++) {
                System.out.print(population[i][j] + " ");
                totalWeight += population[i][j] * w[j];
                totalValue += population[i][j] * v[j];
            }
            System.out.println(" | Weight: " + totalWeight + " | Value: " + totalValue + " | Fitness: " + population[i][n]);
        }
    }

    public static int calculateValue(int [] x) {
        int value =0, phi = 0, weight = 0;
        for (int i = 0; i < n; i++) {
            value += x[i] * v[i];
            weight += x[i] * w[i];
        }
        
        phi = weight > W ? weight - W : 0;
        
        return value - R * phi;
    }
    
    static int [] generateRandomSolution(int n, Random random) {
        int[] x = new int[n + 1];
        for (int i = 0; i < n; i++) {
            x[i] = random.nextInt(2);
        }
        
        x[n] = calculateValue(x);
        return x;
    }
    
    static void sortPopulation(int[][] population) {
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (population[i][n] > population[j][n]) {
                    int[] temp = population[i];
                    population[i] = population[j];
                    population[j] = temp;
                }
            }
        }
    }
}
