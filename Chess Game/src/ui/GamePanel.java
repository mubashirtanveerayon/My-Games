package ui;

import gui_components.Square;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public Square[][] boardSquare;

    public boolean boardDisabled = false;

    public GamePanel() {
        super(new GridLayout(8, 8));
        initComponents();
    }

    public void initComponents() {
        boardSquare = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardSquare[j][i] = new Square(j,i);
                add(boardSquare[j][i]);
            }
        }
    }

    public void disableBoard(){
        if(boardDisabled){
            return;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardSquare[j][i].setEnabled(false);
            }
        }
        boardDisabled = true;
    }

    public void enableBoard(){
        if(!boardDisabled){
            return;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardSquare[j][i].setEnabled(true);
            }
        }
        boardDisabled = false;
    }
    

}
