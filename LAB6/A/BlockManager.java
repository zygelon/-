import javax.swing.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockManager {
    protected int Size;
    public Gamepanel gamepanel;
    public int UpdadeTime = 1000;
    public volatile Block[][] BlocksArray;

    protected volatile HashSet<Coord>AliveCells;



    int BoolToInt(boolean val) {
        return val ? 1 : 0;
    }

    public boolean IsBlockAlive(int x, int y) {
        if (x < 0 || y < 0 || x >= Size || y >= Size) return false;
        return BlocksArray[x][y].bIsAlive.get();
    }



    protected boolean WillBeAlive(Coord o)
    {
        int AliveNeigh =
        BoolToInt(IsBlockAlive(o.x + 1, o.y+ 1)) +
                BoolToInt(IsBlockAlive(o.x + 1, o.y)) +
                BoolToInt(IsBlockAlive(o.x + 1, o.y - 1)) +
                BoolToInt(IsBlockAlive(o.x, o.y + 1)) +
                BoolToInt(IsBlockAlive(o.x, o.y - 1)) +
                BoolToInt(IsBlockAlive(o.x - 1, o.y + 1)) +
                BoolToInt(IsBlockAlive(o.x - 1, o.y)) +
                BoolToInt(IsBlockAlive(o.x - 1, o.y - 1));
        boolean bIsAlive = BlocksArray[o.x][o.y].bIsAlive.get();
        return bIsAlive && AliveNeigh >= 2 && AliveNeigh <= 3 || !bIsAlive && AliveNeigh == 3;
    }


    public BlockManager(int Size, int UpdateTime) {
        this.UpdadeTime = UpdateTime;
        AliveCells=new HashSet<Coord>();
        BlocksArray = new Block[Size][Size];

        this.Size = Size;
        for (int i = 0; i < Size; ++i)
            for (int j = 0; j < Size; ++j) {
                BlocksArray[i][j] = new Block(i, j);
            }
        //BlocksArray[10][10].bIsAlive=true;

        Scanner in = new Scanner(System.in);
        System.out.println("Input num of Alive cells");
        int n = in.nextInt();
        //Coord[] AliveInf=new Coord[n];
        for (int i = 0; i < n; ++i) {
            System.out.println("Input coordinate:");
            Coord InputInf = new Coord(in.nextInt(), in.nextInt());
            //InputInf.setCoord(in.nextInt(), in.nextInt());
            BlocksArray[InputInf.x][InputInf.y].bIsAlive.set(true);
            AliveCells.add(InputInf);
        }

        gamepanel = new Gamepanel(Size, BlocksArray);

        Thread myThr = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Update();
                    try {
                        Thread.sleep(UpdateTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        myThr.start();

        //gamepanel=new Gamepanel(Size,BlocksArray);

        //BlocksArray[0][0].bIsAlive=true;
    }

    public void Update() {
        AtomicInteger Semaf = new AtomicInteger(0);
        int AvailableProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService exec = Executors.newFixedThreadPool(AvailableProcessors);

        HashSet<Coord> NextAliveCells = new HashSet<Coord>();

        try {
            for (final Coord o : AliveCells) {
                exec.submit(new Runnable() {
                    @Override
                    public void run() {
                        if(WillBeAlive(o)) {
                            NextAliveCells.add(o);

                            Coord temp = new Coord(o.x + 1, o.y + 1);
                            if (!IsBlockAlive(temp.x, temp.y) && WillBeAlive(new Coord(temp.x, temp.y)))
                                NextAliveCells.add(temp);

                            temp = new Coord(o.x + 1, o.y);
                            if (!IsBlockAlive(temp.x, temp.y) && WillBeAlive(new Coord(temp.x, temp.y)))
                                NextAliveCells.add(temp);

                            temp = new Coord(o.x + 1, o.y - 1);
                            if (!IsBlockAlive(temp.x, temp.y) && WillBeAlive(new Coord(temp.x, temp.y)))
                                NextAliveCells.add(temp);

                            temp = new Coord(o.x, o.y + 1);
                            if (!IsBlockAlive(temp.x, temp.y) && WillBeAlive(new Coord(temp.x, temp.y)))
                                NextAliveCells.add(temp);

                            temp = new Coord(o.x, o.y - 1);
                            if (!IsBlockAlive(temp.x, temp.y) && WillBeAlive(new Coord(temp.x, temp.y)))
                                NextAliveCells.add(temp);

                            temp = new Coord(o.x - 1, o.y + 1);
                            if (!IsBlockAlive(temp.x, temp.y) && WillBeAlive(new Coord(temp.x, temp.y)))
                                NextAliveCells.add(temp);

                            temp = new Coord(o.x - 1, o.y);
                            if (!IsBlockAlive(temp.x, temp.y) && WillBeAlive(new Coord(temp.x, temp.y)))
                                NextAliveCells.add(temp);

                            temp = new Coord(o.x - 1, o.y - 1);
                            if (!IsBlockAlive(temp.x, temp.y) && WillBeAlive(new Coord(temp.x, temp.y)))
                                NextAliveCells.add(temp);
                        }
                    }
                });
            }
        } finally {
            exec.shutdown();
        }

        exec.w
/*

        try {


            for (int i = 0; i < Size; i += 2) {
                int finalI = i;
                exec.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int g = finalI; g - finalI <= 1 && g < Size; g++) {
                            for (int j = 0; j < Size; ++j) {
                                int AliveNeigh =
                                        BoolToInt(IsBlockAlive(g + 1, j + 1)) +
                                                BoolToInt(IsBlockAlive(g + 1, j)) +
                                                BoolToInt(IsBlockAlive(g + 1, j - 1)) +
                                                BoolToInt(IsBlockAlive(g, j + 1)) +
                                                BoolToInt(IsBlockAlive(g, j - 1)) +
                                                BoolToInt(IsBlockAlive(g - 1, j + 1)) +
                                                BoolToInt(IsBlockAlive(g - 1, j)) +
                                                BoolToInt(IsBlockAlive(g - 1, j - 1));
                                boolean bIsAlive = BlocksArray[g][j].bIsAlive.get();
                                BlocksArray[g][j].bWillBeAlive.set(bIsAlive && AliveNeigh >= 2 && AliveNeigh <= 3 || !bIsAlive && AliveNeigh == 3);
                            }
                            Semaf.incrementAndGet();
                        }
                    }
                });
            }
        } finally {
            exec.shutdown();
        }
*/
        //while (Semaf.get() != Size) ;
        //Semaf.set(0);
        //     if(gamepanel!=null && gamepanel.bIsNeedRepaint!=null)
        //         gamepanel.bIsNeedRepaint.set(true);
        //  for(int i=0;i<Size;++i)
        ExecutorService exec2 = Executors.newFixedThreadPool(AvailableProcessors);
        try {
            for (int i = 0; i < Size; i += 2) {
                int finalI = i;
                exec2.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int g = finalI; g - finalI <= 1 && g < Size; g++) {
                            for (int j = 0; j < Size; ++j) {
                                for (int i = 0; i < Size; ++i)
                                        BlocksArray[g][j].Update();
                                      //  System.out.println(Integer.toString(i) + " " + Integer.toString(j) + " " + Boolean.toString(BlocksArray[i][j].bIsAlive.get()));
                            }
                            Semaf.incrementAndGet();
                        }
                    }
                });
            }
        } finally {
            exec.shutdown();
        }

        while (Semaf.get() != Size) ;

        /*for (int i = 0; i < Size; ++i)
            for (int j = 0; j < Size; ++j) {
                BlocksArray[i][j].Update();
                System.out.println(Integer.toString(i) + " " + Integer.toString(j) + " " + Boolean.toString(BlocksArray[i][j].bIsAlive.get()));
            }*/
        gamepanel.repaint();
        gamepanel.paint(gamepanel.getGraphics());
        gamepanel.DrawBlocks();
    }
    //public
}
