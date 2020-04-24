package A;

public class NameReader implements Runnable {
    private Database Inf;

    public NameReader(Database Inf) {
        this.Inf = Inf;
    }

    @Override
    public void run() {
        for (int i = 0; i < 8 && !Thread.currentThread().isInterrupted(); ++i) {
            try {
                Inf.readName(666);
                Thread.sleep(600);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
