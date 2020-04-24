package C;

public class ChangePriceWR implements Runnable {
    private Graph graph;

    public ChangePriceWR(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        graph.changePrice("Kyiv", "Berlin", 8);
        graph.changePrice("Berlin", "NY", 16);
        graph.changePrice("London", "NY", 21);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        graph.changePrice("Minsk", "NY", 10);

    }
}
