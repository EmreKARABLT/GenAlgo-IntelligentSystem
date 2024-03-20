package Knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Knapsack {
    Random random = new Random();
    public ArrayList<KnapsackItem> sack;
    public static int limit ;
    public static ArrayList<KnapsackItem> items = new ArrayList<>();

    public Knapsack(){
        this.sack = new ArrayList<>();
    }
    // First, exceeding the limits of the knapsack is allowed
    // then if the genome exceeds the limit randomly selected item will be removed until it doesnt goes over the limit
    public void simulate(IndividualKnapsack individual){
        int[] genome = individual.getGene();
        for (int i = 0; i < genome.length ; i++) {
            if(genome[i] == 1) {
                sack.add(items.get(i));

            }
        }
         while(this.getWeight()> limit){
            KnapsackItem knapsackItem = sack.get(random.nextInt(sack.size()));
            int index = items.indexOf(knapsackItem);
            individual.changeGene(index , 0);
            sack.remove(knapsackItem);
        }

        individual.setFitness(this.getValue());

    }

    public int getWeight(){
        int weight = 0 ;
        for (KnapsackItem knapsackItem : this.sack) {
            weight += knapsackItem.getWeight();
        }
        return weight;
    }
    public int getValue(){
        int value = 0 ;
        for (KnapsackItem knapsackItem : this.sack) {
            value += knapsackItem.getValue();
        }
        return value;
    }

    public int simulate_brute_force(int[] genome){
        sack = new ArrayList<>();
        for (int i = 0; i < genome.length ; i++) {
            if(genome[i] == 1) {
                sack.add(items.get(i));
            }
        }
        if(this.getWeight()> limit){
          return 0;
        }
        return this.getValue();



    }
    public int[] decimalToBinary(int decimal, int n) {
        int[] binary = new int[n];
        int index = n - 1;

        while (decimal > 0) {
            binary[index--] = decimal % 2;
            decimal /= 2;
        }

        return binary;
    }
    public int bruteForce(int n){
        int[] binaryArray = new int[n]; // Initialize an array to store binary representation
        int best_value = Integer.MIN_VALUE;
        for (int i = 0; i < Math.pow(2, n); i++) {
            int[] binary = decimalToBinary(i, n); // Convert decimal to binary
            System.arraycopy(binary, 0, binaryArray, 0, n); // Copy binary representation to binaryArray
            int value = simulate_brute_force(binary); // Print binary representation
            if(value > best_value) best_value = value;

        }
        return best_value;
    }

    public static void main(String[] args) {


    }

}
