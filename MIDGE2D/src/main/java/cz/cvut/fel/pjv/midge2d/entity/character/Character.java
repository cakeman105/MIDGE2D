package cz.cvut.fel.pjv.midge2d.entity.character;

import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;
import cz.cvut.fel.pjv.midge2d.logic.CollisionDetection;
import cz.cvut.fel.pjv.midge2d.logic.Direction;

public class Character
{
    private int health;
    private Item weapon;
    private int positionX;
    private int positionY;
    private int prevPositionX;
    private int prevPositionY;
    private boolean isMoving;
    protected CollisionDetection detection;

    public Character(int health, Item weapon)
    {
        this.health = health;
        this.weapon = weapon;
        this.isMoving = true;
    }

    public Character(int health)
    {
        this.health = health;
        this.isMoving = true;
    }

    public int getHealth()
    {
        return this.health;
    }

    public void hit(Character character)
    {
        character.receiveDamage(this.weapon);
    }

    public void receiveDamage(Item weapon)
    {
        if (weapon.getType() == ItemType.ITEM_GUN || weapon.getType() == ItemType.ITEM_KNIFE)
            this.health -= weapon.getValue();
    }

    public void setPosition(int x, int y)
    {
        this.positionX = x;
        this.positionY = y;
    }

    public int getPositionX()
    {
        return this.positionX;
    }

    public int getPositionY()
    {
        return this.positionY;
    }

    public int getPrevPositionX()
    {
        return this.prevPositionX;
    }

    public int getPrevPositionY()
    {
        return this.prevPositionY;
    }

    public void move(Direction direction)
    {
        if (this.getMoving())
        {
            switch (direction)
            {
                case Direction.MOVEMENT_UP ->
                {
                    if (this.detection.checkCoords(this.positionX - 1, this.positionY))
                        this.positionX -= 1;
                }
                case Direction.MOVEMENT_DOWN ->
                {
                    if (this.detection.checkCoords(this.positionX + 1, this.positionY))
                        this.positionX += 1;
                }
                case Direction.MOVEMENT_LEFT ->
                {
                    if (this.detection.checkCoords(this.positionX, this.positionY - 1))
                        this.positionY -= 1;
                }
                case Direction.MOVEMENT_RIGHT ->
                {
                    if (this.detection.checkCoords(this.positionX, this.positionY + 1))
                        this.positionY += 1;
                }
            }

            this.prevPositionX = positionX;
            this.prevPositionY = positionY;
        }
    }

    public void attachCollision(CollisionDetection detection)
    {
        this.detection = detection;
    }
    public void setMoving(boolean value)
    {
        this.isMoving = value;
    }

    public boolean getMoving()
    {
        return this.isMoving;
    }
}
