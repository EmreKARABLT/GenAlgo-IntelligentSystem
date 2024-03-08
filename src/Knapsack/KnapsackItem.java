package Knapsack;

public class KnapsackItem implements Comparable<KnapsackItem>{
    public int weight ;
    public int value ;

    public KnapsackItem(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
    public int getWeight() {
        return weight;
    }
    public int getValue() {
        return value;
    }
    public double density(){
        return ((double)value)/weight;
    }
    @Override
    public int compareTo(KnapsackItem o) {
        return Double.compare(this.density(),o.density());
    }

    @Override
    public String toString() {
        return "Knapsack.KnapsackItem{" +
                "weight=" + weight +
                ", value=" + value +
                '}';
    }
}
