package cz.cvut.fel.pjv.midge2d.entity.character;

import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;
import cz.cvut.fel.pjv.midge2d.logic.Direction;

public class Character
{
    private int health;
    private Item weapon;
    private int positionX;
    private int positionY;
    private int prevPositionX;
    private int prevPositionY;

    public Character(int health, Item weapon)
    {
        this.health = health;
        this.weapon = weapon;
    }

    public Character(int health)
    {
        this.health = health;
    }

    public int getHealth()
    {
        return this.health;
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
        switch(direction)
        {
            case Direction.MOVEMENT_UP -> this.positionX -= 1;
            case Direction.MOVEMENT_DOWN -> this.positionX += 1;
            case Direction.MOVEMENT_LEFT -> this.positionY -= 1;
            case Direction.MOVEMENT_RIGHT -> this.positionY += 1;
        }
        this.prevPositionX = positionX;
        this.prevPositionY = positionY;
    }
}
