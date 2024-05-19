package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.entity.item.Inventory;
import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;
import cz.cvut.fel.pjv.midge2d.logic.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for inventory
 * @author Joshua David Crofts
 */
public class InventoryTest
{
    private Inventory inventory;

    @BeforeEach
    public void init()
    {
        this.inventory = new Inventory();
    }

    @Test
    @Order(1)
    public void addItemTest()
    {
        this.inventory.addItem(new Item(ItemType.ITEM_GUN, 20));
        this.inventory.addItem(new Item(ItemType.ITEM_KNIFE, 5));
        assertEquals(2, this.inventory.getInventory().size());
        this.inventory.addItem(new Item(ItemType.ITEM_KNIFE, 20));
        assertEquals(2, this.inventory.getInventory().size()); //cannot add same item type into inventory
    }

    @Test
    @Order(2)
    public void removeItemTest()
    {
        this.inventory.addItem(new Item(ItemType.ITEM_GUN, 20));
        this.inventory.addItem(new Item(ItemType.ITEM_KNIFE, 5));
        this.inventory.removeItem(ItemType.ITEM_GUN);
        assertEquals(1, this.inventory.getInventory().size());
        this.inventory.removeItem(ItemType.ITEM_GUN);
        assertEquals(1, this.inventory.getInventory().size()); //cannot remove nonexistent item
    }

    @Test
    @Order(3)
    public void craftItemTest()
    {
        this.inventory.addItem(new Item(ItemType.ITEM_FLINT, 10));
        this.inventory.addItem(new Item(ItemType.ITEM_IRON, 20));
        this.inventory.setItemCrafting(0);
        this.inventory.setItemCrafting(1);
        this.inventory.craft();
        assertEquals(1, this.inventory.getInventory().size());
        assertEquals(ItemType.ITEM_STEEL, this.inventory.getInventory().get(ItemType.ITEM_STEEL).getType());
        this.inventory.addItem(new Item(ItemType.ITEM_KNIFE, 10));
        this.inventory.setItemCrafting(0);
        this.inventory.setItemCrafting(1);
        assertEquals(2, this.inventory.getInventory().size());
    }

    @Test
    @Order(4)
    public void toStringTest()
    {
        this.inventory.addItem(new Item(ItemType.ITEM_FLINT, 10));
        this.inventory.addItem(new Item(ItemType.ITEM_IRON, 20));
        this.inventory.setItemCrafting(0);
        this.inventory.setItemCrafting(1);
        Game.state = GameState.GAME_CRAFTING;
        assert(!Objects.equals(this.inventory.toString(), "")); //buggy test
    }
}
