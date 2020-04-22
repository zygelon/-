import javax.swing.*;
import java.awt.*;
import java.awt.image.ByteLookupTable;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Gamepanel extends JPanel {

    private static final long serialVersionUID=1L;

  //  public AtomicBoolean bIsNeedRepaint;

    private volatile Block[][] BlocksArray;

    public static final int PixelsSize=1000;

/*    public static Gamepanel Activate(int Size,Block[][] BlocksArray) {
        final Gamepanel[] obj = new Gamepanel[1];
        obj[0] =new Gamepanel(Size,BlocksArray);
        Thread myThr=new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {
                    if(obj[0].bIsNeedRepaint.get())
                    {
                        obj[0].repaint();
                        obj[0].bIsNeedRepaint.set(false);
                    }
                    obj[0].paint(obj[0].getGraphics());
                    obj[0].DrawBlocks();
            }
        }});
        myThr.start();
        return obj[0];
    }*/

    public int GetSize(){
        return Size;
    }
    public  Gamepanel(int Size,Block[][] BlocksArray){
     //   bIsNeedRepaint=new AtomicBoolean();
     //   bIsNeedRepaint.set(false);

        setPreferredSize(new Dimension(PixelsSize,PixelsSize));
        this.Size=Size;
        this.BlocksArray= BlocksArray;

        JFrame frame=new JFrame();
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLocation(0,0);
        frame.pack();
        frame.setVisible(true);
/*
        Scanner in = new Scanner(System.in);
        System.out.println("Input num of Alive cells");
        int n=in.nextInt();
        //Coord[] AliveInf=new Coord[n];
        for(int i=0;i<n;++i){
            System.out.println("Input coordinate:");
            Coord InputInf=new Coord();
            InputInf.setCoord(in.nextInt(),in.nextInt());
            BlocksArray[InputInf.x][InputInf.y].bIsAlive.set(true);
        }
*/
  /*      Thread myThr=new Thread(new Runnable() {
            @Override
            public void run() {

                }
            }
        });
        myThr.start();*/
    }

 /*   public  void start(){

    }

    public  void stop(){

    }

    public void tick(){

    }*/

    public void paint(Graphics g){
    //    repaint();
        for(int i=0;i<Size;++i)
            g.drawLine(i*PixelsSize/Size,0,i*PixelsSize/Size,PixelsSize);

        for(int i=0;i<Size;++i)
            g.drawLine(0, i * PixelsSize/Size, PixelsSize, i * PixelsSize/Size);
        //}
         //   System.out.println("Hello World!");
        //    g.drawRect(230,80,10,10);
           // g.setColor(Color.RED);
       //     g.fillRect(230,80,10,10);
            //Graphics2D rect=(Graphics2D)g;
            //g.setColor(Color.GREEN);
           // g.fillRect(0,270,300,30);


    }

    public void DrawBlocks(){

        for(int i=0;i<Size;++i)
            for(int j=0;j<Size;++j)
                if(BlocksArray[i][j].bIsAlive.get()) {
                    //getGraphics().drawRect(i*PixelsSize/Size,j*PixelsSize/Size,
                    //        PixelsSize/Size,PixelsSize/Size);
                    getGraphics().fillRect(i*PixelsSize/Size,j*PixelsSize/Size,
                            PixelsSize/Size,PixelsSize/Size);
                }

    }

   /* protected  void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawRect(230,80,10,10);
        g.setColor(Color.RED);
        g.fillRect(230,80,10,10);
    }*/

    protected int Size;
}
