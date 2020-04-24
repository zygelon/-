package B;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Updator implements Runnable {
    MainPanel mainPanel;
    volatile Life life;
    ReentrantReadWriteLock lock;
    int timeSleep = 100;

    Updator(MainPanel mainPanel, Life life, ReentrantReadWriteLock lock) {
        this.mainPanel = mainPanel;
        this.life = life;
        this.lock = lock;
    }

    public void run() {
        this.lock.writeLock().lock();
        this.life.updateToNext();
        this.lock.writeLock().unlock();
        this.mainPanel.repaint();

        try {
            Thread.sleep((long)this.timeSleep);
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

    }
}
