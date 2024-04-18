package cz.cvut.fel.pjv.midge2d.logic;

import cz.cvut.fel.pjv.midge2d.MainController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class responsible for drawing contents of map file to canvas
 * @author Joshua David Crofts
 */
public class MapDraw
{
    protected static final Logger logger = Logger.getLogger(MapDraw.class.getName());
    private static final int CELL_SIZE = 42;
    private static final int ROW_COUNT = 19; //very strange numbers
    private static final int COL_COUNT = 26;
    private final Canvas cvs;
    private final String directory;
    private final List<String> mapList;
    private char[][] map;

    public MapDraw(String directory, Canvas cvs)
    {
        this.directory = directory;
        this.cvs = cvs;
        this.map = new char[ROW_COUNT][COL_COUNT];
        this.mapList = new ArrayList<>();
    }

    /**
     * Loads list of maps from manifest file and runs them
     * @throws FileNotFoundException
     */
    public void run() throws FileNotFoundException
    {
        File file = new File(this.directory);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
            this.mapList.add(file.getParent() + scanner.nextLine());

        this.loadFile(mapList.getFirst());
        this.draw();
    }

    /**
     * Load map contents into 2D char array
     * @param fileName file to load from
     * @throws FileNotFoundException
     */
    private void loadFile(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file))
        {
            int i = 0;
            while (scanner.hasNextLine())
            {
                char[] line = scanner.nextLine().toCharArray();
                System.arraycopy(line, 0, map[i], 0, line.length);
                i++;
            }
        }
    }

    /**
     * Displays the map by drawing it on the canvas
     */
    private void draw()
    {
        logger.info("Loading map.");
        GraphicsContext context = cvs.getGraphicsContext2D();
        context.clearRect(0, 0, cvs.getWidth(), cvs.getHeight());
        context.setFill(new ImagePattern(new Image(String.valueOf(MainController.class.getResource("brick.png")))));
        int x = 0, y = 0;
        for (int i = 0; i < this.map.length; i++)
        {
            for (int j = 0; j < this.map[i].length; j++)
            {
                if (this.map[i][j] == '#')
                    context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                logger.info(String.format("Loaded cell row %d col %d", i, j));
                x += 40;
            }

            x = 0;
            y += 40;
        }
        logger.info("Map loaded.");
    }
}
