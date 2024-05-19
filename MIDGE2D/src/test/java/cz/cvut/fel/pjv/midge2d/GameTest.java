package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.entity.character.Enemy;
import cz.cvut.fel.pjv.midge2d.entity.character.Player;
import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;
import cz.cvut.fel.pjv.midge2d.logic.CollisionDetection;
import cz.cvut.fel.pjv.midge2d.logic.Direction;
import cz.cvut.fel.pjv.midge2d.logic.GameState;
import cz.cvut.fel.pjv.midge2d.logic.Graphics;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest
{

    private Game game;
    private Canvas canvas;
    private Pane pane;
    private Label health;
    private Label enemyHealth;
    private Label currentItem;
    private BorderPane borderPane;
    private Player player;
    private Graphics graphics;

    @BeforeAll
    public static void initAll()
    {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void init()
    {
        canvas = mock(Canvas.class);
        pane = mock(Pane.class);
        health = mock(Label.class);
        enemyHealth = mock(Label.class);
        currentItem = mock(Label.class);
        borderPane = mock(BorderPane.class);
        player = mock(Player.class);
        graphics = mock(Graphics.class);
        game = new Game("test_directory", canvas, pane, health, borderPane, enemyHealth, currentItem);
    }

    @Test
    @Order(1)
    public void intialGameStateTest()
    {
        assertEquals(GameState.GAME_STOPPED, Game.state);
    }

    @Test
    @Order(2)
    public void runGameStartTest()
    {
        game.run();
        assertEquals(GameState.GAME_RUNNING, Game.state);
    }

    @Test
    @Order(3)
    public void onPlayerDeathTest()
    {
        when(player.getHealth()).thenReturn(0);
        Platform.runLater(() ->
        {
            game.run();
            game.checkGameState();
            assertEquals(GameState.GAME_STOPPED, Game.state);
        });
    }

    @Test
    @Order(4)
    public void mapFinishedStateTest()
    {
        Platform.runLater(() ->
        {
            game.run();
            game.enemies.clear();
            game.index = game.mapList.size();
            game.checkGameState();
            assertEquals(GameState.GAME_FINISHED, Game.state);
        });
    }

    @Test
    @Order(5)
    public void enemyMovementTest()
    {
        Enemy enemy = new Enemy(new Item(ItemType.ITEM_KNIFE, 20), 100, Direction.MOVEMENT_RIGHT);
        enemy.setPosition(0, 0);
        CollisionDetection detection = mock(CollisionDetection.class);
        when(detection.checkCoords(anyInt(), anyInt())).thenReturn(true);
        enemy.attachCollision(detection);
        game.enemies.add(enemy);
        game.moveEnemies();

        assertNotEquals(0, enemy.getPositionY());
    }
}
