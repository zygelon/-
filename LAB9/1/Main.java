import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Main {
    private final int N = 2000;
    private Random rand = new Random();

    public void FillMatrix(int[] A) {
        for(int i = 0; i < 4000000; ++i) {
            A[i] = this.rand.nextInt(1267);
        }

    }

    public Main() {
        int[] A = new int[4000000];
        int[] B = new int[4000000];
        int[] C = new int[4000000];
        this.FillMatrix(A);
        this.FillMatrix(B);
        long StartTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool(12);
        forkJoinPool.invoke(new Main.ParallelTask(A, B, C));
        long EndTime = System.currentTimeMillis();
        System.out.println("N = 2000; Execution time : " + (EndTime - StartTime) + " ms");
    }

    public static void main(String[] args) {
        new Main();
    }

    class ParallelTask extends RecursiveAction {
        private int[] A;
        private int[] B;
        private int[] C;
        private int ID;

        ParallelTask(int[] a, int[] b, int[] c) {
            this(a, b, c, -100);
        }

        void mult(int[] a, int[] b, int[] c, int num) {
            for(int i = 0; i < 2000; ++i) {
                for(int j = 0; j < 2000; ++j) {
                    int[] var10000 = this.C;
                    var10000[num * 2000 + i] += this.A[num * 2000 + j] * this.B[j * 2000 + i];
                }
            }

        }

        protected void compute() {
            if (this.ID < 0) {
                ArrayList<Main.ParallelTask> tasks = new ArrayList();

                for(int i = 0; i < 2000; ++i) {
                    tasks.add(Main.this.new ParallelTask(this.A, this.B, this.C, i));
                }

                invokeAll(tasks);
            } else {
                this.mult(this.A, this.B, this.C, this.ID);
            }

        }

        ParallelTask(int[] a, int[] b, int[] c, int id) {
            this.A = a;
            this.B = b;
            this.C = c;
            this.ID = id;
        }
    }
}
