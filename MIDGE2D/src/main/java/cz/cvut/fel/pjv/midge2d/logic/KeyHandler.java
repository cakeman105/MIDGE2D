package cz.cvut.fel.pjv.midge2d.logic;

import cz.cvut.fel.pjv.midge2d.MainController;
import cz.cvut.fel.pjv.midge2d.entity.character.Enemy;
import cz.cvut.fel.pjv.midge2d.entity.character.Player;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;

/**
 * Event handler for key presses
 *
 * @author Joshua David Crofts
 */
public class KeyHandler implements EventHandler<KeyEvent>
{
    private final Player player;
    private final char[][] map;
    /**
     * movement sound for player
     */
    final AudioClip clip;
    final AudioClip punch;
    Enemy enemy;

    public KeyHandler(char[][] map, Player player)
    {
        this.map = map;
        this.player = player;
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
        if (event.getEventType() == KeyEvent.KEY_RELEASED )
        {
            KeyCode code = event.getCode();

            if (code == KeyCode.E)
                punch.play();

            if (code == KeyCode.D || code == KeyCode.A || code == KeyCode.W || code == KeyCode.S)
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
                    if (enemy != null)
                        player.hit(enemy);
                }
                case KeyCode.DIGIT1 -> player.setCurrentWeapon(ItemType.ITEM_KNIFE); //invariant
                case KeyCode.DIGIT2 -> player.setCurrentWeapon(ItemType.ITEM_GUN);

            }

            map[player.getPrevPositionX()][player.getPrevPositionY()] = ' ';
            map[player.getPositionX()][player.getPositionY()] = 'P';
        }
    }
}
