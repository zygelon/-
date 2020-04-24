package com.rows;

import java.util.concurrent.BrokenBarrierException;

public class myThr implements Runnable {
    private int id;
    private Str str;

    public myThr(int id, Str str) {
        this.id = id;
        this.str = str;
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                this.str.changeLetter(this.id);
            } catch (InterruptedException | BrokenBarrierException var2) {
                Thread.currentThread().interrupt();
                var2.printStackTrace();
            }

            if (this.str.IsNeedStop()) {
                Thread.currentThread().interrupt();
            }
        }

    }
}