package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.logic.Graphics;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for handling user interaction with the program
 * @author Joshua David Crofts
 */
public class MainController
{
    @FXML
    private BorderPane borderPane;
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

    @FXML
    private Label hud_health;

    @FXML
    private Label hudEnemyHealth;

    @FXML
    private MenuItem closeMenu;

    @FXML
    private MenuItem openMenu;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Written by Joshua David Crofts aka cakeman105\nSemester project PJV 2024\ngithub.com/cakeman105", ButtonType.CLOSE);
        alert.setHeaderText("MIDGE2D - a simple lightweight 2D game engine");
        alert.setTitle("About");
        ImageView icon = new ImageView(String.valueOf(MainController.class.getResource("icon_large.png")));
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);
        alert.initOwner(cvs.getScene().getWindow());
        alert.showAndWait();
    }

    /**
     * Opens and starts drawing map pack
     */
    public void onOpenClick()
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

        Game game = new Game(sf.getAbsolutePath(), cvs, mainPane, hud_health, borderPane, hudEnemyHealth);
        game.run();
        closeMenu.setDisable(false);
        openMenu.setDisable(true);
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
        closeMenu.setDisable(true);
        openMenu.setDisable(false);
        Game.stop();
        hud_health.setVisible(false);
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