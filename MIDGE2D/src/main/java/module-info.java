module cz.cvut.fel.pjv.midge2d {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.logging;
    requires javafx.media;

    //requires org.junit.jupiter.api;
    //requires org.junit.jupiter.params;
    //requires org.mockito;

    opens cz.cvut.fel.pjv.midge2d to javafx.fxml;
    exports cz.cvut.fel.pjv.midge2d;
}