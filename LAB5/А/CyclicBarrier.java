package com.recruits;

public class CyclicBarrier {
    private int number;
    private Runnable runnable;
    private int arrived = 0;

    public CyclicBarrier(int number, Runnable runnable) {
        this.number = number;
        this.runnable = runnable;
    }

    public synchronized void await() throws InterruptedException {
        if (this.arrived == this.number - 1) {
            this.arrived = 0;
            this.runnable.run();
            this.notifyAll();
        } else {
            ++this.arrived;
            this.wait();
        }

    }
}
