package com.recruits;

public class Worker implements Runnable {
    private final Army army;
    private final int from;
    private final int to;

    public Worker(Army army, int from, int to) {
        this.army = army;
        this.from = from;
        this.to = to;
    }

    public void run() {
        while(!this.army.GetbIsSt()) {
            try {
                this.army.checkFormation(this.from, this.to);
            } catch (InterruptedException var2) {
                Thread.currentThread().interrupt();
            }
        }

    }
}

