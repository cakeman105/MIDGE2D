package cz.cvut.fel.pjv.midge2d.entity.character;

import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.logic.Direction;

import java.util.ArrayList;

/**
 * Player class
 * @author Joshua David Crofts
 */
public class Player extends Character
{
    private final ArrayList<Item> inventory;
    private Item currentWeapon;

    public Player()
    {
        super(100); //temporary
        this.inventory = new ArrayList<>();
    }

    /**
     * hits character
     * @param character character to take damage
     */
    @Override
    public void hit(Character character)
    {
        character.receiveDamage(currentWeapon);
    }

    /**
     * changes current weapon based on player entry
     * @param index order in inventory
     */
    public void changeWeapon(int index)
    {
        this.currentWeapon = this.inventory.get(index);
    }

    /**
     * adds picked up item to inventory
     * @param item picked up
     */
    public void pickUpItem(Item item)
    {
        this.inventory.add(item);
    }
}
