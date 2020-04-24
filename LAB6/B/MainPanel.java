package B;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    private ReentrantReadWriteLock lock;
    Thread[] Thrds = null;
    private volatile Life field;
    private int cellSize;
    private int cellGap = 1;
    private final Color[] CivilColors;

    MainPanel(int width, int height, int size) {
        this.CivilColors = new Color[]{Color.RED, Color.CYAN, Color.MAGENTA, Color.BLUE, Color.PINK};
        this.setBackground(Color.DARK_GRAY);
        this.cellSize = size;
        this.lock = new ReentrantReadWriteLock();
        this.field = new Life(width, height);
    }

    void StartSimul(int NumCiv, float density) {
        this.Thrds = null;
        Updator updated = new Updator(this, this.field, this.lock);
        int amountOfWorkers = 4;
        CyclicBarrier barrier = new CyclicBarrier(amountOfWorkers, updated);
        this.field.clr();
        this.field.InitField(NumCiv, density);
        int quarterSize = this.field.getN() / amountOfWorkers;
        this.Thrds = new ExecThr[amountOfWorkers];

        for(byte i = 0; i < amountOfWorkers; ++i) {
            this.Thrds[i] = new ExecThr(this.field, barrier, this.lock, quarterSize * i, quarterSize * (i + 1), NumCiv);
        }

        for(int i = 0; i < amountOfWorkers; ++i) {
            this.Thrds[i].start();
        }

    }

    void stopSimul(JButton button) {
        button.setEnabled(false);
        if (this.Thrds != null) {
            Thread[] var2 = this.Thrds;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Thread worker = var2[var4];
                worker.interrupt();
            }
        }

        this.Thrds = null;
        button.setEnabled(true);
    }

    public Dimension getPreferredSize() {
        if (this.field != null) {
            Insets b = this.getInsets();
            return new Dimension((this.cellSize + this.cellGap) * this.field.getW() + this.cellGap + b.left + b.right + 20, (this.cellSize + this.cellGap) * this.field.getN() + this.cellGap + b.top + b.bottom + 20);
        } else {
            return new Dimension(200, 200);
        }
    }

    protected void paintComponent(Graphics g) {
        if (this.field != null) {
            this.lock.readLock().lock();
            super.paintComponent(g);
            Insets b = this.getInsets();

            for(int y = 0; y < this.field.getN(); ++y) {
                for(int x = 0; x < this.field.getW(); ++x) {
                    byte cell = this.field.getCell(x, y).value;
                    g.setColor(this.CivilColors[cell]);
                    g.fillRect(b.left + this.cellGap + x * (this.cellSize + this.cellGap), b.top + this.cellGap + y * (this.cellSize + this.cellGap), this.cellSize, this.cellSize);
                }
            }

            this.lock.readLock().unlock();
        }

    }
}
