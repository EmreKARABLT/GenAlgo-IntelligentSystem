package Knapsack;

import java.util.*;

public class GaKnapsack {
    Random random = new Random();

    public int populationSize ;
    public int geneLength ;
    public int maxGen ;
    public double mutationRate ;
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

        int gen = 0 ;
        while(!checkStoppingConditionReached(population, 0.90, gen)){
            ArrayList<IndividualKnapsack> matingPool = selection();
            population = generateNewGeneration(matingPool);

            System.out.println("Gen :" + (gen+1) + " - Average :"+  this.averageFitness(population) + " - Max :" + population.stream().max(IndividualKnapsack::compareTo).stream().toList().get(0).getFitness() );
            gen++;
        }




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
        int numberOfItems = 2500 ;
        for (int i = 0; i < numberOfItems; i++) {
                items.add(new KnapsackItem(random_.nextInt(10), random_.nextInt(10)));
        }
        Knapsack.items = items;
        Knapsack.limit = 350;

//        Knapsack knapsack = new Knapsack();
//        System.out.println(knapsack.bruteForce(items.size()));
//        items.add(a);
//        items.add(b);
//        items.add(c);
        ArrayList<IndividualKnapsack> population = GaKnapsack.initializePopulation(10000, numberOfItems );
        double[] mutation_rates = {0.00001 ,0.000025 , 0.00005 , 0.0001 , 0.0002,0.0005 };
        for (double mutation_rate : mutation_rates) {
            GaKnapsack ga = new GaKnapsack(population, population.size(), Knapsack.items.size(),  mutation_rate,500);
        }
    }

}
