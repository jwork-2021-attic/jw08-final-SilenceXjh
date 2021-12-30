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

public class Main extends JFrame {

    private AsciiPanel terminal;

    private Screen screen;

    private Selector selector;

	private InetSocketAddress listenAddress;
	private final static int PORT = 9093;

    private int count = 0;

    public Main() {

        terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.TALRYTH_32_32);
        add(terminal);
        pack();
        //this.setFocusable(true);
        //this.requestFocus();
        //this.requestFocusInWindow();
        //addKeyListener(this);
        screen = new WorldScreen();
        //repaint();

        listenAddress = new InetSocketAddress("localhost", PORT);
        try {
            this.selector = Selector.open();
		    ServerSocketChannel serverChannel = ServerSocketChannel.open();
		    serverChannel.configureBlocking(false);

		// bind server socket channel to port
		    serverChannel.socket().bind(listenAddress);
		    serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

		    System.out.println("Server started on port >> " + PORT);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        new Thread(new Refresh(this)).start();
    }

    private void startServer() throws IOException {

		while (true) {
            synchronized(selector) {
			// wait for events
			int readyCount = selector.select();
			if (readyCount == 0) {
				continue;
			}

			// process selected keys...
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();

				// Remove key from set so we don't process it twice
				iterator.remove();

				if (!key.isValid()) {
					continue;
				}

				if (key.isAcceptable() && count < 3) { // Accept client connections
                    count++;
					this.accept(key, count);
                    System.out.println(count);
				} else if (key.isReadable()) { // Read from client
					this.read(key);
				} 
			}
            }
		}
	}

    // accept client connection
	private void accept(SelectionKey key, int count) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel = serverChannel.accept();
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		SocketAddress remoteAddr = socket.getRemoteSocketAddress();
		System.out.println("Connected to: " + remoteAddr);

		/*
		 * Register channel with selector for further IO (record it for read/write
		 * operations, here we have used read operation)
		 */
		channel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, count);
        screen.addBro(count);
	}

    private void read(SelectionKey key) throws IOException {
        int index = (int)key.attachment();
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int numRead = -1;
		numRead = channel.read(buffer);

		if (numRead == -1) {
			Socket socket = channel.socket();
			SocketAddress remoteAddr = socket.getRemoteSocketAddress();
			System.out.println("Connection closed by client: " + remoteAddr);
			channel.close();
			key.cancel();
			return;
		}

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array());
            ObjectInputStream ois = new ObjectInputStream(bis);
            int dir = (int)ois.readObject();
            screen.respondToUserInput(dir, index);
            bis.close();
            ois.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        

		//byte[] data = new byte[numRead];
		//System.arraycopy(buffer.array(), 0, data, 0, numRead);
		//System.out.println("Got: " + new String(data));
	}

    public void write() throws Exception {
        synchronized(selector) {
        int readyCount = selector.select();
		if (readyCount == 0) {
			return;
		}
        Set<SelectionKey> readyKeys = selector.selectedKeys();
		Iterator iterator = readyKeys.iterator();
		while (iterator.hasNext()) {
			SelectionKey key = (SelectionKey) iterator.next();
			iterator.remove();
			if (!key.isValid()) {
				continue;
			}
            if(key.isWritable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
                ByteArrayOutputStream bos = null;
                ObjectOutputStream oos = null;
                try {
                    bos = new ByteArrayOutputStream();
                    oos = new ObjectOutputStream(bos);
                    synchronized(screen) {
                    oos.writeObject(screen);
                    }
                    byte[] bytes = bos.toByteArray();
                    buffer.put(bytes);
                    buffer.flip();
                    channel.write(buffer);
                    buffer.clear();
                    //System.out.println("out");
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
                //channel.close();
                bos.close();
                oos.close();
            }
        }
        }
    }

    public Screen getScreen() {
        return screen;
    }
    
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    

    public static void main(String[] args) {
        Main app = new Main();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        try {
			app.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}

