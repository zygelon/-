package AB;

import java.awt.Dimension;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bullet extends Thread {
    private int x;
    private int y;
    private static final int dy = 10;
    private static final int labelWidth = 14;
    private static final int labelHeight = 37;
    private MainPanel panel;
    private JLabel bulletLabel;
    private Hunter hunter;

    Bullet(MainPanel panel, Hunter hunter, int x, int y) {
        this.panel = panel;
        this.hunter = hunter;
        this.x = x;
        this.y = y;
        this.bulletLabel = new JLabel(new ImageIcon("./resources/shot.png"));
        this.bulletLabel.setSize(new Dimension(14, 37));
        this.bulletLabel.setLocation(x - 7, y - 18);
    }

    public void run() {
        this.hunter.addBullet(1);
        this.panel.add(this.bulletLabel);

        while(!this.isInterrupted() && this.y >= 0) {
            this.y -= 10;
            this.bulletLabel.setLocation(this.x - 7, this.y - 18);
            Iterator var1 = this.panel.ducks.iterator();

            while(var1.hasNext()) {
                Duck duck = (Duck)var1.next();
                synchronized(this.panel.ducks) {
                    if (this.x > duck.x && this.x < duck.x + duck.labelWidth && this.y > duck.y && this.y < duck.y + duck.labelHeight) {
                        duck.interrupt();
                        this.interrupt();
                        break;
                    }
                }
            }

            try {
                sleep(10L);
            } catch (InterruptedException var5) {
                break;
            }
        }

        this.panel.remove(this.bulletLabel);
        this.panel.repaint();
        this.hunter.addBullet(-1);
    }
}
