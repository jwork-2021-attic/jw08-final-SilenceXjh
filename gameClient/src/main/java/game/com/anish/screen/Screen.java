package game.com.anish.screen;

import java.awt.event.KeyEvent;

import game.asciiPanel.AsciiPanel;

public interface Screen {

    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(int dir, int index);

    public void addBro(int index);
}
