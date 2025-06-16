package beebeebooboo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.*;

public class PrimaryController {

    @FXML
    private GridPane boardGrid;
    @FXML
    private Label statusLabel;

    private final int numRows = 8;
    private final int numCols = 8;
    private MineTile[][] board = new MineTile[numRows][numCols];
    private List<MineTile> mineList = new ArrayList<>();
    private int tilesClicked = 0;
    private boolean gameOver = false;

    @FXML
    private void initialize() {
        setupBoard();
        new MineSetter().execute();
    }

    private void setupBoard() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                MineTile tile = new MineTile(r, c);
                tile.setPrefSize(50, 50);
                tile.setOnMouseClicked(event -> {
                    if (gameOver) return;

                    switch (event.getButton()) {
                        case PRIMARY:
                            if (tile.getText().isEmpty()) {
                                if (mineList.contains(tile)) {
                                    new MineRevealer().execute();
                                } else {
                                    new SafeTileChecker().execute(tile.getRow(), tile.getCol());
                                }
                            }
                            break;

                        case SECONDARY:
                            if (tile.getText().isEmpty() && !tile.isDisable()) {
                                tile.setText("🚩");
                            } else if ("🚩".equals(tile.getText())) {
                                tile.setText("");
                            }
                            break;
                    }
                });

                board[r][c] = tile;
                boardGrid.add(tile, c, r);
            }
        }
    }

    private class MineTile extends Button {
        int row, col;
        MineTile(int r, int c) {
            this.row = r;
            this.col = c;
        }
    }

    private class MineSetter {
        private final int bombs = 10;

        public void execute() {
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < numRows * numCols; i++) positions.add(i);
            Collections.shuffle(positions);
            for (int i = 0; i < bombs; i++) {
                int pos = positions.get(i);
                int r = pos / numCols;
                int c = pos % numCols;
                mineList.add(board[r][c]);
            }
        }
    }

    private class MineRevealer {
        public void execute() {
            for (MineTile tile : mineList) {
                tile.setText("💣");
                tile.setDisable(true);
            }
            gameOver = true;
            statusLabel.setText("You hit a bomb. Game Over!");
        }
    }

    private class MineChecker {
        public void execute(int r, int c) {
            if (r < 0 || r >= numRows || c < 0 || c >= numCols) return;
            MineTile tile = board[r][c];
            if (tile.isDisabled()) return;

            tile.setDisable(true);
            tilesClicked++;

            int minesFound = 0;
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) continue;
                    minesFound += countMine(r + dr, c + dc);
                }
            }

            if (minesFound > 0) {
                tile.setText(String.valueOf(minesFound));
            } else {
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        execute(r + dr, c + dc);
                    }
                }
            }

            if (tilesClicked == numRows * numCols - mineList.size()) {
                gameOver = true;
                statusLabel.setText("Congratulations, you won!");
            }
        }

        private int countMine(int r, int c) {
            if (r < 0 || r >= numRows || c < 0 || c >= numCols) return 0;
            return mineList.contains(board[r][c]) ? 1 : 0;
        }
    }
}
