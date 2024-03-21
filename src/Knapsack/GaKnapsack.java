package Knapsack;

import TSP.IndividualTSP;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class GaKnapsack {
    Random random = new Random();

    private int populationSize ;
    private int geneLength ;
    private int maxGen ;
    private double mutationRate ;
    ArrayList<IndividualKnapsack> population ;
    public GaKnapsack(ArrayList<IndividualKnapsack> population , int populationSize , int geneLength, double mutationRate, int maxGen){
        this.maxGen = maxGen;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.geneLength = geneLength;
        this.population = population;
        geneticAlgorithm();
    }
    public static ArrayList<IndividualKnapsack> initializePopulation( int populationSize , int geneLength){
        ArrayList<IndividualKnapsack> pop = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            int[] genome = new int[geneLength];
            for (int j = 0; j < geneLength; j++) {
                genome[j] = (int)(Math.round(Math.random()));
            }
            IndividualKnapsack individual = new IndividualKnapsack(genome);
            pop.add(individual);
        }
        return pop;


    }
    public void geneticAlgorithm(){

        int col1 = 20, col2 = 20, col3 = 20;
        int gen = 0;

        // Define the CSV file path
        String csvFilePath = String.format("ks_%f.csv", mutationRate) ;

        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFilePath))) {
            // Write CSV header
            pw.println("Gen,Average Fitness,Min");

            while (!checkStoppingConditionReached(population, 0.90, gen)) {
                ArrayList<IndividualKnapsack> matingPool = selection();
                population = generateNewGeneration(matingPool);


                // Write data to CSV
                String dataLine = String.format("%d,%f,%d", gen, averageFitness(population), population.stream().max(IndividualKnapsack::compareTo).stream().toList().get(0).getFitness());
                pw.println(dataLine);
                System.out.println(dataLine);
                gen++;
                if(gen == 1000) break;
            }
            // Write final data to CSV
            String finalDataLine = String.format("%d,%f,%d", --gen, averageFitness(population), population.stream().max(IndividualKnapsack::compareTo).stream().toList().get(0).getFitness());
            pw.println(finalDataLine);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(mutationRate + " completed");




    }
    public double averageFitness(ArrayList<IndividualKnapsack> population){
        double sum = population
                .stream().map(IndividualKnapsack::getFitness).reduce(0,Integer::sum);
        return sum / (double)(populationSize);
    }
    public boolean checkStoppingConditionReached(ArrayList<IndividualKnapsack> population , double percantage, int generation ){
        if( this.maxGen > 0){
            return generation > maxGen;
        }
        double averageFitness = this.averageFitness(population);
        double count = 0;
        for (IndividualKnapsack individual : population) {
            if(individual.getFitness() > averageFitness)
                count++;
        }
        return count / populationSize >= percantage;
    }

    public IndividualKnapsack crossover(IndividualKnapsack mother , IndividualKnapsack father){
        int[] genome = new int[this.geneLength];
        for (int i = 0; i < this.geneLength; i++) {
            if(random.nextDouble() > 0.5) {
                genome[i] = mother.getGene(i);
            }else {
                genome[i] = father.getGene(i);
            }
        }

        if(random.nextDouble() < this.mutationRate){
            int random_index = random.nextInt(geneLength);
            genome[random_index] = (int)(Math.round(Math.random()));
        }
        return new IndividualKnapsack(genome);
    }

    public ArrayList<IndividualKnapsack> generateNewGeneration(ArrayList<IndividualKnapsack> matingPool){
        ArrayList<IndividualKnapsack> newGeneration = new ArrayList<>();
        for (int i = 0; i < matingPool.size(); i+=2) {
            IndividualKnapsack mother = matingPool.get(i);
            IndividualKnapsack father = matingPool.get(i+1);

            for (int j = 0; j < 2 * this.populationSize / matingPool.size(); j++) {
                IndividualKnapsack child = crossover(mother , father);
                newGeneration.add(child);
            }
        }
        return newGeneration;
    }
    public ArrayList<IndividualKnapsack> selection(){
        Collections.sort(population);
        Collections.reverse(population);
        ArrayList<IndividualKnapsack> matingPool = new ArrayList<>(population.subList(0 , (int)( population.size()*0.10) ));
        Collections.shuffle(matingPool);
        return matingPool;
    }

    public static void main(String[] args) {
        Random random_ = new Random();
//        Knapsack.KnapsackItem a = new Knapsack.KnapsackItem(6,4);
//        Knapsack.KnapsackItem b = new Knapsack.KnapsackItem(7,3);
//        Knapsack.KnapsackItem c = new Knapsack.KnapsackItem(8,5);
        ArrayList<KnapsackItem> items = new ArrayList<>();
        int numberOfItems = 2000 ;
        for (int i = 0; i < numberOfItems; i++) {
                items.add(new KnapsackItem(random_.nextInt(8) + 2, random_.nextInt(8) + 2));
        }
        Knapsack.items = items;
        Knapsack.limit = 1000;

//        Knapsack knapsack = new Knapsack();
//        System.out.println(knapsack.bruteForce(items.size()));
//        items.add(a);
//        items.add(b);
//        items.add(c);
        ArrayList<IndividualKnapsack> population = GaKnapsack.initializePopulation(10000, numberOfItems );
        double[] mutation_rates = {0.01000, 0.10000 , 0.25, 0.5};



        // Create a fixed thread pool with a size equal to the number of cores

        // Submit each task to the executor

        for (double mutation_rate : mutation_rates) {
            System.out.println("--------------- MUTATION RATE : " + mutation_rate + "---------------");
            GaKnapsack ga2 = new GaKnapsack(population, population.size(), Knapsack.items.size(), mutation_rate, 0);
        }
    }

}
