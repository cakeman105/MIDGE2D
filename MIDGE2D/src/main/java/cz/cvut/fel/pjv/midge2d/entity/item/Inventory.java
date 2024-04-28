package cz.cvut.fel.pjv.midge2d.entity.item;

import java.util.HashMap;

public class Inventory
{
    private final HashMap<ItemType, Item> inventory;
    public Inventory()
    {
        this.inventory = new HashMap<>();
    }

    public void removeItem(ItemType type)
    {
        inventory.remove(type);
    }

    /**
     * Inventory can hold max 4 items
     * @param item item to be added
     */
    public void addItem(Item item)
    {
        if (inventory.size() < 4)
            inventory.put(item.getType(), item);
    }

    /**
     * TODO
     */
    public void craft()
    {

    }

    public HashMap<ItemType, Item> getInventory()
    {
        return this.inventory;
    }
}
