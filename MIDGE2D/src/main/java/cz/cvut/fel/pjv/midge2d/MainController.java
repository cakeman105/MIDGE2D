package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.logic.MapDraw;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;

public class MainController
{
    @FXML
    private Label mapName;

    @FXML
    private Canvas cvs;

    @FXML
    private Pane mainPane;

    public void onAboutClick()
    {

    }

    public void onOpenClick() throws FileNotFoundException
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select mappack manifest");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Map pack manifests", "*.txt"));
        File sf = fc.showOpenDialog(new Stage());
        if (sf == null)
            return;

        mapName.setText("Map name: " + sf.getName());
        MapDraw mp = new MapDraw(sf.getAbsolutePath(), cvs);
        mp.loadFile("C:\\Users\\joshu\\Desktop\\testpack\\testpack_l0.midge");
        mp.draw();
    }
}