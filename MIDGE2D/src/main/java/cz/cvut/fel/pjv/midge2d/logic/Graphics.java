package cz.cvut.fel.pjv.midge2d.logic;

import cz.cvut.fel.pjv.midge2d.MainController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for graphics handling
 *
 * @author Joshua David Crofts
 */
public class Graphics
{
    protected static final Logger logger = Logger.getLogger(Graphics.class.getName());
    private static final int CELL_SIZE = 42;
    private final Canvas cvs;

    public Graphics(Canvas cvs)
    {
        this.cvs = cvs;
        logger.setLevel(Level.SEVERE);
    }

    /**
     * Displays the map by drawing it on the canvas
     */
    public void draw(char[][] map)
    {
        logger.info("Loading map.");
        GraphicsContext context = cvs.getGraphicsContext2D();
        context.clearRect(0, 0, cvs.getWidth(), cvs.getHeight());
        ImagePattern brick = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("brick.png"))));
        ImagePattern player = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("character.png"))));
        context.setFill(brick);
        int x = 0, y = 0;
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                switch (map[i][j])
                {
                    case '#':
                        context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        break;
                    case 'P':
                        context.setFill(player);
                        context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        context.setFill(brick);
                        break;
                }

                logger.info(String.format("Loaded cell row %d col %d", i, j));
                x += 40;
            }

            x = 0;
            y += 40;
        }
        logger.info("Map loaded.");
    }

    public void clearCanvas()
    {
        this.cvs.getGraphicsContext2D().clearRect(0, 0, this.cvs.getWidth(), this.cvs.getHeight());
    }
}
