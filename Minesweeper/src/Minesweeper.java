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

        //getter for r 
        public int getRows() {
            return r;
        }

        //setter for r
        public void setRows(int r) {
            this.r = r;
        }

        //getter for c
        public int getCols() {
            return c;
        }

        //setter for c
        public void setCols(int c) {
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

    int tilesClicked = 0; //check how many tiles are clicked, set to 0 by default
    boolean gameOver = false; //default false, as it is first when a bomb is clicked, it is gameover

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
                        if (gameOver) {
                            return;
                        }

                        MineTile tile = (MineTile) e.getSource();

                        //for left click
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (tile.getText() == "") {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                }
                                else {
                                    checkMine(tile.getRows(), tile.getCols());
                                }
                            }
                        }
                        //right click
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (tile.getText() == "" && tile.isEnabled()) { //empty string or already opened tile
                                tile.setText("🚩");
                            }
                            else if (tile.getText() == "🚩") {
                                tile.setText("");
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

    /**
     * places 10 bombs randomly
     */
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
            MineTile tile = board[bomb/numRows][bomb%numCols]; //creates tile reference using division and modulus 
            mineList.add(tile);
            //mineList.add(board[bomb/numRows][bomb%numCols]);
            System.out.println("Bomb added at: " + bomb + " which is at " + tile.getRows() + "," + tile.getCols());
            //System.out.println("Bomb added at: " + bomb + " which is at " + bomb/numRows + "," + bomb%numCols);
        }
    }

    /**
     * Reveals all mines on the board when a bomb is clicked
     */
    void revealMines() {
        for (int i = 0; i < mineList.size(); i++) {
            MineTile tile = mineList.get(i);
            tile.setText("💣");
            tile.setEnabled(false); //disables buttons that has been clicked
        }

        gameOver = true;
        textLabel.setText("You hit a bomb, game over!");
    }

    void checkMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {  //if it is out of bounce
            return;
        }

        MineTile tile = board[r][c];
        if (!tile.isEnabled()) {
            return;
        }

        tile.setEnabled(false); //disables buttons that has been clicked
        tilesClicked += 1;
        
        int minesFound = 0;

        //check fields above the clicked button
        minesFound += countMine(r-1,c-1); //top left
        minesFound += countMine(r-1, c); //top
        minesFound += countMine(r-1,c+1); //top right 

        //check left and right from clicked button
        minesFound += countMine(r, c-1); //left
        minesFound += countMine(r,c+1); //right

        //check fields below the clicked button  
        minesFound += countMine(r+1,c-1); //bottom left
        minesFound += countMine(r+1,c); //bottom
        minesFound += countMine(r+1,c+1); //bottom right

        if (minesFound > 0) {
            tile.setText(Integer.toString(minesFound));
        }
        else {
            tile.setText(""); //empty string

            //recursion function to check if there are no mines
            checkMine(r-1, c-1); //top left
            checkMine(r-1, c); //top
            checkMine(r-1, c+1); //top right

            checkMine(r,c-1); //left
            checkMine(r, c+1); //right

            checkMine(r+1, c-1); //bottom left
            checkMine(r+1, c); //bottom
            checkMine(r+1, c+1); //bottom right
        }

        if (tilesClicked == numRows * numCols - mineList.size()) {
            gameOver = true; //should not be able to click on more tiles
            textLabel.setText("Congratulations, you won!");
        }
    }

    int countMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) { //check if patch is outside the area/out of bounce
            return 0;
        }
        if (mineList.contains(board[r][c])) { //check is button is inside area
            return 1;
        }
        return 0;
    }
}
