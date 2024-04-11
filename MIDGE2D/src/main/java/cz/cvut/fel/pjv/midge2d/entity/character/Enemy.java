package cz.cvut.fel.pjv.midge2d.entity.character;

import cz.cvut.fel.pjv.midge2d.entity.item.Item;

public abstract class Enemy extends Character
{
    public Enemy(Item weapon, int health)
    {
        super(health, weapon);
    }
}
