package cz.cvut.fel.pjv.midge2d.entity.item;

import java.util.ArrayList;
import java.util.List;

public class Inventory
{
    private ArrayList<Item> inventory;
    public Inventory()
    {
        this.inventory = new ArrayList<>();
    }

    public void removeItem(int index)
    {
        inventory.remove(index);
    }

    public void addItem(Item item)
    {
        if (!inventory.contains(item))
        {
            inventory.add(item);
        }
    }

    public void craft()
    {

    }

    public ArrayList<Item> getInventory()
    {
        return this.inventory;
    }
}
