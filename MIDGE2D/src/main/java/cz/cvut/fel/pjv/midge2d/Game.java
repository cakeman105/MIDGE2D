package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.entity.character.Player;
import cz.cvut.fel.pjv.midge2d.logic.CollisionDetection;
import cz.cvut.fel.pjv.midge2d.logic.Graphics;
import cz.cvut.fel.pjv.midge2d.logic.KeyHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class responsible for game runtime
 * @author Joshua David Crofts
 */
public class Game
{
    private static AnimationTimer timer;
    private String directory;
    private char[][] map;
    private Canvas canvas;
    private ArrayList<String> mapList;
    private final Graphics graphics;
    private final Player player;
    private final KeyHandler handler;
    private static final int ROW_COUNT = 19; //very strange numbers
    private static final int COL_COUNT = 26;
    protected static final Logger logger = Logger.getLogger(Game.class.getName());

    public Game(String directory, Canvas canvas, Pane pane)
    {
        this.player = new Player();
        this.directory = directory;
        this.map = new char[ROW_COUNT][COL_COUNT];
        this.canvas = canvas;
        this.mapList = new ArrayList<>();
        this.graphics = new Graphics(this.canvas);
        BackgroundImage image = new BackgroundImage(new Image(String.valueOf(this.getClass().getResource("background.png"))), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(image));
        this.handler = new KeyHandler(this.map, this.player);
    }

    public void run()
    {
        try
        {
            loadMaps();
            loadMapToCharArray(this.mapList.getFirst());
            CollisionDetection detection = new CollisionDetection(this.map);
            this.player.attachCollision(detection);
            canvas.setOnKeyPressed(this.handler);
            canvas.setFocusTraversable(true);
            this.timer = new AnimationTimer()
            {
                private long update = 0;
                @Override
                public void handle(long l)
                {
                    if (l - update >= 1_000_000_00)
                    {
                        tick();
                        update = l;
                    }
                }
            };

            this.timer.start();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void loadMaps() throws FileNotFoundException
    {
        File file = new File(this.directory);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
            this.mapList.add(file.getParent() + scanner.nextLine());
    }

    private void loadMapToCharArray(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file))
        {
            int i = 0;
            while (scanner.hasNextLine())
            {
                char[] line = scanner.nextLine().toCharArray();
                for (int j = 0; j < line.length; j++)
                {
                    map[i][j] = line[j];
                    if (map[i][j] == 'P')
                        this.player.setPosition(i, j);
                }

                i++;
            }
        }
    }

    private void tick()
    {
        graphics.clearCanvas();
        graphics.draw(this.map);
        canvas.requestFocus();
    }

    static public void stop()
    {
        timer.stop();
    }
}
