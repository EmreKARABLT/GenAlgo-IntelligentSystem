package TSP;

import Knapsack.IndividualKnapsack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GaTSP {
    Random random = new Random();

    public int populationSize ;
    public int geneLength ;
    public double mutationRate ;
    ArrayList<IndividualTSP> population ;
    public GaTSP(int populationSize , int geneLength, double mutationRate){

        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.geneLength = geneLength;
        this.population = new ArrayList<>();
        initializePopulation();
//        System.out.println(population);
        geneticAlgorithm();

    }
    public ArrayList<Integer> orderedListOfNumbers(){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < this.geneLength; i++) {
            list.add(i);
        }
        return list;
    }
    public void initializePopulation(){
        for (int i = 0; i < populationSize; i++) {
            ArrayList<Integer> allCities = this.orderedListOfNumbers();

            int[] genome = new int[geneLength];

            for (int j = 0; j < geneLength; j++) {
                int randomIndex = random.nextInt( allCities.size() );
                genome[j] = allCities.get(randomIndex);
                allCities.remove(randomIndex);
            }

            IndividualTSP individual = new IndividualTSP(genome);
            population.add(individual);
        }


    }
    public void geneticAlgorithm(){
        initializePopulation();
        int gen = 0 ;
        while(!checkStoppingConditionReached(population, 0.005)){
            ArrayList<IndividualTSP> matingPool = selection();
            population = generateNewGeneration(matingPool);
//
            System.out.println("Gen :" + (gen+1) + " - 2 PI  :"+  +2*Math.PI + " - Min :" + population.stream().min(IndividualTSP::compareTo).stream().toList().get(0).getFitness() );
            gen++;
        }
        System.out.println(population.stream().min(IndividualTSP::compareTo).stream().toList().get(0).knapsack.path);
    }
    public double averageFitness(ArrayList<IndividualTSP> population){
        double sum = population
                .stream()
                .map(IndividualTSP::getFitness).reduce(0.0,Double::sum);
        return sum / (double)(populationSize);
    }

    public boolean checkStoppingConditionReached(ArrayList<IndividualTSP> population , double threshold_percentage_error ){
        double min = population.stream().min(IndividualTSP::compareTo).stream().toList().getFirst().getFitness();
        double percantage_error = Math.abs( (min - 2*Math.PI)) / Math.PI ;

        return percantage_error < threshold_percentage_error;

    }
    public ArrayList<IndividualTSP> crossover(IndividualTSP mother , IndividualTSP father){
        //https://user.ceng.metu.edu.tr/~ucoluk/research/publications/tspnew.pdf
        int[] motherGen = mother.getGene().clone();
        int[] fatherGen = father.getGene().clone();
        int random_start = random.nextInt(geneLength -1 ) ;
        int random_end = random_start +  1 + random.nextInt( geneLength- random_start -1);

        int[] a = Arrays.copyOfRange(motherGen, random_start, random_end);
        int[] b = Arrays.copyOfRange(fatherGen, random_start, random_end);
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            child1.add(a[i]);
            child2.add(b[i]);
        }
        for (int i = 0; i < fatherGen.length; i++) {
            if (!child1.contains(fatherGen[i])) {
                child1.add(fatherGen[i]);
            }
            if (!child2.contains(motherGen[i])) {
                child2.add(motherGen[i]);
            }
        }
        for (int i = 0; i < motherGen.length; i++) {
            motherGen[i] = child1.get(i);
            fatherGen[i] = child2.get(i);
        }
        if(random.nextDouble() < mutationRate){
            fatherGen = mutate(fatherGen);
        }
        if(random.nextDouble() < mutationRate){
            motherGen = mutate(motherGen);
        }

//        System.out.println("DONE : " + Arrays.toString(motherGen) + "\nDONE : " + Arrays.toString(fatherGen) );
        ArrayList<IndividualTSP> children = new ArrayList<>();
        children.add(new IndividualTSP(fatherGen));
        children.add(new IndividualTSP(motherGen));

        return children;
    }
    public int findIndex(int gene, int[] genome){
        for (int i = 0; i < genome.length; i++) {
            if( genome[i] == gene)
                return i;
        }
        return -1;
    }
    public int[] mutate(int[] genome){
        int indexA = random.nextInt(genome.length);
        int indexB = random.nextInt(genome.length);
        while( indexB == indexA ){
            indexB = random.nextInt(genome.length);
        }
        int temp = genome[indexA];
        genome[indexA] = genome[indexB];
        genome[indexB] = temp;

        return genome;
    }
    public ArrayList<IndividualTSP> generateNewGeneration(ArrayList<IndividualTSP> matingPool){

        ArrayList<IndividualTSP> newGeneration = new ArrayList<>();
        for (int i = 0; i < matingPool.size(); i+=2) {
            IndividualTSP mother = matingPool.get(i);
            IndividualTSP father = matingPool.get(i+1);

            for (int j = 0; j < 2 * this.populationSize / matingPool.size(); j++) {
                ArrayList<IndividualTSP> children = crossover(mother , father);
                newGeneration.addAll(children);
            }
        }
        return newGeneration;
    }
    public ArrayList<IndividualTSP> selection(){
        Collections.sort(population);

        ArrayList<IndividualTSP> matingPool = new ArrayList<>(population.subList(0 , (int)( population.size()*0.25) ));
        Collections.shuffle(matingPool);
        return matingPool;
    }


    public static void main(String[] args) {
        Random random_ = new Random();
        ArrayList<City> items = new ArrayList<>();
        int piece = 1000 ;
        int[] arr = new int[piece];

        for( int i = 0 ; i < piece ; i++ ){
            double angle = i *  Math.PI * 2.0 / piece  ;
            System.out.println(angle);
            items.add(new City(angle , 1 ));
            arr[i] = i;
        }

        TSP.cities = items;


        GaTSP ga = new GaTSP(10000, items.size(),0.001);

    }
}