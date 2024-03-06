import java.util.ArrayList;
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
//        while(this.getWeight()> limit){
//            Collections.sort(sack);
//            System.out.println("sack  " +sack);
//            int index = items.indexOf(sack.get(0));
//            individual.changeGene(index , 0);
//            sack.remove(0);
//        }
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

}
