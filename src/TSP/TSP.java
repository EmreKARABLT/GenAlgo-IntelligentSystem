package TSP;


import java.util.ArrayList;

public class TSP {
    public ArrayList<City> path;

    public static ArrayList<City> cities = new ArrayList<>();

    public TSP(){
        this.path = new ArrayList<>();
    }
    // First, exceeding the limits of the knapsack is allowed
    // then if the genome exceeds the limit randomly selected item will be removed until it doesnt goes over the limit
    public void simulate(IndividualTSP individual){
        int[] genome = individual.getGene();
        for (int i = 0; i < genome.length ; i++) {
            path.add(cities.get(genome[i]));
        }
        path.add(cities.get(genome[0]));


        individual.setFitness(this.getValue());

    }
    public double getValue(){
        double value = 0 ;
        for (int i = 0 ; i < path.size()-1 ; i++) {
            value += path.get(i).calculateDist(path.get(i+1));
        }
        return value;
    }
}
