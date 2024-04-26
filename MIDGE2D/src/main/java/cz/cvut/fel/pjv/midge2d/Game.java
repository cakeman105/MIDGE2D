package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.entity.character.Enemy;
import cz.cvut.fel.pjv.midge2d.entity.character.Player;
import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;
import cz.cvut.fel.pjv.midge2d.logic.*;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class responsible for game runtime
 * @author Joshua David Crofts
 */
public class Game
{
    public static GameState state;
    private static AnimationTimer timer;
    private final String directory;
    private char[][] map;
    private final Canvas canvas;
    private final ArrayList<String> mapList;
    private final ArrayList<Enemy> enemies;
    private final Graphics graphics;
    private final Pane pane;
    private final Label health;
    private final Player player;
    private final BorderPane borderPane;
    private final KeyHandler handler;
    private static final int ROW_COUNT = 19; //very strange numbers
    private static final int COL_COUNT = 26;
    protected static final Logger logger = Logger.getLogger(Game.class.getName());

    public Game(String directory, Canvas canvas, Pane pane, Label health, BorderPane bp)
    {
        this.player = new Player();
        this.enemies = new ArrayList<>();
        this.directory = directory;
        this.map = new char[ROW_COUNT][COL_COUNT];
        this.canvas = canvas;
        this.mapList = new ArrayList<>();
        this.graphics = new Graphics(this.canvas);
        this.handler = new KeyHandler(this.map, this.player);
        this.pane = pane;
        this.health = health;
        this.health.setVisible(true);
        this.borderPane = bp;
    }

    public void run()
    {
        try
        {
            Game.state = GameState.GAME_RUNNING;
            loadMaps();
            loadMapToCharArray(this.mapList.getFirst());
            CollisionDetection detection = new CollisionDetection(this.map);
            this.player.attachCollision(detection);
            this.enemies.forEach(e -> e.attachCollision(detection));
            this.pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
            borderPane.setOnKeyPressed(this.handler);
            timer = new AnimationTimer()
            {
                private long update = 0;
                @Override
                public void handle(long l)
                {
                    if (l - update >= 1_000_000_50)
                    {
                        tick();
                        update = l;
                    }
                }
            };

            timer.start();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.toString(), ButtonType.OK);
            alert.setHeaderText(ex.getMessage());
            alert.initOwner(canvas.getScene().getWindow());
            alert.showAndWait();
        }
    }

    /**
     * load map paths to map list
     * @throws FileNotFoundException this exception will not be thrown, as it's handled by the class that calls this
     */
    private void loadMaps() throws FileNotFoundException
    {
        File file = new File(this.directory);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
            this.mapList.add(file.getParent() + scanner.nextLine());
    }

    /**
     * load map data into 2D char array for easy representation
     * @param fileName map to be loaded
     * @throws FileNotFoundException ditto as loadMaps
     */
    private void loadMapToCharArray(String fileName) throws FileNotFoundException
    {
        Random generator = new Random();
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

                    if(map[i][j] == 'E')
                    {
                        this.enemies.add(new Enemy(new Item(ItemType.ITEM_GUN, 5, 20), 100, generator.nextInt(2) == 1 ? Direction.MOVEMENT_RIGHT : Direction.MOVEMENT_LEFT));
                        this.enemies.getLast().setPosition(i, j);
                    }
                }

                i++;
            }
        }
    }

    private void tick()
    {
        graphics.clearCanvas();
        moveEnemies();
        graphics.draw(this.map);
        graphics.drawHud(this.player);
        health.setText(String.format("Health: %d", this.player.getHealth()));
    }

    static public void stop()
    {
        timer.stop();
        Game.state = GameState.GAME_STOPPED;
    }

    /**
     * move all enemies into random direction until they hit wall
     */
    private void moveEnemies()
    {
        for (Enemy e : this.enemies)
        {
            map[e.getPositionX()][e.getPositionY()] = ' ';
            e.moveEnemy();
            map[e.getPrevPositionX()][e.getPrevPositionY()] = ' ';
            map[e.getPositionX()][e.getPositionY()] = 'E';
        }
    }
}
