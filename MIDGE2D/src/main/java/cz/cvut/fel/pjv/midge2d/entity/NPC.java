package cz.cvut.fel.pjv.midge2d.entity;

/**
 * Class for handling NPC logic
 * @author Joshua David Crofts
 */
public class NPC
{
    private int health;
    private int speed;
    public NPC(int health, int speed)
    {
        this.health = health;
        this.speed = speed;
    }

    public int getHealth()
    {
        return this.health;
    }

    public int getSpeed()
    {
        return this.speed;
    }

    public void talk()
    {

    }
}
