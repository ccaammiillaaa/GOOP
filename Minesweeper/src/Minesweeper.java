import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Minesweeper {
    private class MineTile extends JButton {
        int r; //row
        int c; // coloumn 

        public MineTile(int r, int c) { //constructor
            this.r = r;
            this.c = c;
        }
    }

    int tileSize = 70;
    int numRows = 8;
    int numCols = numRows; /*same as number of rows, as I want a square*/
    int boardWidth = numCols * tileSize;
    int boardHeight = numRows * tileSize; 

    //initialise frame and panel
    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
   
    //initialise 2D array of Mine tiles
    MineTile [][] board = new MineTile[numRows][numCols];
    //Arraylist for mines
    ArrayList<MineTile> mineList;

    Minesweeper() {
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(numRows, numCols)); //8x8
        frame.add(boardPanel);

        for (int r = 0; r < numRows; r++) {
            for(int c = 0; c < numCols; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;

                // System.out.println("Added tile at " + r + "," + c);

                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN,45));
                //tile.setText(String.valueOf(r) + String.valueOf(c));
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        MineTile tile = (MineTile) e.getSource();

                        //for left click
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (tile.getText() == "") {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                }
                            }
                        }
                    }
                });
                boardPanel.add(tile);
                frame.revalidate(); //for updating the window, ensuring all boxes appear
                frame.repaint();

            }
        }

        frame.setVisible(true); //only first load frame after loading all components

        setMines();

    }

    void setMines() {
        int numBombs = 10;
        ArrayList<Integer> bombgen = new ArrayList<>();
        mineList = new ArrayList<MineTile>();

        for(int i = 0; i<numRows*numCols; i++){
            bombgen.add(i);
        }
        
        Random r = new Random();

        for(int i = 0; i<numBombs; i++){
            int bombPlace = r.nextInt(bombgen.size());
            int bomb = bombgen.get(bombPlace);
            bombgen.remove(bombPlace);
            mineList.add(board[bomb/numRows][bomb%numCols]);
            System.out.println("Bomb added at: " + bomb + " which is at " + bomb/numRows + "," + bomb%numCols);
        }
    }

    void revealMines() {
        for (int i = 0; i < mineList.size(); i++) {
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }
    }
}
