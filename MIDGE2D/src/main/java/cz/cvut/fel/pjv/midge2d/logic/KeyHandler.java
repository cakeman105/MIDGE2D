package cz.cvut.fel.pjv.midge2d.logic;

import cz.cvut.fel.pjv.midge2d.entity.character.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent>
{
    private Player player;
    private char[][] map;
    public KeyHandler(char[][] map, Player player)
    {
        this.map = map;
        this.player = player;
    }

    @Override
    public void handle(KeyEvent event)
    {
        if (event.getEventType() == KeyEvent.KEY_PRESSED)
        {
            map[player.getPositionX()][player.getPositionY()] = ' ';
            switch(event.getCode())
            {
                case D -> player.move(Direction.MOVEMENT_RIGHT);
                case A -> player.move(Direction.MOVEMENT_LEFT);
                case W -> player.move(Direction.MOVEMENT_UP);
                case S -> player.move(Direction.MOVEMENT_DOWN);
            }

            map[player.getPrevPositionX()][player.getPrevPositionY()] = ' ';
            map[player.getPositionX()][player.getPositionY()] = 'P';
        }
    }
}
