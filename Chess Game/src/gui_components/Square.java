package gui_components;

import values.Parameter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Square extends JButton {

    public Color bgColor;

    public char pieceChar = ' ';
    
    public int file,rank;

    public Square(int file ,int rank){
        this.file = file;
        this.rank = rank;
        bgColor = (file + rank)%2!=0? Parameter.LIGHT_SQUARE_COLOR:Parameter.DARK_SQUARE_COLOR;
        setFocusable(false);
        setBorder(new LineBorder(new Color(50,50,50)));
        setBackground(bgColor);
    }

    public void showPiece(char piece){
        if (piece != ' '){
            setIcon(new ImageIcon(Parameter.getPath(piece)));
            pieceChar = piece;
        }else
            setIcon(null);
    }

}
