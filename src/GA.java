import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GA {
    Random random = new Random();

    public int populationSize ;
    public int geneLength ;
    public double mutationRate ;
    public int[][] chromosomes ;
    FitnessFunction fitnessFunction ;
    public GA(int populationSize , int geneLength, double mutationRate, FitnessFunction fitnessFunction){
        this.fitnessFunction = fitnessFunction;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.geneLength = geneLength;
        this.chromosomes = new int[this.populationSize][this.geneLength];


        for (int i = 0; i < chromosomes.length; i++) {
            for (int j = 0; j < chromosomes[i].length; j++) {
                chromosomes[i][j] = (int)(Math.round(Math.random()));
            }
        }
        System.out.println(Arrays.deepToString(chromosomes));
    }

    public void geneticAlgorithm(){
        ArrayList<int[]> matingPool = new ArrayList<>();
        //SELECTION




        for(int z = 0; z < matingPool.size(); z = z+2){
            int[] mother = matingPool.get(z);
            int[] father = matingPool.get(z+1);
            for (int k = 0; k < 4  ; k++) {
                int[] child1 = new int[geneLength];
                for (int i = 0; i < child1.length; i++) {
                    if (random.nextDouble() < 0.5) {
                        child1[i] = mother[i];
                    } else {
                        child1[i] = father[i];
                    }
                    if(random.nextDouble() < mutationRate){
                        child1[i]= (int)Math.round(random.nextDouble());
                    }
                }
                chromosomes[k] = child1;
            }
        }

    }

    public static void main(String[] args) {
        GA ga = new GA(2,5,0.1, null);

    }
//    public ArrayList<double[]> selection(){
//
//    }
}
