package game;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import game.com.anish.screen.WorldScreen;

public class Refresh implements Runnable {

    private Main mainActivity;

    public Refresh(Main mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void run() {
        while (true) {
            SocketChannel client = mainActivity.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
            int numRead = -1;
            try {
                numRead = client.read(buffer);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if(numRead != -1) {
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array());
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    WorldScreen screen = (WorldScreen)ois.readObject();
                    mainActivity.setScreen(screen);
                    mainActivity.repaint();
                    bis.close();
                    ois.close();
                    File file = new File("log.txt");
                    Boolean b = file.exists();
                    FileOutputStream fos = new FileOutputStream("log.txt", true);
                    ObjectOutputStream oos;
                    if(b) {
                        oos = new MyObjectOutputStream(fos);
                    }
                    else {
                        oos = new ObjectOutputStream(fos);
                    }
                    oos.writeObject(screen);
                    oos.flush();
                    oos.close();
                    //System.out.print("read");
                }
                catch(IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    
                    e.printStackTrace();
                }
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
