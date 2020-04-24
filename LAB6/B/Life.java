package B;

import java.util.Arrays;
import java.util.Random;

public class Life {
    private Cell[][] cur;
    private Cell[][] next;
    private int W;
    private int N;
    private int[][] neighbors = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};

    Life(int W, int height) {
        this.W = W;
        this.N = height;
        this.cur = new Cell[height][W];
        this.next = new Cell[height][W];

        for(int i = 0; i < W; ++i) {
            for(int j = 0; j < W; ++j) {
                this.next[i][j] = new Cell();
                this.cur[i][j] = new Cell();
            }
        }

    }

    int getW() {
        return this.W;
    }

    int getN() {
        return this.N;
    }

    void clr() {
        for(int i = 0; i < this.N; ++i) {
            for(int j = 0; j < this.W; ++j) {
                this.cur[i][j].value = 0;
            }
        }

    }

    Cell getCell(int x, int y) {
        return this.cur[x][y];
    }

    void simulate(int types, int start, int finish) {
        for(byte type = 1; type <= types; ++type) {
            for(int x = start; x < finish; ++x) {
                for(int y = 0; y < this.W; ++y) {
                    this.next[x][y].lock.writeLock().lock();
                    this.next[x][y].value = this.analizeNextVal(this.cur[x][y].value, this.next[x][y].value, this.countNeighbors(x, y, type), type);
                    this.next[x][y].lock.writeLock().unlock();
                }
            }
        }

    }

    void InitField(int civAmount, float density) {
        Random rand = new Random();
        int cellAmount = (int)((float)(this.W * this.N) * density / (float)civAmount);

        for(byte i = 1; i <= civAmount; ++i) {
            for(int j = 0; j < cellAmount; ++j) {
                int randomX = rand.nextInt(this.W);
                int randomY = rand.nextInt(this.N);
                if (this.cur[randomX][randomY].value == 0) {
                    this.cur[randomX][randomY].value = i;
                } else {
                    boolean set = false;

                    int ri;
                    int rj;
                    for(ri = randomX; ri < this.W && !set; ++ri) {
                        for(rj = randomY; rj < this.N && !set; ++rj) {
                            if (this.cur[ri][rj].value == 0) {
                                this.cur[ri][rj].value = i;
                                set = true;
                            }
                        }
                    }

                    for(ri = 0; ri < randomX && !set; ++ri) {
                        for(rj = 0; rj < randomY && !set; ++rj) {
                            if (this.cur[ri][rj].value == 0) {
                                this.cur[ri][rj].value = i;
                                set = true;
                            }
                        }
                    }
                }
            }
        }

    }

    void updateToNext() {
        Cell[][] t = this.cur;
        this.cur = this.next;
        this.next = t;
        this.next = new Cell[this.N][this.W];

        for(int i = 0; i < this.W; ++i) {
            for(int j = 0; j < this.W; ++j) {
                this.next[i][j] = new Cell();
            }
        }

    }

    private byte analizeNextVal(byte cell, byte inNewField, byte neighbors, byte type) {
        if (cell == type && neighbors < 2) {
            return 0;
        } else if (cell == type && (neighbors == 2 || neighbors == 3)) {
            return type;
        } else if (cell == type) {
            return 0;
        } else {
            return neighbors == 3 ? type : inNewField;
        }
    }

    private byte countNeighbors(int cellX, int cellY, byte type) {
        return (byte)((int)Arrays.stream(this.neighbors).filter((neighbor) -> {
            int neighborX = cellX + neighbor[0];
            int neighborY = cellY + neighbor[1];
            if (neighborX >= 0 && neighborX < this.N && neighborY >= 0 && neighborY < this.W) {
                return this.cur[neighborX][neighborY].value == type;
            } else {
                return false;
            }
        }).count());
    }
}
