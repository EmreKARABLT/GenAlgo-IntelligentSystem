import java.util.ArrayList;

public class Knapsack {
    public ArrayList<KnapsackItem> sack;
    public int limit ;

    public Knapsack(int limit){
        this.limit = limit;
        this.sack = new ArrayList<>();
    }
    // exceeding the limits of the knapsack is not allowed
    public void addItem(KnapsackItem item) {
        if (this.getWeight() + item.getWeight() <= this.limit) {
            sack.add(item);
        }
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
