module com.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens com.minesweeper to javafx.fxml;
    exports com.minesweeper;
    exports com.minesweeper.modules;
}


