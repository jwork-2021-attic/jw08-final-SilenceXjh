package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class Refresh implements Runnable {

    private Main mainActivity;

    public Refresh(Main mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(30);
                mainActivity.write();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}

class MyObjectOutputStream extends ObjectOutputStream {
    
    public MyObjectOutputStream() throws SecurityException, IOException {
        super();
    }
    public MyObjectOutputStream(OutputStream o) throws IOException {
        super(o);
    }

    public void writeStreamHeader() throws IOException {
        super.reset();
    }
}
