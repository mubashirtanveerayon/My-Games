package ui;

import listener.GameListener;

import javax.swing.*;

public class Window extends JFrame{

    public int width,height;

    public String title;

    public JMenuBar menuBar;

    public JMenu file;

    public JMenuItem newGame,changeSide,loadGame,saveGame,undo,setDepth,flipBoard;

    public Window(){
        width = 600;
        height = 500;
        title = "My Chess Game";
        setSize(width, height);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setTitle(title);
        initComponents();
    }


    public void initComponents(){
        menuBar = new JMenuBar();
        file = new JMenu("File");
        newGame = new JMenuItem("New Game");
        changeSide = new JMenuItem("Change Side");
        loadGame = new JMenuItem("Load Game");
        saveGame = new JMenuItem("Save Game");
        undo = new JMenuItem("Undo");
        setDepth = new JMenuItem("Set Depth");
        flipBoard = new JMenuItem("Flip Board");
        file.add(newGame);
        file.add(changeSide);
        file.add(loadGame);
        file.add(saveGame);
        file.add(undo);
        file.add(setDepth);
        file.add(flipBoard);
        menuBar.add(file);
        
        setJMenuBar(menuBar);
    }

}
