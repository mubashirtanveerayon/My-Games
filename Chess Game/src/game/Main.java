package game;

import listener.GameListener;
import ui.GamePanel;
import ui.Window;
/**
 *
 * @author ayon2
 */
public class Main {
    
    Window window;
    GamePanel gamePanel;
    GameListener listener;

    public static void main(String[] args) {
        new Main();
    }
    
    public Main(){
        init();
    }
    
    public void init(){
        window = new Window();
        gamePanel = new GamePanel();
        listener = new GameListener(window,gamePanel);
        window.add(gamePanel);
    }
    
}
