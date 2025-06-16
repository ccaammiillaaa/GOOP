package com.minesweeper.modules;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/*
This interface is used for all rendering types 
fx. desktop UI using javaFX or 
ascii text for terminal implementation
*/
public interface IUI {
    public void SetupBoard(ArrayList<ArrayList<Tile>> tiles, BiConsumer<Integer, Integer> buttonPressed);
    public void UpdateTitle(String title);
    public void Render();

}