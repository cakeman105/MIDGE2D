module cz.cvut.fel.pjv.midge2d {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.logging;
    requires javafx.media;

    opens cz.cvut.fel.pjv.midge2d to javafx.fxml;
    exports cz.cvut.fel.pjv.midge2d;
}