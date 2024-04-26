package cz.cvut.fel.pjv.midge2d.logic;

import cz.cvut.fel.pjv.midge2d.MainController;
import cz.cvut.fel.pjv.midge2d.entity.character.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;

public class KeyHandler implements EventHandler<KeyEvent>
{
    private Player player;
    private char[][] map;
    AudioClip clip;
    public KeyHandler(char[][] map, Player player)
    {
        this.map = map;
        this.player = player;
        this.clip = new AudioClip(String.valueOf(MainController.class.getResource("move.wav")));
    }

    @Override
    public void handle(KeyEvent event)
    {
        if (event.getEventType() == KeyEvent.KEY_PRESSED)
        {
            KeyCode code = event.getCode();
            map[player.getPositionX()][player.getPositionY()] = ' ';

            if (code == KeyCode.D || code == KeyCode.A || code == KeyCode.W || code == KeyCode.S)
                clip.play();
            
            switch(code)
            {
                case D -> player.move(Direction.MOVEMENT_RIGHT);
                case A -> player.move(Direction.MOVEMENT_LEFT);
                case W -> player.move(Direction.MOVEMENT_UP);
                case S -> player.move(Direction.MOVEMENT_DOWN);
                //case E -> player.hit();
            }

            map[player.getPrevPositionX()][player.getPrevPositionY()] = ' ';
            map[player.getPositionX()][player.getPositionY()] = 'P';
        }
    }
}
