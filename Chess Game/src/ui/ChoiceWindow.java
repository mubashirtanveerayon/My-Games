package ui;

import listener.GameListener;
import resourceloader.ResourceLoader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChoiceWindow extends JFrame {

    
    JPanel panel;
    public JButton[] buttons;
    JLabel label;

    public ChoiceWindow(){
        setDefaultCloseOperation(3);
        setResizable(false);
        initComponents();
    }
    
    public void initComponents(){ 
        panel = new JPanel();
        label = new JLabel();
        label.setHorizontalAlignment(0);
        add(label,BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);
    }

    public void showChoices(String text, int fontSize, URL[] images, int hGap, int vGap, int width, int height, GameListener listener){
        setSize(width,height);
        label.setText(text);
        label.setFont(new Font("Arial",Font.BOLD,fontSize));
        ((FlowLayout)panel.getLayout()).setHgap(hGap);
        ((FlowLayout)panel.getLayout()).setVgap(vGap);
        removeComponents();
        buttons = new JButton[images.length];
        for(int i=0;i<buttons.length;i++){
            buttons[i] = new JButton();
            buttons[i].setFocusable(false);
            buttons[i].setIcon(new ImageIcon(images[i]));
            buttons[i].addActionListener(listener);
            panel.add(buttons[i]);
        }
        setVisible(true);
    }

    private void removeComponents(){
        panel.removeAll();
    }
    
    
}
