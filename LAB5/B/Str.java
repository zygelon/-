package com.rows;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Str {
    private String[] strings = new String[4];
    private int[] EqAB = new int[4];
    private CyclicBarrier barrier = new CyclicBarrier(4, () -> {
        this.CountSymb();
        System.out.println("Num A,B: " + Arrays.toString(this.EqAB));
        this.Show();
    });

    public Str() {
        for(int j = 0; j < this.strings.length; ++j) {
            StringBuilder s = new StringBuilder();

            for(int i = 0; i < 6; ++i) {
                int r = (new Random()).nextInt(4);
                switch(r) {
                    case 0:
                        s.append("A");
                        break;
                    case 1:
                        s.append("B");
                        break;
                    case 2:
                        s.append("C");
                        break;
                    case 3:
                        s.append("D");
                }
            }

            this.strings[j] = s.toString();
        }

    }

    public void CountSymb() {
        for(int i = 0; i < this.EqAB.length; ++i) {
            this.EqAB[i] = (int)this.strings[i].chars().filter((ch) -> {
                return ch == 65 || ch == 66;
            }).count();
        }

    }

    public void changeLetter(int i) throws BrokenBarrierException, InterruptedException {
        int myRand = (new Random()).nextInt(this.strings[i].length());
        char symb;
        if (this.strings[i].charAt(myRand) == 'A') {
            symb = 'C';
        } else if (this.strings[i].charAt(myRand) == 'B') {
            symb = 'D';
        } else if (this.strings[i].charAt(myRand) == 'C') {
            symb = 'A';
        } else {
            symb = 'B';
        }

        this.strings[i] = this.strings[i].substring(0, myRand) + symb + this.strings[i].substring(myRand + 1);
        this.barrier.await();
    }

    public String[] getStr() {
        return this.strings;
    }

    public void setStr(String[] strings) {
        this.strings = strings;
    }

    public int[] getEqAB() {
        return this.EqAB;
    }

    public void SetEqAB(int[] eqAB) {
        this.EqAB = eqAB;
    }

    public void Show() {
        String[] var1 = this.strings;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            System.out.println(s);
        }

        System.out.println();
    }

    public boolean IsNeedStop() {
        if (this.EqAB[0] == this.EqAB[1] && this.EqAB[1] == this.EqAB[2]) {
            return true;
        } else if (this.EqAB[0] == this.EqAB[1] && this.EqAB[1] == this.EqAB[3]) {
            return true;
        } else if (this.EqAB[0] == this.EqAB[2] && this.EqAB[2] == this.EqAB[3]) {
            return true;
        } else {
            return this.EqAB[1] == this.EqAB[2] && this.EqAB[2] == this.EqAB[3];
        }
    }
}