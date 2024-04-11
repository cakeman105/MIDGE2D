module cz.cvut.fel.pjv.midge2d {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.logging;

    opens cz.cvut.fel.pjv.midge2d to javafx.fxml;
    exports cz.cvut.fel.pjv.midge2d;
}