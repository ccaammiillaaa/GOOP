package com.minesweeper.modules;

import java.util.ArrayList;
import java.util.Random;

public class Minesweeper  {

    private int numRows = 8;
    private int numCols = 8;
    private int numBombs = 10;

    private IUI ui = new UI();

    private ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();

    private ArrayList<Integer> bombgen = new ArrayList<Integer>();
    // private MineTile[][] board = new MileTile[numRows][numCols];
    private ArrayList<Integer> mineList = new ArrayList<Integer>();

    int tilesClicked = 0;
    boolean gameOver = false;
    
    public void Run () {
        mineGen();

        ui.SetupBoard(board, this::buttonPress);
        ui.UpdateTitle("Minesweeper");

    }


    void mineGen (){
        
        for (int i = 0; i < numRows*numCols; i++) {
            bombgen.add(i);
        }

        Random R = new Random();

        for(int j = 0; j < numBombs; j++) {
            int bombPlace = R.nextInt(bombgen.size());
            bombgen.remove(bombPlace);
            mineList.add(bombPlace);
            try {
                int value = bombgen.get(bombPlace);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: Invalid bomb index");
            }
        }

        for (int i = 0; i < numRows; i++) {
            
            ArrayList<Tile> row = new ArrayList<Tile>();

            for (int j = 0; j < numCols; j++) {
                boolean mineHere = false;
                for (int k=0; k<mineList.size(); k++){
                    if(i == mineList.get(k)/8 && j == mineList.get(k)%8 ){
                        mineHere = true;
                        break;
                    }
                }
                row.add(new Tile(mineHere ? TileTypes.MINE : TileTypes.BLANK));
            }

            board.add(row);

        }
    }

    private void buttonPress(int row, int col){
        if(board.get(row).get(col).IsMine()){revealMines();}
        else{ checkMine(row, col);}
    }

    
    private void checkMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) return;
        // MineTile tile = board[r][c];
        // if (tile.isDisable()) return;

        // tile.setDisable(true);
        tilesClicked++;

        int minesFound = countMine(r - 1, c - 1) + countMine(r - 1, c) + countMine(r - 1, c + 1)
                + countMine(r, c - 1) + countMine(r, c + 1)
                + countMine(r + 1, c - 1) + countMine(r + 1, c) + countMine(r + 1, c + 1);

        if (minesFound > 0) {
            // tile.setText(Integer.toString(minesFound));
        } else {
            // tile.setText("");
            checkMine(r - 1, c - 1); checkMine(r - 1, c); checkMine(r - 1, c + 1);
            checkMine(r, c - 1);                 checkMine(r, c + 1);
            checkMine(r + 1, c - 1); checkMine(r + 1, c); checkMine(r + 1, c + 1);
        }

        if (tilesClicked == numRows * numCols - mineList.size()) {
            gameOver = true;
            // textLabel.setText("Congratulations, you won!");
        }
    }

    private int countMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) return 0;
        return board.get(r).get(c).IsMine() ? 1 : 0;
    }

   
    void revealMines() {
        // for (int i = 0; i < mineList.size(); i++) {
        //     MineTile tile = mineList.get(i);
        //     tile.setText("💣");
        //     tile.setEnabled(false); //disables buttons that has been clicked
        // }

        gameOver = true;
        // textLabel.setText("You hit a bomb, game over!");
    }

}   

