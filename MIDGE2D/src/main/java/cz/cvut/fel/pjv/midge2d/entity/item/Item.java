package cz.cvut.fel.pjv.midge2d.entity.item;

import java.io.Serializable;

/**
 * Class for handling item logic
 * @author Joshua David Crofts
 */
public class Item implements Serializable
{
    private final ItemType Type;
    private final int Value;

    public Item(ItemType itemType, int value)
    {
        this.Type = itemType;
        this.Value = value;
    }

    public ItemType getType()
    {
        return this.Type;
    }

    public int getValue()
    {
        return this.Value;
    }
}
