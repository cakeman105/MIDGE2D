package cz.cvut.fel.pjv.midge2d.entity.character;

import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;

public class Character
{
    private int health;
    private Item weapon;

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
}
