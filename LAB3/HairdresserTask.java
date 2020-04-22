import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HairdresserTask {

    static Client sheared = null;
    static Lock lock = new ReentrantLock(true);

    static Condition hairdresser = lock.newCondition();
    static Condition client = lock.newCondition();

    static class Client implements Runnable {
        private int id;

        Client(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                while (sheared != null) {
                    client.await();
                }
                System.out.println("Client " + id + " chooses haircut");
                sheared = this;
                hairdresser.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class Hairdresser implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (sheared == null) {
                        hairdresser.await();
                    }
                    System.out.println("Make haircut for client " + sheared.getId());
                    System.out.println("The hairdresser sees off the client " + sheared.getId());
                    sheared = null;
                    client.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Hairdresser()).start();
        for (int k = 0; k < 10; k++) {
            new Thread(new Client(k)).start();
        }
    }
}
