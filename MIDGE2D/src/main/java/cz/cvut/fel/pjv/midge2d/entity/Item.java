package cz.cvut.fel.pjv.midge2d.entity;

/**
 * Class responsible for handling items located in map
 * @author Joshua David Crofts
 */
public class Item
{
    /**
     * Type of item
     */
    private final ItemType type;
    /**
     * Amount of times item can be used before breaking
     */
    private int attempts;

    public Item(ItemType type, int attempts)
    {
        this.attempts = attempts;
        this.type = type;
    }

    public void use()
    {
        switch(this.type)
        {
            case KNIFE:
                break;
            case HEART:
                break;
            case KEY:
                break;
        }

        attempts--;
    }
}
