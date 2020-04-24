
package com.recruits;

import java.util.Arrays;
import java.util.Random;

public class Army {
    private Army.ETurn[] posses;
    private final int NumSoldiers;
    private boolean bIsS;
    private final CyclicBarrier barrier;

    public boolean GetbIsSt() {
        return this.bIsS;
    }

    public Army(int NumSoldiers, int threadNum) {
        this.barrier = new CyclicBarrier(threadNum, () -> {
            this.bIsS = this.isbIsS();
            this.ShowTurn();
        });
        this.NumSoldiers = NumSoldiers;
        this.posses = new Army.ETurn[NumSoldiers];

        for(int i = 0; i < NumSoldiers; ++i) {
            int side = (new Random()).nextInt(2);
            if (side == 0) {
                this.posses[i] = Army.ETurn.L;
            } else {
                this.posses[i] = Army.ETurn.R;
            }
        }

    }

    public void ShowTurn() {
        System.out.println(Arrays.toString(this.posses));
    }

    public boolean isbIsS() {
        for(int i = 0; i < this.NumSoldiers - 2; ++i) {
            if (this.posses[i] == Army.ETurn.R && this.posses[i + 1] == Army.ETurn.L) {
                return false;
            }
        }

        return true;
    }

    private void rotate(int i, int j) {
        Army.ETurn iSide = this.posses[i];
        this.posses[i] = this.posses[j];
        this.posses[j] = iSide;
    }

    private boolean IsLookEachOther(int left, int right) {
        return this.posses[left] == Army.ETurn.R && this.posses[right] == Army.ETurn.L;
    }

    public void checkFormation(int from, int to) throws InterruptedException {
        if (from > 0) {
            synchronized(this.posses) {
                if (this.IsLookEachOther(from - 1, from)) {
                    this.rotate(from - 1, from);
                }
            }
        }

        for(int i = from; i < to; ++i) {
            if (this.IsLookEachOther(i, i + 1)) {
                this.rotate(i, i + 1);
            }
        }

        if (to < this.NumSoldiers - 1) {
            synchronized(this.posses) {
                if (this.IsLookEachOther(to, to + 1)) {
                    this.rotate(to, to + 1);
                }
            }
        }

        this.barrier.await();
    }

    private static enum ETurn {
        L,
        R;

        private ETurn() {
        }
    }
}
