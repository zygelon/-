package C;

public class AddTripsWriter implements Runnable {
    private Graph graph;

    public AddTripsWriter(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        graph.appendCity("Kyiv");
        graph.appendCity("Berlin");
        graph.appendCity("London");
        graph.appendCity("NY");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        graph.appendCity("Moscow");
        graph.appendCity("Minsk");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
