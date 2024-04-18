package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.logic.Graphics;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for handling user interaction with the program
 * @author Joshua David Crofts
 */
public class MainController
{
    @FXML
    private Label mapName;

    @FXML
    private Canvas cvs;

    @FXML
    private Pane mainPane;

    @FXML
    private Label mapLoadWarning;

    @FXML
    private Label mapLoadDescription;

    protected static final Logger logger = Logger.getLogger(MainController.class.getName());

    public MainController()
    {
        logger.setLevel(Level.SEVERE);
    }

    /**
     * Open the about dialog
     */
    public void onAboutClick()
    {

    }

    /**
     * Opens and starts drawing mappack
     * @throws FileNotFoundException
     */
    public void onOpenClick() throws FileNotFoundException
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select mappack manifest");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Map pack manifests", "*.txt"));
        File sf = fc.showOpenDialog(new Stage());
        if (sf == null)
            return;

        mapLoadWarning.setVisible(false);
        mapLoadDescription.setVisible(false);
        mapName.setText("Manifest: " + sf.getName());

        Game game = new Game(sf.getAbsolutePath(), cvs, mainPane);
        game.run();
    }

    /**
     * Closes mappack, calls GC
     * TODO
     */
    public void onCloseClick()
    {
        mapLoadWarning.setVisible(true);
        mapLoadDescription.setVisible(true);
        mapName.setText("No map loaded!");
        cvs.getGraphicsContext2D().clearRect(0, 0, cvs.getWidth(), cvs.getHeight());
        mainPane.setBackground(null);
    }

    /**
     * Turn logging globally on/off
     */
    public void onLoggingClick()
    {
        if (logger.getLevel() == Level.ALL)
        {
            logger.setLevel(Level.SEVERE);
            Logger.getLogger(Graphics.class.getName()).setLevel(Level.SEVERE);
        }
        else
        {
            logger.setLevel(Level.ALL);
            Logger.getLogger(Graphics.class.getName()).setLevel(Level.ALL);
        }
    }

    public void onSaveClick()
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save game state");
        fc.showSaveDialog(new Stage());
    }
}