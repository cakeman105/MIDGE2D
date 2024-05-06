package cz.cvut.fel.pjv.midge2d.logic;

import cz.cvut.fel.pjv.midge2d.Game;
import cz.cvut.fel.pjv.midge2d.MainController;
import cz.cvut.fel.pjv.midge2d.entity.character.Enemy;
import cz.cvut.fel.pjv.midge2d.entity.character.Player;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Event handler for key presses
 *
 * @author Joshua David Crofts
 */
public class KeyHandler implements EventHandler<KeyEvent>
{
    private Player player;
    private char[][] map;
    /**
     * movement sound for player
     */
    private final AudioClip clip;
    private final AudioClip punch;
    private Enemy enemy;
    private final Label enemyHealth;

    protected static Logger logger = Logger.getLogger(KeyHandler.class.getName());

    public KeyHandler( Player player, Label enemyHealth)
    {
        logger.setLevel(Level.SEVERE);
        this.player = player;
        this.enemyHealth = enemyHealth;
        this.clip = new AudioClip(String.valueOf(MainController.class.getResource("move.wav")));
        this.punch = new AudioClip(String.valueOf(MainController.class.getResource("punch.wav")));
    }

    public void setEnemy(Enemy enemy)
    {
        this.enemy = enemy;
    }

    @Override
    public void handle(KeyEvent event)
    {
        if (event.getEventType() == KeyEvent.KEY_RELEASED)
        {
            KeyCode code = event.getCode();
            boolean flag = code == KeyCode.D || code == KeyCode.A || code == KeyCode.W || code == KeyCode.S;

            logger.info(String.format("Detected key %s", code.getChar()));
            if (code == KeyCode.E)
                punch.play();

            if (flag)
            {
                clip.play();
                map[player.getPositionX()][player.getPositionY()] = ' ';
            }

            switch (code)
            {
                case D -> player.move(Direction.MOVEMENT_RIGHT);
                case A -> player.move(Direction.MOVEMENT_LEFT);
                case W -> player.move(Direction.MOVEMENT_UP);
                case S -> player.move(Direction.MOVEMENT_DOWN);
                case E ->
                {
                    if (enemy != null && (player.getCurrentWeapon() == ItemType.ITEM_GUN || player.getCurrentWeapon() == ItemType.ITEM_KNIFE || player.getCurrentWeapon() == ItemType.ITEM_FISTS))
                        player.hit(enemy);

                }
                case KeyCode.DIGIT1 ->
                {
                    if (Game.state == GameState.GAME_RUNNING)
                        player.setCurrentWeapon(0);
                    else
                        player.getInventory().setItemCrafting(0);
                }
                case KeyCode.DIGIT2 ->
                {
                    if (Game.state == GameState.GAME_RUNNING)
                        player.setCurrentWeapon(1);
                    else
                        player.getInventory().setItemCrafting(1);
                }
                case KeyCode.DIGIT3 ->
                {
                    if (Game.state == GameState.GAME_RUNNING)
                        player.setCurrentWeapon(2);
                    else
                        player.getInventory().setItemCrafting(2);
                }
                case KeyCode.DIGIT4 ->
                {
                    if (Game.state == GameState.GAME_RUNNING)
                        player.setCurrentWeapon(3);
                    else
                        player.getInventory().setItemCrafting(3);
                }
                case C ->
                {
                    if (Game.state == GameState.GAME_CRAFTING)
                    {
                        Game.state = GameState.GAME_RUNNING;
                        this.enemyHealth.setVisible(false);
                        this.player.getInventory().clearCrafting();
                    }
                    else
                        Game.state = GameState.GAME_CRAFTING;

                }

                case ENTER ->
                {
                    if (Game.state == GameState.GAME_CRAFTING)
                    {
                        player.getInventory().craft();
                        Game.state = GameState.GAME_RUNNING;
                        this.enemyHealth.setVisible(false);
                    }
                }
            }

            if (flag)
            {
                map[player.getPrevPositionX()][player.getPrevPositionY()] = ' ';
                map[player.getPositionX()][player.getPositionY()] = 'P';
            }
        }
    }

    public void setMap(char[][] map)
    {
        this.map = map;
    }
}