package TSP;

import java.util.Arrays;

public class IndividualTSP implements Comparable<IndividualTSP>{
    protected double fitness;
    protected int[] genome ;
    TSP knapsack ;
    public IndividualTSP(int[] genome){
        this.genome = genome;
        this.knapsack = new TSP();

        this.knapsack.simulate(this);
    }
    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double fitness) {
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

    public void swapGenes(int indexA , int indexB){
        int temp = this.genome[indexA];
        this.genome[indexA] = this.genome[indexB];
        this.genome[indexB] = temp;

    }
    @Override
    public int compareTo(IndividualTSP o) {
        return Double.compare(this.fitness,o.fitness);
    }

    @Override
    public String toString() {
        return "Knapsack.IndividualKnapsack{" +
                "fitness=" + fitness + " "+
                "genotype" + Arrays.toString(genome) +
                '}';
    }
}
