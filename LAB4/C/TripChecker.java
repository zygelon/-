package C;

public class TripChecker implements Runnable {

    @Override
    public void run() {

        String StartTown = "Moscow";
        String Destination = "Kyiv";

        boolean bIsTrip = false;

        bIsTrip = graph.isTrip(StartTown, Destination);

        if(bIsTrip) {
            System.out.println("Trip exists: " + StartTown + " to " + Destination);
        }
        else {
            System.out.println("Trip does't exist " + StartTown + " to " + Destination);
        }
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        bIsTrip = false;

        StartTown = "London";
        Destination = "Minsk";

        bIsTrip = graph.isTrip(StartTown, Destination);
        if(bIsTrip) {
            System.out.println("Trip exists: " + StartTown + " to " + Destination);
        }
        else {
            System.out.println("Trip does't exist " + StartTown + " to " + Destination);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        bIsTrip = false;
        StartTown = "Berlin";
        Destination = "NY";
        bIsTrip = graph.isTrip(StartTown, Destination);
        if(bIsTrip) {
            System.out.println("Trip exists: " + StartTown + " to " + Destination);
        }
        else {
            System.out.println("Trip does't exist " + StartTown + " to " + Destination);
        }
        graph.debugShowGraph();
    }

    private Graph graph;

    public TripChecker(Graph grInstance) {
        this.graph = grInstance;
    }

}
