package cz.cvut.fel.pjv.midge2d.logic;

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
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) //run check for end of map
            return false;

        return map[x][y] != '#' && map[x][y] != 'D';
    }
}
