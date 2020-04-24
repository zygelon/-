package B;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ExecThr extends Thread {
    volatile Life life;
    CyclicBarrier barrier;
    ReentrantReadWriteLock lock;
    int types;
    int start;
    int finish;

    ExecThr(Life life, CyclicBarrier barrier, ReentrantReadWriteLock lock, int start, int finish, int type) {
        this.life = life;
        this.barrier = barrier;
        this.lock = lock;
        this.types = type;
        this.start = start;
        this.finish = finish;
    }

    public void run() {
        while(!this.isInterrupted()) {
            this.lock.readLock().lock();
            this.life.simulate(this.types, this.start, this.finish);
            this.lock.readLock().unlock();

            try {
                this.barrier.await();
            } catch (BrokenBarrierException | InterruptedException var2) {
            }
        }

    }
}
