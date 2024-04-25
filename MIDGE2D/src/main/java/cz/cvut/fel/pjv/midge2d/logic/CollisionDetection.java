package cz.cvut.fel.pjv.midge2d.logic;

/**
 * class for handling collision detection
 * @author Joshua David Crofts
 */
public class CollisionDetection
{
    private char[][] map;
    public CollisionDetection(char [][] map)
    {
        this.map = map;
    }

    public boolean checkCoords(int x, int y)
    {
        return map[x][y] != '#';
    }
}
