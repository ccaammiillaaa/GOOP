module beebeebooboo {
    requires javafx.controls;
    requires javafx.fxml;

    opens beebeebooboo to javafx.fxml;
    exports beebeebooboo;
}
