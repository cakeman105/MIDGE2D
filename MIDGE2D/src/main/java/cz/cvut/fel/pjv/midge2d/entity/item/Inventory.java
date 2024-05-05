package cz.cvut.fel.pjv.midge2d.entity.item;

import cz.cvut.fel.pjv.midge2d.Game;
import cz.cvut.fel.pjv.midge2d.logic.GameState;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Class for handling inventory functions
 * @author Joshua David Crofts
 */
public class Inventory
{
    private Item itemCrafting1;
    private Item itemCrafting2;


    /**
     * Map contains recipes for crafting<br>
     * Example: this.recipes.put("ITEM_FLINT;ITEM_IRON", ItemType.ITEM_STEEL);
     */
    private final HashMap<String, ItemType> recipes;
    private final HashMap<ItemType, Item> inventory;
    public Inventory()
    {
        this.inventory = new HashMap<>();
        this.recipes = new HashMap<>();
        this.recipes.put("ITEM_FLINT;ITEM_IRON", ItemType.ITEM_STEEL);
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
     * takes itemCrafting fields and checks whether recipe exists in hashmap<br>
     * if so, adds new instance of item and deletes existing items<br>
     * if not, no operation
     */
    public void craft()
    {
        if (itemCrafting1 == null || itemCrafting2 == null)
            return;

        String[] presort = {itemCrafting1.getType().toString(), itemCrafting2.getType().toString()};
        Arrays.sort(presort);
        String ingredients = String.format("%s;%s", presort[0], presort[1]);
        ItemType crafted = this.recipes.get(ingredients);

        if (crafted != null)
        {
            addItem(new Item(crafted, 10));
            removeItem(itemCrafting1.getType());
            removeItem(itemCrafting2.getType());
        }

        clearCrafting();
    }

    public HashMap<ItemType, Item> getInventory()
    {
        return this.inventory;
    }

    public void setItemCrafting(int index)
    {
        if (this.inventory.size() > 1)
        {
            try
            {
                ItemType key = (ItemType) this.inventory.keySet().toArray()[index];
                if (this.itemCrafting1 == null)
                    this.itemCrafting1 = this.inventory.get(key);
                else
                    this.itemCrafting2 = this.inventory.get(key);
            }
            catch (NullPointerException ignored){}
        }
    }

    /**
     * outputs crafting combo to string
     * @return combination to be crafted
     */
    public String getCraftingToString()
    {
        return Game.state == GameState.GAME_CRAFTING ? String.format("%s %s %s", itemCrafting1 != null ? itemCrafting1.getType().toString() : "", itemCrafting1 != null ? "+" : "", itemCrafting2 != null ? itemCrafting2.getType().toString() : "")
                : "";
    }

    public void clearCrafting()
    {
        this.itemCrafting1 = null;
        this.itemCrafting2 = null;
    }
}
