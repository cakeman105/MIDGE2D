package cz.cvut.fel.pjv.midge2d.entity.character;

import cz.cvut.fel.pjv.midge2d.entity.item.Item;

public abstract class Ally extends Character
{
    public Ally(int health, Item weapon, int x, int y)
    {
        super(health, weapon);
    }
}
