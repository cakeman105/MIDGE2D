package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.entity.character.Player;
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
import org.mockito.Mockito;

import java.io.File;

import static cz.cvut.fel.pjv.midge2d.entity.item.ItemType.ITEM_KNIFE;
import static cz.cvut.fel.pjv.midge2d.entity.item.ItemType.ITEM_STEEL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ProcessTest
{
    private Game game;
    private Player player;
    private Graphics graphics;

    @BeforeAll
    public static void before()
    {
        //Platform.startup(() -> {});
    }

    @BeforeEach
    public void init()
    {
        Canvas canvas = mock(Canvas.class);
        Pane pane = mock(Pane.class);
        Label health = mock(Label.class);
        Label enemyHealth = mock(Label.class);
        Label currentItem = mock(Label.class);
        BorderPane borderPane = mock(BorderPane.class);
        player = new Player();
        graphics = mock(Graphics.class);
        game = new Game("test_directory", canvas, pane, health, borderPane, enemyHealth, currentItem);
    }

    @Test
    @Order(1)
    public void playerMovementTest()
    {
        Platform.runLater(() ->
        {
            game.setMap(new char[][]{
                    {'P', ' ', ' '},
                    {' ', ' ', ' '},
                    {' ', ' ', ' '}});
            player.setPosition(0, 0);
            CollisionDetection cd = mock(CollisionDetection.class);
            when(cd.checkCoords(anyInt(), anyInt())).thenReturn(true);
            player.attachCollision(cd);
            game.run();
            player.move(Direction.MOVEMENT_RIGHT);
            assert(player.getPositionX() == 0 && player.getPositionY() == 1);
            player.move(Direction.MOVEMENT_DOWN);
            assert(player.getPositionX() == 1 && player.getPositionY() == 1);
            player.move(Direction.MOVEMENT_LEFT);
            assert(player.getPositionX() == 1 && player.getPositionY() == 0);
        });
    }

    @Test
    @Order(2)
    public void pickUpItemTest()
    {
        Platform.runLater(() ->
        {
            game.setMap(new char[][]{{'P', 'K'}});
            game.player.setPosition(0, 0);
            assertTrue(game.player.getInventory().getInventory().isEmpty());
            game.checkItems();
            assertEquals(ITEM_KNIFE, game.player.getInventory().getInventory().get(ITEM_KNIFE).getType());
        });
    }

    @Test
    @Order(3)
    public void fightTest()
    {
        Platform.runLater(() ->
        {
            game.run();
            game.setMap(new char[][]{{'P', 'E'}});
            Game spy = Mockito.spy(game);
            game.tick();
            verify(spy, atLeastOnce()).fight();
            while (game.player.getHealth() > 0)
            {
                int playerHealth = game.player.getHealth();
                game.tick();
                assertTrue(playerHealth > game.player.getHealth());
            }
            assertEquals(GameState.GAME_STOPPED, Game.state);
        });
    }

    @Test
    @Order(4)
    public void pickUpAndCraftTest()
    {
        Platform.runLater(() ->
        {
            game.setMap(new char[][]{{'P', 'F', 'I'}});
            assertTrue(game.player.getInventory().getInventory().isEmpty());
            game.checkGameState();
            assertEquals(1, game.player.getInventory().getInventory().size());
            game.player.move(Direction.MOVEMENT_RIGHT);
            game.checkGameState();
            assertEquals(2, game.player.getInventory().getInventory().size());

            game.player.getInventory().setItemCrafting(0);
            game.player.getInventory().setItemCrafting(1);

            game.player.getInventory().craft();
            assertEquals(1, game.player.getInventory().getInventory().size());
            assertEquals(ITEM_STEEL, game.player.getInventory().getInventory().get(ITEM_STEEL).getType());
        });
    }

    @Test
    @Order(5)
    public void saveAndLoadGame()
    {
        game.run();
        assertEquals(100, player.getHealth());
        game.player.setHealth(50);
        game.setMap(new char[][]{{'P', 'F', 'I'}});
        game.saveGame(new File("./testfile"));
        game.loadSave("./testfile");
        char[][] test = new char[][]{{'P', 'F', 'I'}};
        assert(test[0][0] == game.map[0][0] && test[0][1] == game.map[0][1] && test[0][2] == game.map[0][2]);
        assertEquals(50, game.player.getHealth());
    }
}
