package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.logic.CollisionDetection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionDetectionTest
{
    char[][] map;
    @BeforeEach
    public void init()
    {
        this.map = new char[][]{{'#', '#'}};
    }

    @ParameterizedTest
    @Order(1)
    @CsvSource(value = {"1, 2", "3, 4"})
    public void CoordinatesTest(String stringX, String stringY)
    {
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        CollisionDetection detection = new CollisionDetection(this.map);
        assertTrue(detection.checkCoords(x, y));
    }
}
