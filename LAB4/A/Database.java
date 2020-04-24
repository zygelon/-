package A;

import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class Database {
    private File file;
    private ReadWriteLock lock;


    public Database(File file) {
        this.file = file;
        this.lock = new ReadWriteLock();
    }

    public List<Inf> readPhone(String name) throws InterruptedException {
        lock.startRead();
        System.out.println(Thread.currentThread().getName() + " started reading");
        List<Inf> result = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Inf d = new Inf(line);
                if(d.getName().equals(name)) {
                    result.add(d);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ended reading");
        lock.stopRead();
        return result;
    }

    public void write(Inf data) throws InterruptedException {
        lock.startWrite();

        System.out.println(Thread.currentThread().getName() + " started writing");
        try(FileWriter writer = new FileWriter(file, true))
        {
            writer.write(data.getName() + " " + Long.toString(data.getPhone()) + "\n");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        Thread.sleep(1500);
        System.out.println(Thread.currentThread().getName() + " ended writing");
        lock.stopWrite();
    }

    public List<Inf> readName(long phone) throws InterruptedException {
        lock.startRead();
        System.out.println(Thread.currentThread().getName() + " started reading");
        List<Inf> res = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Inf d = new Inf(line);
                if(d.getPhone() == phone) {
                    res.add(d);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ended reading");
        lock.stopRead();
        return res;
    }
}
