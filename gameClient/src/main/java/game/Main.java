package game;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import game.asciiPanel.AsciiFont;
import game.asciiPanel.AsciiPanel;
import javafx.event.ActionEvent;

import game.com.anish.calabashbros.World;
import game.com.anish.screen.Screen;
import game.com.anish.screen.WorldScreen;

public class Main extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private JButton newgameButton;
    private JButton playbackButton;
    private JPanel panel;
    private Screen screen;

	private final static int PORT = 9093;

    //private InetSocketAddress hostAddress = new InetSocketAddress("localhost", 9093);
    private InetSocketAddress hostAddress = null;
    SocketChannel client = null;

    public Main(String hostname) {
        super();
        panel = new JPanel();
        this.setSize(200, 200);
        newgameButton = new JButton("start new game");
        newgameButton.addActionListener(new StartGameAction(this, hostname));
        playbackButton = new JButton("watch playback");
        playbackButton.addActionListener(new PlaybackAction(this));
        panel.add(newgameButton);
        panel.add(playbackButton);
        add(panel);

    }
    
    public void startGame(String hostname) throws IOException {
        File file = new File("log.txt");
        if(file.exists()) {
            file.delete();
        }
        terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.TALRYTH_32_32);
        add(terminal);
        pack();
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        addKeyListener(this);
        //screen = new WorldScreen();
        //repaint();
        if(hostname == null) {
            hostAddress = new InetSocketAddress("localhost", 9093);
        }
        else {
            hostAddress = new InetSocketAddress(hostname, 9093);
        }
        client = SocketChannel.open(hostAddress);
        new Thread(new Refresh(this)).start();
    }

    public SocketChannel getChannel() {
        return client;
    }

    public void playback() {
        terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.TALRYTH_32_32);
        add(terminal);
        pack();
        File file = new File("log.txt");
        if(!file.exists()) {
            return;
        }
        
        new Thread(new RefreshLog(this)).start();
    }
    

    public Screen getScreen() {
        return screen;
    }
    
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {  
            //System.out.println("key1");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            int dir = 0;
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                dir = 1;
            } 
            else if(e.getKeyCode() == KeyEvent.VK_UP) {
                dir = 2;
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                dir = 3;
            }
            else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                dir = 4;
            }
            if(dir == 0) return;
            oos.writeObject(dir);  
      
            byte[] bytes = bos.toByteArray(); 
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(bytes);
            buffer.flip();
            client.write(buffer);
            buffer.clear();
            bos.close();
            oos.close();  
            System.out.println("key");
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        String hostname = null;
        if(args.length == 1) {
            hostname = args[0];
        }
        Main app = new Main(hostname);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

}

class StartGameAction implements ActionListener {

    private Main M;
    private String hostname;

    public StartGameAction(Main M, String hostname) {
        this.M = M;
        this.hostname = hostname;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        M.remove(M.getPanel());
        try {
            M.startGame(hostname);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}

class PlaybackAction implements ActionListener {

    private Main M;

    public PlaybackAction(Main M) {
        this.M = M;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        M.remove(M.getPanel());
        M.playback();
    }

}