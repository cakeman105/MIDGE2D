package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.logic.CollisionDetection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for Collision detection
 * @author Joshua David Crofts
 */
public class CollisionDetectionTest
{
    char[][] map;
    @BeforeEach
    public void init()
    {
        this.map = new char[][]{{'#', '#', '#'}, {'#', ' ', '#'}, {'#', 'D', '#'}};
    }

    @Test
    @Order(1)
    public void wallTest()
    {
        CollisionDetection detection = new CollisionDetection(this.map);
        assertFalse(detection.checkCoords(0, 0));
        assertTrue(detection.checkCoords(1, 1));
    }

    @Test
    @Order(2)
    public void outOfBoundsTest()
    {
        CollisionDetection detection = new CollisionDetection(this.map);
        assertFalse(detection.checkCoords(-1, -1));
        assertFalse(detection.checkCoords(map.length + 1, map.length + 1));
    }

    @Test
    @Order(3)
    public void doorTest()
    {
        CollisionDetection detection = new CollisionDetection(this.map);
        assertFalse(detection.checkCoords(2, 1));
    }
}
