package cz.cvut.fel.pjv.midge2d.entity.character;

import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.logic.Direction;

import java.util.logging.Logger;

public class Enemy extends Character
{
    private static final Logger logger = Logger.getLogger(Enemy.class.getName());
    private Direction direction;
    public Enemy(Item weapon, int health, Direction direction)
    {
        super(health, weapon);
        this.direction = direction;
    }

    public void moveEnemy()
    {
        if (!detection.checkCoords(this.getPrevPositionX(), this.getPositionY() + 1) && direction == Direction.MOVEMENT_RIGHT)
            this.direction = Direction.MOVEMENT_LEFT;
        else if (!detection.checkCoords(this.getPrevPositionX(), this.getPositionY() + - 1) && direction == Direction.MOVEMENT_LEFT)
            this.direction = Direction.MOVEMENT_RIGHT;

        this.move(direction);
    }
}
