package values;

import resourceloader.ResourceLoader;

import java.awt.*;
import java.io.File;
import java.net.URL;

public class Parameter {

    public static final String[] ALLIANCE_CHOOSE_IMAGE_PATH = {("assets" + File.separator + "white" + File.separator + "pawn.png"), ("assets" + File.separator + "black" + File.separator + "pawn.png")};


     public static final String[] ALLIANCE_PROMOTION_CHOICE_WHITE_IMAGE_PATH = {("assets" + File.separator + "white" + File.separator + "night.png"), ("assets" + File.separator + "white" + File.separator + "bishop.png"), ("assets" + File.separator + "white" + File.separator + "rook.png"),("assets" + File.separator + "white" + File.separator + "queen.png")};
    public static final String[] ALLIANCE_PROMOTION_CHOICE_BLACK_IMAGE_PATH = {("assets" + File.separator + "black" + File.separator + "night.png"), ("assets" + File.separator + "black" + File.separator + "bishop.png"), ("assets" + File.separator + "black" + File.separator + "rook.png"),("assets" + File.separator + "black" + File.separator + "queen.png")};

    public static final String[] WHITE_IMAGE_PATH = {("assets" + File.separator + "white" + File.separator + "night.png"), ("assets" + File.separator + "white" + File.separator + "bishop.png"), ("assets" + File.separator + "white" + File.separator + "rook.png"),("assets" + File.separator + "white" + File.separator + "queen.png"),("assets" + File.separator + "white" + File.separator + "pawn.png"),("assets" + File.separator + "white" + File.separator + "king.png")};
    public static final String[] BLACK_IMAGE_PATH = {("assets" + File.separator + "black" + File.separator + "night.png"), ("assets" + File.separator + "black" + File.separator + "bishop.png"), ("assets" + File.separator + "black" + File.separator + "rook.png"),("assets" + File.separator + "black" + File.separator + "queen.png"),("assets" + File.separator + "black" + File.separator + "pawn.png"),("assets" + File.separator + "black" + File.separator + "king.png")};

    public static final String[] SOUND_PATH = {("assets" + File.separator + "audio" + File.separator + "move.wav"), ("assets" + File.separator + "audio" + File.separator + "cap.wav")};

    public static boolean HUMAN_CHOSE_WHITE = false;
    
    public static final Color DARK_SQUARE_COLOR = new Color(203,103,57);
    public static final Color LIGHT_SQUARE_COLOR = new Color(224,170,146);
    
    public static final Color SELECTED_SQUARE_COLOR = new Color(0,100,70);
    public static final Color KING_ON_CHECK_SQUARE_COLOR = Color.red;
    
    public static final Color LEGAL_SQUARE_COLOR = new Color(150,200,0);
    
    public static boolean FLIP = false;

    public static String getPath(char piece) {
        String[] paths;
        if (isUpperCase(piece)) {
            paths = WHITE_IMAGE_PATH;
        } else {
            paths = BLACK_IMAGE_PATH;
        }
        switch (toUpper(piece)) {
            case 'N':
                return paths[0];
            case 'B':
                return paths[1];
            case 'R':
                return paths[2];
            case 'Q':
                return paths[3];
            case 'P':
                return paths[4];
            case 'K':
                return paths[5];
            case 'M':
                return SOUND_PATH[0];
            case 'C':
                return SOUND_PATH[1];
            default:
                return null;
        }

    }
    
    public static boolean isUpperCase(char t) {
        return t == toUpper(t);
    }

    public static char toUpper(char c) {
        return Character.toString(c).toUpperCase().charAt(0);
    }

}
