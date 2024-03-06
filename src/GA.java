import java.util.*;

public class GA {
    Random random = new Random();

    public int populationSize ;
    public int geneLength ;
    public double mutationRate ;
    ArrayList<IndividualKnapsack> population ;
    public GA(int populationSize , int geneLength, double mutationRate){

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
        System.out.println(population);

    }
    public void geneticAlgorithm(){
        initializePopulation();
        for (int i = 0; i < 5; i++) {
            ArrayList<IndividualKnapsack> matingPool = selection();
            System.out.println("pop size " + population.size());
            population = generateNewGeneration(matingPool);

        }



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
        System.out.println(2 * this.populationSize / matingPool.size());
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
        ArrayList<IndividualKnapsack> matingPool = new ArrayList<>(population.subList(0 , (int)( population.size()*0.5) ));
        Collections.shuffle(matingPool);
        return matingPool;
    }

    public static void main(String[] args) {
        KnapsackItem item1 = new KnapsackItem(1,1);
        KnapsackItem item2 = new KnapsackItem(2,4);
        KnapsackItem item3 = new KnapsackItem(3,9);
        KnapsackItem item4 = new KnapsackItem(4,16);
        KnapsackItem item5 = new KnapsackItem(5,25);
        System.out.println(item1.density());
        ArrayList<KnapsackItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        System.out.println(items.stream().sorted(KnapsackItem::compareTo).toList());
        System.out.println(items);
        Knapsack.items = items;
        Knapsack.limit = 8;
        GA ga = new GA(80,5,0.1);




//        IndividualKnapsack individual1 = new IndividualKnapsack(new int[]{1,0,1,0,1});
//        IndividualKnapsack individual2 = new IndividualKnapsack(new int[]{1,1,1,1,1});
//        System.out.println(individual1.getFitness()+ " " + Arrays.toString(individual1.getGenome()));
//        System.out.println(individual2.getFitness() + " " + Arrays.toString(individual2.getGenome()));
//        ArrayList<IndividualKnapsack> individuals = new ArrayList<>();
//        individuals.add(individual1);
//        individuals.add(individual2);
//        System.out.println(individuals.stream().sorted().toList());
    }

}
