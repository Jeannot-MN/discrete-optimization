package org.jmn;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GeneticKnapsack {

    // Problem data
    static int W = 25; // Knapsack capacity
    static int n = 16; // Number of items
    static int[] v = {6, 8, 3, 4, 5, 9, 11, 12, 6, 8, 13, 15, 16, 13, 9, 25}; // Values
    static int[] w = {3, 5, 4, 7, 4, 10, 3, 6, 8, 14, 4, 9, 10, 11, 17, 12}; // Weights

    // GA parameters
    static int populationSize = 20;
    static double crossoverProbability = 1.0;
    static double mutationProbability = 0.001;
    static int maxIterations = 30;
    static int numChildrenPerIteration = 6;
    static int R = 50; // Penalty parameter

    public static void main(String[] args) {
        Random random = new Random();

        // Initialize the population
        List<int[]> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(generateRandomSolution(n, random));
        }
        
        //sort population by value
        population.sort(Comparator.comparingDouble(GeneticKnapsack::calculateFitness).reversed());
        
        //print population
        System.out.println("Initial Sorted Population: ");
        for (int i = 0; i < populationSize; i++) {
            System.out.print(Arrays.toString(population.get(i)));
            System.out.println(", Fitness: " + calculateFitness(population.get(i)));
        }

        int[] bestSolution = null;
        double bestFitness = Double.NEGATIVE_INFINITY;

        // Genetic Algorithm loop
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            List<int[]> newPopulation = new ArrayList<>();

            // Select parents and generate children via crossover and mutation
            while (newPopulation.size() < numChildrenPerIteration) {
                int[] parent1 = selectParent(population, random);
                int[] parent2 = selectParent(population, random);

                int[] child = crossover(parent1, parent2, random);

                if (random.nextDouble() < mutationProbability) {
                    mutate(child, random);
                }

                newPopulation.add(child);
            }

            // Add the children to the population and evaluate
            population.addAll(newPopulation);

            for (int[] individual : population) {
                double fitness = calculateFitness(individual);

                if (fitness > bestFitness) {
                    bestFitness = fitness;
                    bestSolution = individual.clone();
                }
            }

            // Sort by fitness and retain only the best N individuals
            population.sort(Comparator.comparingDouble(GeneticKnapsack::calculateFitness).reversed());
            while (population.size() > populationSize) {
                population.remove(population.size() - 1);
            }

            System.out.println("Iteration " + (iteration + 1) + ": Best fitness = " + bestFitness);
        }

        // Output the best solution found
        System.out.println("Best solution: " + Arrays.toString(bestSolution));
        System.out.println("Best fitness: " + bestFitness);
    }

    // Generate a random binary solution
    public static int[] generateRandomSolution(int n, Random random) {
        int[] solution = new int[n];
        for (int i = 0; i < n; i++) {
            solution[i] = random.nextInt(2); // 0 or 1
        }
        return solution;
    }

    // Calculate the fitness function F(x) = f(x) - R * penalty
    public static double calculateFitness(int[] x) {
        int totalValue = 0;
        int totalWeight = 0;

        for (int i = 0; i < n; i++) {
            totalValue += x[i] * v[i];
            totalWeight += x[i] * w[i];
        }

        // Calculate penalty
        int penalty = Math.max(0, totalWeight - W);
        return totalValue - R * penalty;
    }

    // Crossover between two parents to produce a child
    public static int[] crossover(int[] parent1, int[] parent2, Random random) {
        int[] child = new int[n];
        for (int i = 0; i < n; i++) {
            child[i] = (random.nextDouble() < 0.5) ? parent1[i] : parent2[i];
        }
        return child;
    }

    // Mutate a child by flipping one random bit
    public static void mutate(int[] individual, Random random) {
        int mutationPoint = random.nextInt(n);
        individual[mutationPoint] = 1 - individual[mutationPoint]; // Flip bit
    }

    // Select a parent using tournament selection
    public static int[] selectParent(List<int[]> population, Random random) {
        int tournamentSize = 3;
        int[] best = null;
        double bestFitness = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < tournamentSize; i++) {
            int[] candidate = population.get(random.nextInt(population.size()));
            double fitness = calculateFitness(candidate);
            if (fitness > bestFitness) {
                best = candidate;
                bestFitness = fitness;
            }
        }
        return best;
    }
}
