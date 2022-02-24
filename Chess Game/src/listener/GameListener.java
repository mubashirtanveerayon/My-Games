package listener;

import gui_components.Square;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import javax.swing.*;

import schneizel.Schneizel;

import ui.ChoiceWindow;
import ui.GamePanel;
import ui.Window;
import values.Parameter;

public class GameListener implements ActionListener {

    private Window window;
    private GamePanel gamePanel;
    Schneizel engine;
    char[][] board;

    Square selected;

    private Sound sound;

    ChoiceWindow cw;
    Integer type=0;

    int rowTo,colTo,promotionType = -1;

    public GameListener(Window window, GamePanel gamePanel, Schneizel engne_) {
        cw = new ChoiceWindow();
        this.window = window;
        this.gamePanel = gamePanel;
        this.engine = engne_;
        sound = new Sound();
        window.setVisible(false);
        choose();
        registerListener();
        board = engine.getBoard();
        renderBoard();
    }

    private void choose(){
        cw.setVisible(true);
        switch(type) {
            case 0:
                cw.showChoices("Choose alliance",20,Parameter.ALLIANCE_CHOOSE_IMAGE_PATH,20,20,300,200,this );
                break;
            case 1:
                if(engine.getTurn() == 'w') {
                    cw.showChoices("Choose promotion", 20, Parameter.ALLIANCE_PROMOTION_CHOICE_WHITE_IMAGE_PATH, 20, 20, 500, 200, this);
                }else{
                    cw.showChoices("Choose promotion", 20,Parameter.ALLIANCE_PROMOTION_CHOICE_BLACK_IMAGE_PATH, 20, 20, 500, 200, this);
                }
                break;
        }

    }

