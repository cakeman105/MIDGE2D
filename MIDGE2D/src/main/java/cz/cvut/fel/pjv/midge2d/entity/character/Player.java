package cz.cvut.fel.pjv.midge2d.entity.character;

import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;

public abstract class Player extends Character
{
    private int health;

    public Player()
    {
        super(100); //temporary
    }
}
