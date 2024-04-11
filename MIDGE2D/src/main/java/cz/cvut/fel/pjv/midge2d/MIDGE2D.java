package cz.cvut.fel.pjv.midge2d;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MIDGE2D extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MIDGE2D.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setResizable(false);
        stage.setTitle("MIDGE2D Game Engine");
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(MIDGE2D.class.getResource("icon.png"))));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}