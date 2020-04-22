import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MmMain {
    private static final Lock lock = new ReentrantLock();

    private static int Cup = 0;
    private static int CupCapacity = 10;
    private static final int CountOfBees = 10;

    private static final Condition BearCond = lock.newCondition();
    private static final Condition BeesCond = lock.newCondition();

    static class Bear implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (Cup != CupCapacity) {
                        BearCond.await();
                    }
                    Cup = 0;
                    System.out.println("ВЕДЄДЬ зї'в весь мед!!!");
                    BeesCond.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Bee implements Runnable {


        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (Cup == CupCapacity) {
                        BeesCond.await();
                    }
                    Cup++;
                    System.out.println("Бжола поклала каплину меду в глечик");
                    if (Cup == CupCapacity) {
                        System.out.println("Бжоли збудили vedmedya");
                        BearCond.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }



    public static void main(String[] args) {
        new Thread(new Bear()).start();
        for (int i = 0; i < CountOfBees; i++) {
            new Thread(new Bee()).start();
        }
    }
}