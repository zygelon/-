
package AB;

import java.awt.Dimension;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Duck extends Thread {
    int x;
    int y;
    private int speedX;
    private int speedY;
    private int GameFieldX;
    private int GameFieldY;
    int labelWidth = 200;
    int labelHeight = 200;
    private Random random = new Random();
    private JLabel duck;
    private MainPanel panel;

    Duck(int width, int height, MainPanel panel) {
        this.GameFieldX = width;
        this.GameFieldY = height - 350;
        this.panel = panel;
        ImageIcon duckLeft;
        int type;
        if (this.random.nextInt() % 2 == 0) {
            duckLeft = new ImageIcon("./resources/duckLR.gif");
            this.duck = new JLabel(duckLeft);
            this.duck.setSize(new Dimension(this.labelWidth, this.labelHeight));
            type = this.random.nextInt(3);
            if (type == 0) {
                this.x = -this.labelWidth;
                this.y = Math.abs(this.random.nextInt()) % this.GameFieldY;
            } else if (type == 1) {
                this.y = -this.labelHeight;
                this.x = Math.abs(this.random.nextInt()) % this.GameFieldX;
            } else {
                this.y = this.GameFieldY - this.labelHeight;
                this.x = Math.abs(this.random.nextInt()) % this.GameFieldX;
            }

            this.speedX = Math.abs(this.random.nextInt(5)) + 1;
        } else {
            duckLeft = new ImageIcon("./resources/duckRL.gif");
            this.duck = new JLabel(duckLeft);
            this.duck.setSize(new Dimension(this.labelWidth, this.labelHeight));
            type = this.random.nextInt(3);
            if (type == 0) {
                this.x = this.GameFieldX + this.labelWidth;
                this.y = Math.abs(this.random.nextInt()) % this.GameFieldY;
            } else if (type == 1) {
                this.y = -this.labelHeight;
                this.x = Math.abs(this.random.nextInt()) % this.GameFieldX;
            } else {
                this.y = this.GameFieldY - this.labelHeight;
                this.x = Math.abs(this.random.nextInt()) % this.GameFieldX;
            }

            this.speedX = -Math.abs(this.random.nextInt(5)) - 1;
        }

        if (this.y > this.GameFieldY / 2) {
            this.speedY = -Math.abs(this.random.nextInt(4)) - 1;
        } else {
            this.speedY = Math.abs(this.random.nextInt(4)) + 1;
        }

    }

    public void run() {
        this.panel.add(this.duck);
        boolean flag = true;

        while(!this.isInterrupted() && flag) {
            int nextX = this.x + this.speedX;
            int nextY = this.y + this.speedY;
            if (this.speedX > 0 && nextX > this.GameFieldX || this.speedX < 0 && nextX < -this.labelWidth || this.speedY > 0 && nextY > this.GameFieldY || this.speedY < 0 && nextY < -this.labelHeight) {
                flag = false;
            }

            this.x = nextX;
            this.y = nextY;
            this.duck.setLocation(this.x, this.y);

            try {
                sleep((long)(Math.abs(this.random.nextInt(5)) + 20));
            } catch (InterruptedException var5) {
                this.interrupt();
            }
        }

        this.panel.remove(this.duck);
        this.panel.repaint();
        this.panel.ducks.remove(this);
    }
}
