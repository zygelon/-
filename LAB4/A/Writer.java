package A;

public class Writer implements Runnable {
    private Database db;

    public Writer(Database db) {
        this.db = db;
    }

    @Override
    public void run() {
        for(int i = 0; i < 3; ++i) {
            try {
                Thread.sleep(2000);
                db.write(new Inf("name3", 666));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
