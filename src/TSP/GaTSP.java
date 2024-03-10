package TSP;

import java.util.ArrayList;
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
        System.out.println(population);
//        geneticAlgorithm();

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
        while(!checkStoppingConditionReached(population, 0.90)){
            ArrayList<IndividualTSP> matingPool = selection();
            population = generateNewGeneration(matingPool);
//
            System.out.println("Gen :" + (gen+1) + " - Average :"+  this.averageFitness(population) + " - Min :" + population.stream().min(IndividualTSP::compareTo).stream().toList().get(0).getFitness() );
            gen++;
        }
    }
    public double averageFitness(ArrayList<IndividualTSP> population){
        double sum = population
                .stream()
                .map(IndividualTSP::getFitness).reduce(0.0,Double::sum);
        return sum / (double)(populationSize);
    }

    public boolean checkStoppingConditionReached(ArrayList<IndividualTSP> population , double percantage ){
        double averageFitness = this.averageFitness(population);
        double count = 0;
        for (IndividualTSP individual : population) {
            if(individual.getFitness() > averageFitness)
                count++;
        }
        return count / populationSize >= percantage;
    }

    public IndividualTSP crossover(IndividualTSP mother , IndividualTSP father){
        int[] genome = new int[this.geneLength];


        return new IndividualTSP(genome);
    }

    public ArrayList<IndividualTSP> generateNewGeneration(ArrayList<IndividualTSP> matingPool){

        ArrayList<IndividualTSP> newGeneration = new ArrayList<>();
        for (int i = 0; i < matingPool.size(); i+=2) {
            IndividualTSP mother = matingPool.get(i);
            IndividualTSP father = matingPool.get(i+1);

            for (int j = 0; j < 2 * this.populationSize / matingPool.size(); j++) {
                IndividualTSP child = crossover(mother , father);
                newGeneration.add(child);
            }
        }
        return newGeneration;
    }
    public ArrayList<IndividualTSP> selection(){
        Collections.sort(population);
        Collections.reverse(population);
        ArrayList<IndividualTSP> matingPool = new ArrayList<>(population.subList(0 , (int)( population.size()*0.50) ));
        Collections.shuffle(matingPool);
        return matingPool;
    }

    public static void main(String[] args) {
        Random random_ = new Random();
//        Knapsack.KnapsackItem a = new Knapsack.KnapsackItem(6,4);
//        Knapsack.KnapsackItem b = new Knapsack.KnapsackItem(7,3);
//        Knapsack.KnapsackItem c = new Knapsack.KnapsackItem(8,5);
        ArrayList<City> items = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                items.add(new City(random_.nextInt(10), random_.nextInt(10)));
//            }
//        }
        City leuven = new City(0, 0 );
        City maastrich = new City(1,0);
        City amsterdam = new City(2,0);
        City berlin = new City(3,0);
        items.add(leuven);
        items.add(maastrich);
        items.add(amsterdam);
        items.add(berlin);
        TSP.cities = items;

        GaTSP ga = new GaTSP(4, items.size(),0.001);

    }
}
