package com.recruits;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        int threadNum = true;
        Army army = new Army(300, 5);

        for(int i = 0; i < 5; ++i) {
            (new Thread(new Worker(army, i * 60, (i + 1) * 60 - 1))).start();
        }

    }
}
