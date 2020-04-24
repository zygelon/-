package A;

public class PhoneReader implements Runnable {
    private Database inform;

    public PhoneReader(Database Inf) {
        this.inform = Inf;
    }

    @Override
    public void run() {
        for (int i = 0; i < 13 && !Thread.currentThread().isInterrupted(); ++i) {
            try {
                inform.readPhone("name1");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
