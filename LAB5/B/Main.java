package com.rows;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Str str = new Str();

        for(int i = 0; i < 4; ++i) {
            (new Thread(new myThr(i, str))).start();
        }

    }
}
