package cz.cvut.fel.pjv.midge2d.logic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public class MapDraw
{
    protected static final Logger logger = Logger.getLogger(MapDraw.class.getName());
    private static final int CELL_SIZE = 10;
    private Canvas cvs;
    private final String directory;
    private char[][] map;

    public MapDraw(String directory, Canvas cvs)
    {
        this.directory = directory;
        this.cvs = cvs;
        this.map = new char[50][50];
    }

    public void loadFile(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        int i = 0;
        while(scanner.hasNextLine())
        {
            char[] line = scanner.nextLine().toCharArray();
            for (int j = 0; j < line.length; j++)
            {
                map[i][j] = line[j];
            }
            i++;
        }
    }

    public void draw()
    {
        logger.info("Loading map.");
        GraphicsContext context = cvs.getGraphicsContext2D();
        for (int row = 0; row < map.length; row++) {
            logger.info(String.format("Loading row no. %d", row));
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == '#') {
                    Rectangle wall = new Rectangle(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    wall.setFill(Color.BLACK);
                    context.fill();
                }
            }
        }
        logger.info("Map loaded.");
    }
}
