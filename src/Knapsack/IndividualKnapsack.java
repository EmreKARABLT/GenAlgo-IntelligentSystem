package Knapsack;

import java.util.Arrays;

public class IndividualKnapsack implements Comparable<IndividualKnapsack>{
    protected int fitness;
    protected int[] genome ;
    Knapsack knapsack ;
    public IndividualKnapsack(int[] genome){
        this.genome = genome;
        this.knapsack = new Knapsack();
        this.knapsack.simulate(this);
    }
    public int getFitness() {
        return this.fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int[] getGene() {
        return genome;
    }
    public int getGene(int index){
        return this.genome[index];
    }

    public void setGenome(int[] genome) {
        this.genome = genome;
    }
    public void changeGene(int index , int gene){
        this.genome[index] = gene;
    }
    public Knapsack getKnapsack(){
        return this.knapsack ;
    }
    @Override
    public int compareTo(IndividualKnapsack o) {
        return Integer.compare(this.fitness,o.fitness);
    }

    @Override
    public String toString() {
        return "Knapsack.IndividualKnapsack{" +
                "fitness=" + fitness + " "+
                "genotype" + Arrays.toString(genome) +
                '}';
    }
}
