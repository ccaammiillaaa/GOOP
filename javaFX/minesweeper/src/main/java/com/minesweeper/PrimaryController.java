package com.minesweeper;


import java.io.IOException;
import javafx.fxml.FXML;


import com.minesweeper.modules.Tile;
import com.minesweeper.modules.Minesweeper;

public class PrimaryController {
    
    PrimaryController() {
        new Minesweeper().Run();
    }
}







