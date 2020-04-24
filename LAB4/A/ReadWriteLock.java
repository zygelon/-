package A;

public class ReadWriteLock {


    private int waitingWriters;


    public void startWrite() throws InterruptedException {
        synchronized (this) {
            waitingWriters++;
        }
        while (!startWriteS()) {
            synchronized (this) {
                this.wait(10);
            }
        }

    }

    public ReadWriteLock() {
        waitingWriters = 0;
        numActiveReaders = 0;
        numActiveWriters = 0;
    }

    private int numActiveReaders;
    private int numActiveWriters;

    synchronized public void stopWrite() {
        numActiveWriters = 0;
    }



    synchronized private boolean startWriteS() {
        if (numActiveReaders != 0) {
            return false;
        }

        numActiveWriters = 1;
        waitingWriters--;

        notifyAll();
        return true;
    }

    synchronized private boolean startReadS() {
        if (numActiveWriters == 1 || waitingWriters > 0) {
            return false;
        }

        numActiveReaders++;
        notifyAll();

        return true;
    }

    public void startRead() throws InterruptedException {
        while (!startReadS()) {
            synchronized (this) {
                this.wait(20);
            }
        }
    }

    synchronized public void stopRead() {
        numActiveReaders--;
    }
}
