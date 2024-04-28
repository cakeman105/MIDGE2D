package cz.cvut.fel.pjv.midge2d.entity.character;

import cz.cvut.fel.pjv.midge2d.entity.item.Inventory;
import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;

/**
 * Player class
 * @author Joshua David Crofts
 */
public class Player extends Character
{
    private final Inventory inventory;
    private Item currentWeapon;

    public Player()
    {
        super(100); //temporary
        this.inventory = new Inventory();
        this.currentWeapon = new Item(ItemType.ITEM_GUN, 5, 10);
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

    public Inventory getInventory()
    {
        return this.inventory;
    }

    public void addItem(Item item)
    {
        this.inventory.addItem(item);
    }
}
