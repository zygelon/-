package A;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        File file = new File("database.txt");
        file.createNewFile();

        Database inf = new Database(file);

        Thread phoneR = new Thread(new PhoneReader(inf), "Phone");
        phoneR.start();

        Thread nameR = new Thread(new NameReader(inf), "Name");
        nameR.start();

        Thread writer = new Thread(new Writer(inf), "Writer");
        writer.start();
    }
}

