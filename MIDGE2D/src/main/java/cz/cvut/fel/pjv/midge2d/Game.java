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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for game runtime
 *
 * @author Joshua David Crofts
 */
public class Game
{
    public static GameState state;
    private static AnimationTimer timer;
    private final String directory;
    private final char[][] map;
    private final Canvas canvas;
    private final ArrayList<String> mapList;
    private final ArrayList<Enemy> enemies;
    private final Graphics graphics;
    private final Pane pane;
    private final Label health;
    private final Label enemyHealth;
    private final Player player;
    private boolean isFighting;
    private final BorderPane borderPane;
    private Enemy enemyFighting;
    private final KeyHandler handler;
    private static final int ROW_COUNT = 19; //very strange numbers
    private static final int COL_COUNT = 26;
    protected static final Logger logger = Logger.getLogger(Game.class.getName());

    public Game(String directory, Canvas canvas, Pane pane, Label health, BorderPane bp, Label enemyHealth)
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
        this.enemyHealth = enemyHealth;
        this.isFighting = false;
        this.enemyFighting = null;

        logger.setLevel(Level.SEVERE);
    }

    public void run()
    {
        try
        {
            logger.info("Init game");
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
        } catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.toString(), ButtonType.OK);
            alert.setHeaderText(ex.getMessage());
            alert.initOwner(canvas.getScene().getWindow());
            alert.showAndWait();
        }
    }

    /**
     * load map paths to map list
     *
     * @throws FileNotFoundException this exception will not be thrown, as it's handled by the class that calls this
     */
    private void loadMaps() throws FileNotFoundException
    {
        File file = new File(this.directory);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine())
            this.mapList.add(file.getParent() + scanner.nextLine());
    }

    /**
     * load map data into 2D char array for easy representation
     *
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

                    if (map[i][j] == 'E')
                    {
                        this.enemies.add(new Enemy(new Item(ItemType.ITEM_GUN, 5, 20), 100, generator.nextInt(2) == 1 ? Direction.MOVEMENT_RIGHT : Direction.MOVEMENT_LEFT));
                        this.enemies.getLast().setPosition(i, j);
                    }
                }
                i++;
            }
        }
    }

    /**
     * logic that must be run every render
     */
    private void tick()
    {
        logger.info("tick!");
        graphics.clearCanvas();
        moveEnemies();
        fight();
        checkGameState();
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
            if (e.getMoving())
            {
                map[e.getPositionX()][e.getPositionY()] = ' ';
                e.moveEnemy();
                map[e.getPrevPositionX()][e.getPrevPositionY()] = ' ';
                map[e.getPositionX()][e.getPositionY()] = 'E';
            }
        }
    }

    /**
     * checks every tick for interaction with enemies, spawns new thread for enemy's reaction if yes
     */
    private void fight()
    {
        if ((map[player.getPositionX()][player.getPositionY() + 1] == 'E' || map[player.getPositionX()][player.getPositionY() - 1] == 'E') && !isFighting)
        {
            this.enemyFighting = map[player.getPositionX()][player.getPositionY() + 1] == 'E' ? enemies.stream().filter(enemy -> enemy.getPositionX() == player.getPositionX() && enemy.getPositionY() == player.getPositionY() + 1).findFirst().get() :
            enemies.stream().filter(enemy -> enemy.getPositionX() == player.getPositionX() && enemy.getPositionY() == player.getPositionY() - 1).findFirst().get();
            final Enemy enemyCopy = enemyFighting;
            isFighting = true;
            this.enemyHealth.setVisible(true);
            handler.setEnemy(enemyFighting);
            enemyFighting.setMoving(false);
            logger.info(String.format("Player fighting enemy %d with %d health", enemyFighting.hashCode(), enemyFighting.getHealth()));
            new Thread(() ->
            {
                try
                {
                    while (player.getHealth() > 0 && enemyFighting.getHealth() > 0)
                    {
                        enemyCopy.hit(player);
                        Thread.sleep(1500);
                    }
                    Thread.currentThread().interrupt(); //stop thread on enemy death or player death
                }
                catch (InterruptedException ignored){}
            }).start();

            player.setMoving(false);
            this.enemyHealth.setText(String.format("Enemy health: %d", enemyFighting.getHealth()));
        }

        if (isFighting)
        {
            enemyHealth.setText(String.format("Enemy health: %d",enemyFighting.getHealth()));
            if (enemyFighting.getHealth() <= 0)
            {
                isFighting = false;
                enemyHealth.setVisible(false);
                map[enemyFighting.getPositionX()][enemyFighting.getPositionY()] = ' '; //wiped from the face of the earth
                this.player.setMoving(true);
            }
        }
    }

    /**
     * Displays window with statistics on failure
     */
    private void checkGameState()
    {
        if (player.getHealth() <= 0)
        {
            state = GameState.GAME_STOPPED;
            stop();
            Alert alert = new Alert(Alert.AlertType.WARNING, "You lost! Better luck next time...", ButtonType.CLOSE);
            alert.setTitle("You lost!");
            alert.setHeaderText("Dead!");
            alert.show();
            logger.info("Player dead");
        }
    }
}
