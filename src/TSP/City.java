package TSP;

public class City {
    public double x ;
    public double y ;
    public static int counter = 0 ;
    public int cityID = 0 ;
    public City(double theta, double r ){
        this.cityID = counter++;
        this.x = r * Math.cos(theta ) ;
        this.y = r * Math.sin(theta ) ;
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

    @Override
    public String toString() {
        return "C-" + cityID;
    }

    public static void main(String[] args) {
        City c = new City(Math.PI/2,1 );
        System.out.println(c.getX() + " " + c.getY());
    }
}
