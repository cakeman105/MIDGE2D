package cz.cvut.fel.pjv.midge2d.logic;

import javafx.scene.canvas.Canvas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class responsible for drawing contents of map file to canvas
 */
public class MapDraw
{
    protected static final Logger logger = Logger.getLogger(MapDraw.class.getName());
    private static final int CELL_SIZE = 10;
    private Canvas cvs;
    private final String directory;
    private final List<String> mapList;
    private char[][] map;

    public MapDraw(String directory, Canvas cvs)
    {
        this.directory = directory;
        this.cvs = cvs;
        this.map = new char[50][50];
        this.mapList = new ArrayList<>();
    }

    public void run() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File(this.directory));
        while(scanner.hasNextLine())
            this.mapList.add(scanner.nextLine());

        this.loadFile(mapList.getFirst());
        this.draw();
    }

    private void loadFile(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        int i = 0;
        while(scanner.hasNextLine())
        {
            char[] line = scanner.nextLine().toCharArray();
            System.arraycopy(line, 0, map[i], 0, line.length);
            i++;
        }
    }

    private void draw()
    {
        logger.info("Loading map.");

        logger.info("Map loaded.");
    }
}
