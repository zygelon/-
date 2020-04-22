import java.awt.geom.Point2D;
import java.util.concurrent.atomic.AtomicBoolean;

public class Block {

    protected Coord Location;

    public AtomicBoolean bIsAlive;

    public AtomicBoolean bWillBeAlive;

    public void Update(){
        bIsAlive.set(bWillBeAlive.get());
    }

    public Coord GetLocation(){return Location;}

    public Block(int x,int y){
        Location=new Coord(x,y);
        //Location.setCoord(x,y);

        bIsAlive=new AtomicBoolean();
        bIsAlive.set(false);

        bWillBeAlive=new AtomicBoolean();
        bWillBeAlive.set(false);
    }

}

