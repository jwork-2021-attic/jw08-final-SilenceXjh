package game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import game.com.anish.screen.*;

public class RefreshLog implements Runnable {
    
    private Main mainActivity;
    private Screen screen;

    public RefreshLog(Main mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void run() {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream("log.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                Thread.sleep(30);
                screen = (WorldScreen)objectInputStream.readObject();
                mainActivity.setScreen(screen);
                mainActivity.repaint();
            }
            catch(Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

}