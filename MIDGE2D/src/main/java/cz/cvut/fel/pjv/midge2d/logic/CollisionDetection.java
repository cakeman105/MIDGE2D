package cz.cvut.fel.pjv.midge2d.logic;

import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;

/**
 * class for handling collision detection
 * @author Joshua David Crofts
 */
public class CollisionDetection
{
    private final char[][] map;
    public CollisionDetection(char [][] map)
    {
        this.map = map;
    }

    public boolean checkCoords(int x, int y)
    {
        return map[x][y] != '#' && map[x][y] != 'D';
    }
}