    private void registerListener() {
        window.newGame.addActionListener(this);
        window.changeSide.addActionListener(this);
        window.loadGame.addActionListener(this);
        window.saveGame.addActionListener(this);
        window.undo.addActionListener(this);
        window.setDepth.addActionListener(this);
        window.flipBoard.addActionListener(this);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gamePanel.boardSquare[i][j].addActionListener(this);
            }
        }

    }

    public void renderBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Parameter.FLIP) {
                    gamePanel.boardSquare[i][j].showPiece(board[8 - 1 - i][8 - 1 - j]);
                } else {
                    gamePanel.boardSquare[i][j].showPiece(board[i][j]);
                }
                gamePanel.boardSquare[i][j].setBackground(gamePanel.boardSquare[i][j].bgColor);
            }
        }
        if (Parameter.FLIP) {
            if (!engine.moves.isEmpty()) {
                int[] lastMove = engine.moves.get(engine.moves.size() - 1);
                gamePanel.boardSquare[8 - 1 - lastMove[0]][8 - 1 - lastMove[1]].setBackground(Parameter.SELECTED_SQUARE_COLOR);
                gamePanel.boardSquare[8 - 1 - lastMove[2]][8 - 1 - lastMove[3]].setBackground(Parameter.SELECTED_SQUARE_COLOR);
                if(engine.isKingInCheck(engine.getTurn() == 'w')){
                    int[] kingPos = engine.getKingPosition(engine.getTurn() == 'w');
                    gamePanel.boardSquare[8-1-kingPos[0]][8-1-kingPos[1]].setBackground(Parameter.KING_ON_CHECK_SQUARE_COLOR);
                }

            }
        } else {
            if (!engine.moves.isEmpty()) {
                int[] lastMove = engine.moves.get(engine.moves.size() - 1);
                gamePanel.boardSquare[lastMove[0]][lastMove[1]].setBackground(Parameter.SELECTED_SQUARE_COLOR);
                gamePanel.boardSquare[lastMove[2]][lastMove[3]].setBackground(Parameter.SELECTED_SQUARE_COLOR);
                if(engine.isKingInCheck(engine.getTurn() == 'w')){
                    int[] kingPos = engine.getKingPosition(engine.getTurn() == 'w');
                    gamePanel.boardSquare[kingPos[0]][kingPos[1]].setBackground(Parameter.KING_ON_CHECK_SQUARE_COLOR);
                }
            }
        }
        String state = engine.getState();
        if(state != null){
            gamePanel.disableBoard();
            if(state.equals("d")){
                JOptionPane.showMessageDialog(null, "Draw");
            }else if (state.equals("w")){
                JOptionPane.showMessageDialog(null, "White wins");
            }else{
                JOptionPane.showMessageDialog(null, "Black wins");
            }
        }else{
            gamePanel.enableBoard();
        }
    }

    public Thread computer(){
        return new Thread(){
            @Override
            public void run() {
                String side = engine.getTurn()=='w'?"White":"Black";
                System.out.println(side+" is thinking...");
                double start = System.nanoTime();
                int[] bestMove = engine.getBestMove();
                double elapsedTime = (System.nanoTime() - start)/1000000000;
                System.out.println(side+" has chosen move "+engine.cvtMove(bestMove)+" in "+elapsedTime+" seconds");
                sound.play(isCapture(bestMove));
                engine.makeMove(engine.cvtMove(bestMove));
                renderBoard();
            }
        };
    }

    public boolean isCapture(String move){
        return isCapture(engine.parseMove(move));
    }

    public boolean isCapture(int[] move){
        //System.out.println(board[move[2]][move[3]]);
        return (board[move[2]][move[3]] != ' ') || (Parameter.toUpper(board[move[0]][move[1]]) == 'P' && Math.abs(move[0] - move[2]) == 1 && Math.abs(move[1] - move[3]) == 1 && board[move[2]][move[3]] == ' ');
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(type == null){
            if (src == window.flipBoard) {
                Parameter.FLIP = !Parameter.FLIP;
                renderBoard();
            } else if (src == window.newGame) {
                engine = new Schneizel("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
                board = engine.getBoard();
                renderBoard();
                type = 0;
                window.setVisible(false);
                choose();
                gamePanel.enableBoard();
            }else if(src == window.changeSide){
                Parameter.HUMAN_CHOSE_WHITE = !Parameter.HUMAN_CHOSE_WHITE;
                if((Parameter.HUMAN_CHOSE_WHITE && engine.getTurn() == 'b') || (!Parameter.HUMAN_CHOSE_WHITE && engine.getTurn() == 'w')){
                    computer().start();
                }
            }
            else if (src == window.loadGame) {
                String fen = JOptionPane.showInputDialog(null, "Enter FEN string :", "Load Game", JOptionPane.QUESTION_MESSAGE);
                if (fen != null && !fen.isEmpty()) {
                    fen = fen.trim();
                    if (engine.isValidFEN(fen)) {
                        engine = new Schneizel(fen);
                        board = engine.getBoard();
                        renderBoard();
                        if((Parameter.HUMAN_CHOSE_WHITE&&engine.getTurn()=='b') || (!Parameter.HUMAN_CHOSE_WHITE&&engine.getTurn()=='w')){
                            computer().start();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid FEN string!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(engine.getState() == null){
                    gamePanel.enableBoard();
                }
            } else if (src == window.saveGame) {
                String fen = engine.getFen();
                try {
                    File file = new File("FEN.txt");
                    FileWriter fw = new FileWriter(file);
                    fw.write(fen);
                    fw.close();
                    JOptionPane.showMessageDialog(null, "Game FEN saved to file : " + file.getAbsolutePath(), "Save", 1);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }

            } else if (src == window.setDepth) {
                String input = JOptionPane.showInputDialog(null, "Enter difficulty level : (recomended not to enter higher than 4 as it requires a lot of cpu power to calculate best possible move)", "Difficulty", 3);
                if (input != null && !input.isEmpty() && Character.isDigit(input.charAt(0)) && Integer.parseInt(Character.toString(input.charAt(0))) >= 0) {
                    engine.setDifficulty(Integer.parseInt(Character.toString(input.charAt(0))));
                    JOptionPane.showMessageDialog(null, "Difficulty is set to " + String.valueOf(engine.getDifficulty()), "Success", 1);
                }
            }else if (src == window.undo){
                if(engine.history.size() >= 2) {
                    engine = new Schneizel(engine.history.get(engine.history.size() - 2));
                    board = engine.getBoard();
                    selected = null;
                    renderBoard();
                    if((Parameter.HUMAN_CHOSE_WHITE&&engine.getTurn()=='b') || (!Parameter.HUMAN_CHOSE_WHITE&&engine.getTurn()=='w')){
                        computer().start();
                    }
                }
            }
        }else{
            switch(type){
                case 0:
                    Parameter.HUMAN_CHOSE_WHITE = src == cw.buttons[0];
                    if(!Parameter.HUMAN_CHOSE_WHITE){
                        computer().start();
                    }
                    type = null;
                    window.setVisible(true);
                    cw.setVisible(false);
                    return;
                case 1:
                    for(int i=0;i<cw.buttons.length;i++){
                        if(src == cw.buttons[i]){
                            promotionType = i+1;
                        }
                    }
                    String moveStr;
                    if(Parameter.FLIP){
                        if(promotionType != 4) {
                            moveStr = engine.cvtMove(new int[]{8 - 1 - selected.file, 8 - 1 - selected.rank, rowTo, colTo, promotionType});
                        }else{
                            moveStr = engine.cvtMove(new int[]{8 - 1 - selected.file, 8 - 1 - selected.rank, rowTo, colTo});
                        }
                    }else {
                        if(promotionType != 4) {
                            moveStr = engine.cvtMove(new int[]{selected.file, selected.rank, rowTo, colTo, promotionType});
                        }else{
                            moveStr = engine.cvtMove(new int[]{selected.file, selected.rank, rowTo, colTo});
                        }
                    }
                    sound.play(isCapture(moveStr));
                    engine.makeMove(moveStr);
                    type = null;
                    cw.setVisible(false);
                    renderBoard();
                    computer().start();
                    return;
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (type==null&&src == gamePanel.boardSquare[i][j]) {
                    if (Parameter.FLIP) {
                        if (selected == null && board[8 - 1 - i][8 - 1 - j] != ' ' && ((Parameter.HUMAN_CHOSE_WHITE&&Parameter.isUpperCase(board[8 - 1 - i][8 - 1 - j]))||(!Parameter.HUMAN_CHOSE_WHITE&&!Parameter.isUpperCase(board[8 - 1 - i][8 - 1 - j])))) {
                            selected = gamePanel.boardSquare[i][j];
                            selected.setBackground(Parameter.SELECTED_SQUARE_COLOR);
                            String legalMovesStr = engine.generateLegalMoves(new int[]{8 - 1 - i, 8 - 1 - j});
                            for (String legals : legalMovesStr.split("\n")) {
                                if(!legals.isEmpty()) {
                                    int[] move = engine.parseMove(legals);
                                    gamePanel.boardSquare[8 - 1 - move[2]][8 - 1 - move[3]].setBackground(Parameter.LEGAL_SQUARE_COLOR);
                                }
                            }
                        } else if (selected != null) {
                            if((j==0&&board[selected.file][selected.rank]=='P')||(j==7&&board[selected.file][selected.rank]=='p')){
                                type = 1;
                                choose();
                                rowTo = 8-1-i;
                                colTo = 8-1-j;
                                return;
                            }
                            String moveStr = engine.cvtMove(new int[]{8 - 1 - selected.file, 8 - 1 - selected.rank, 8 - 1 - i, 8 - 1 - j});
                            String legalMovesStr = engine.generateLegalMoves(new int[]{8 - 1 - selected.file, 8 - 1 - selected.rank});
                            for (String legals : legalMovesStr.split("\n")) {
                                if(!legals.isEmpty() && legals.equals(moveStr)) {
                                    sound.play(isCapture(moveStr));
                                    engine.makeMove(moveStr);
                                    computer().start();
                                }
                            }
                            selected = null;
                            renderBoard();
                        }
                    } else {
                        if (selected == null && board[i][j] != ' '&& ((Parameter.HUMAN_CHOSE_WHITE&&Parameter.isUpperCase(board[i][j]))||(!Parameter.HUMAN_CHOSE_WHITE&&!Parameter.isUpperCase(board[i][j])))) {
                            selected = gamePanel.boardSquare[i][j];
                            selected.setBackground(Parameter.SELECTED_SQUARE_COLOR);
                            String legalMovesStr = engine.generateLegalMoves(new int[]{i, j});
                            for (String legals : legalMovesStr.split("\n")) {
                                if(!legals.isEmpty()){
                                    int[] move = engine.parseMove(legals);
                                    gamePanel.boardSquare[move[2]][move[3]].setBackground(Parameter.LEGAL_SQUARE_COLOR);
                                }
                            }
                        } else if (selected != null) {
                            if((j==0&&board[selected.file][selected.rank]=='P')||(j==7&&board[selected.file][selected.rank]=='p')){
                                type = 1;
                                choose();
                                rowTo = i;
                                colTo = j;
                                return;
                            }
                            String moveStr = engine.cvtMove(new int[]{selected.file, selected.rank, i, j});
                            String legalMovesStr = engine.generateLegalMoves(new int[]{selected.file, selected.rank});
                            for (String legals : legalMovesStr.split("\n")) {
                                if(!legals.isEmpty()&&legals.equals(moveStr)) {
                                    sound.play(isCapture(moveStr));
                                    engine.makeMove(moveStr);
                                    selected = null;
                                    computer().start();
                                }
                            }
                            selected = null;
                            renderBoard();
                        }
                    }
                }
            }
        }
    }
}
