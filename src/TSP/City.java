package TSP;

public class City {
    public double x ;
    public double y ;

    public City(double x, double y ){
        this.x = x ;
        this.y = y ;
    }
    public double calculateDist(City city){
        return Math.sqrt( Math.pow(this.x - city.x, 2) + Math.pow(this.y - city.y, 2) );
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
