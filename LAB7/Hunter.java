package AB;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Hunter extends Thread {
    private JLabel hunterLabel;
    private MainPanel panel;
    private int x;
    private int y;
    private int labelWidth = 118;
    private int labelHeight = 215;
    private ImageIcon hunterRight = new ImageIcon("./resources/hunterLR.png");
    private ImageIcon hunterLeft = new ImageIcon("./resources/hunterRL.png");
    private int side = 1;
    private int dx = 20;
    private int panelWidth;
    private volatile int countBullet = 0;
    private Hunter hunter = this;
    private boolean bLeftKeyDown = false;
    private boolean bRightKeyDown = false;

    Hunter(MainFrame main, MainPanel panel) {
        this.panelWidth = panel.width;
        this.panel = panel;
        this.x = panel.width / 2;
        this.y = panel.height - this.labelHeight;
        this.hunterLabel = new JLabel(new ImageIcon("./resources/hunterRL.png"));
        this.hunterLabel.setSize(new Dimension(this.labelWidth, this.labelHeight));
        this.hunterLabel.setLocation(this.x, this.y);
        this.hunterLabel.setVisible(true);
        panel.add(this.hunterLabel);
        Hunter.HunterKeyListener keyListener = new Hunter.HunterKeyListener();
        main.addKeyListener(keyListener);
    }

    synchronized void addBullet(int num) {
        this.countBullet += num;
    }

    public void run() {
        while(true) {
            if (!this.isInterrupted()) {
                if (this.bLeftKeyDown && this.x - this.dx >= 0) {
                    this.x -= this.dx;
                } else if (this.bRightKeyDown && this.x + this.dx + this.labelWidth <= this.panelWidth) {
                    this.x += this.dx;
                }

                this.hunterLabel.setLocation(this.x, this.y);

                try {
                    sleep(20L);
                    continue;
                } catch (InterruptedException var2) {
                }
            }

            this.panel.remove(this.hunterLabel);
            return;
        }
    }

    public class HunterKeyListener implements KeyListener {
        public HunterKeyListener() {
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (Hunter.this.panel != null) {
                switch(e.getKeyCode()) {
                    case 32:
                        if (Hunter.this.countBullet < 10) {
                            Bullet bullet;
                            if (Hunter.this.side == 1) {
                                bullet = new Bullet(Hunter.this.panel, Hunter.this.hunter, Hunter.this.x, Hunter.this.y + 50);
                            } else {
                                bullet = new Bullet(Hunter.this.panel, Hunter.this.hunter, Hunter.this.x + Hunter.this.labelWidth, Hunter.this.y + 50);
                            }

                            bullet.start();
                        }
                        break;
                    case 65:
                        Hunter.this.bLeftKeyDown = true;
                        if (Hunter.this.x > 0 && Hunter.this.side != 1) {
                            Hunter.this.hunterLabel.setIcon(Hunter.this.hunterLeft);
                            Hunter.this.side = 1;
                        }
                        break;
                    case 68:
                        Hunter.this.bRightKeyDown = true;
                        if (Hunter.this.x < Hunter.this.panelWidth - Hunter.this.labelWidth && Hunter.this.side != 2) {
                            Hunter.this.hunterLabel.setIcon(Hunter.this.hunterRight);
                            Hunter.this.side = 2;
                        }
                }
            }

        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 68) {
                Hunter.this.bRightKeyDown = false;
            } else if (e.getKeyCode() == 65) {
                Hunter.this.bLeftKeyDown = false;
            }

        }
    }
}
