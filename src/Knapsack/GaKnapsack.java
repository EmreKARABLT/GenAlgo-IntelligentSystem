package Knapsack;

import java.util.*;

public class GaKnapsack {
    Random random = new Random();

    public int populationSize ;
    public int geneLength ;
    public double mutationRate ;
    ArrayList<IndividualKnapsack> population ;
    public GaKnapsack(int populationSize , int geneLength, double mutationRate){

        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.geneLength = geneLength;
        this.population = new ArrayList<>();
        geneticAlgorithm();
    }
    public void initializePopulation(){
        for (int i = 0; i < populationSize; i++) {
            int[] genome = new int[geneLength];
            for (int j = 0; j < geneLength; j++) {
                genome[j] = (int)(Math.round(Math.random()));
            }
            IndividualKnapsack individual = new IndividualKnapsack(genome);
            population.add(individual);
        }


    }
    public void geneticAlgorithm(){
        initializePopulation();
        int gen = 0 ;
        while(!checkStoppingConditionReached(population, 0.90)){
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
    public boolean checkStoppingConditionReached(ArrayList<IndividualKnapsack> population , double percantage ){
        double averageFitness = this.averageFitness(population);
        double count = 0;
        for (IndividualKnapsack individual : population) {
            if(individual.getFitness() > averageFitness)
                count++;
        }
        if( count / populationSize >= percantage){
            return true;
        }
        return false;
    }

    public IndividualKnapsack crossover(IndividualKnapsack mother , IndividualKnapsack father){
        int[] genome = new int[this.geneLength];
        for (int i = 0; i < this.geneLength; i++) {
            if(random.nextDouble() > 0.5) {
                genome[i] = mother.getGene(i);
            }else {
                genome[i] = father.getGene(i);
            }
            if(random.nextDouble() < this.mutationRate){
                genome[i] = (int)(Math.round(Math.random()));
            }
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
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                items.add(new KnapsackItem(i,j));
            }
        }
//        items.add(a);
//        items.add(b);
//        items.add(c);
        Knapsack.items = items;
        Knapsack.limit = 100;
        GaKnapsack ga = new GaKnapsack(100000,Knapsack.items.size(),0.001);

    }

}
