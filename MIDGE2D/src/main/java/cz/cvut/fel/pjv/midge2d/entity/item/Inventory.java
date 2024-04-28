package cz.cvut.fel.pjv.midge2d.entity.item;

import java.util.ArrayList;
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

    public void addItem(Item item)
    {
        inventory.put(item.getType(), item);
    }

    public void craft()
    {

    }

    public HashMap<ItemType, Item> getInventory()
    {
        return this.inventory;
    }
}
