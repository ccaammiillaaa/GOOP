package com.minesweeper.modules;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class UI implements IUI {
    ArrayList<ArrayList<Tile>> tiles;
    BiConsumer<Integer, Integer> buttonPressed = null;

    @FXML
    public GridPane buttonGrid;
    
    @FXML
    public Label title;

    public void SetupBoard(ArrayList<ArrayList<Tile>> tiles, BiConsumer<Integer, Integer> buttonPressed) {
        this.tiles = tiles; 
        this.buttonPressed = buttonPressed;
    }

    public void UpdateTitle(String title) {
        this.title.setText(title);
    }

    public void Render() {
        for (int row = 0; row < tiles.size(); row++) {
            for (int col = 0, cols = tiles.get(row).size(); col < cols; col++) {
                Button btn = new Button("Btn " + (row * cols + col + 1));
                // btn.setOnAction(e -> buttonPressed.accept(row, col));
                
                btn.setPrefSize(40, 40);
                // btn UI....
                buttonGrid.add(btn, col, row);
            }
        }
    }
}