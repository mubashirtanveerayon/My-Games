package game;

import listener.GameListener;
import schneizel.Schneizel;
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
    Schneizel engine;
   
    public static void main(String[] args) {
        new Main();
    }
    
    public Main(){
        init();
    }
    
    public void init(){
        window = new Window();
        gamePanel = new GamePanel();
        engine = new Schneizel();
        listener = new GameListener(window,gamePanel,engine);
        window.add(gamePanel);
    }
    
}
