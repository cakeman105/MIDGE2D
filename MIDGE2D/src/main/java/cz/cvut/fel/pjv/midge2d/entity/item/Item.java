package cz.cvut.fel.pjv.midge2d.entity.item;

/**
 * Class for handling item logic
 * @author Joshua David Crofts
 */
public class Item
{
    private final ItemType Type;
    private int AmountOfUses;
    private final int Value;

    public Item(ItemType itemType, int amountOfUses, int value)
    {
        this.AmountOfUses = amountOfUses;
        this.Type = itemType;
        this.Value = value;
    }

    public void use()
    {
        this.AmountOfUses -= 1;
    }

    public int getAmountOfUses()
    {
        return this.AmountOfUses;
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
