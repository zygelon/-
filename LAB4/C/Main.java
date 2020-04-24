package C;

public class Main {
    public static void main(String[] args) {

        Graph graphInstance = new Graph();

        new Thread(new ChangePriceWR(graphInstance)).start();

        new Thread(new ChangeWriter(graphInstance)).start();

        new Thread(new TripChecker(graphInstance)).start();

        new Thread(new AddTripsWriter(graphInstance)).start();
    }
}
