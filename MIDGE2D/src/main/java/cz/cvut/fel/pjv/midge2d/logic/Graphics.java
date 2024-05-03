package cz.cvut.fel.pjv.midge2d.logic;

import cz.cvut.fel.pjv.midge2d.MainController;
import cz.cvut.fel.pjv.midge2d.entity.character.Player;
import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for graphics handling
 * @author Joshua David Crofts
 */
public class Graphics
{
    protected static final Logger logger = Logger.getLogger(Graphics.class.getName());
    private static final int CELL_SIZE = 42;
    private final Canvas cvs;
    private final ImagePattern brick;
    private final ImagePattern enemy;
    private final ImagePattern player;
    private final ImagePattern hud;
    private final ImagePattern knife;
    private final ImagePattern gun;
    private final ImagePattern hudGun;
    private final ImagePattern hudKnife;
    private final ImagePattern door;
    private final ImagePattern hudFlint;
    private final ImagePattern hudSteel;
    private final ImagePattern hudIron;
    private final ImagePattern flint;
    private final ImagePattern iron;

    public Graphics(Canvas cvs)
    {
        this.cvs = cvs;
        logger.setLevel(Level.SEVERE);
        this.brick = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("wall.png"))));
        this.enemy = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("enemy.png"))));
        this.player = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("character.png"))));
        this.hud = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("hud.png"))));
        this.knife = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("knife.png"))));
        this.gun = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("gun.png"))));
        this.hudGun = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("hudGun.png"))));
        this.hudKnife = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("hudKnife.png"))));
        this.door = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("wood.png"))));
        this.hudFlint = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("hudFlint.png"))));
        this.hudSteel = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("hudSteel.png"))));
        this.hudIron = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("hudIron.png"))));
        this.flint = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("flint.png"))));
        this.iron = new ImagePattern(new Image(String.valueOf(MainController.class.getResource("ingot.png"))));
    }

    /**
     * Displays the map by drawing it on the canvas
     * reads char value and draws corresponding texture
     */
    public void draw(char[][] map)
    {
        logger.info("Loading map.");
        GraphicsContext context = cvs.getGraphicsContext2D();
        context.clearRect(0, 0, cvs.getWidth(), cvs.getHeight());
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
                    case 'E':
                        context.setFill(enemy);
                        context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        context.setFill(brick);
                        break;
                    case 'K':
                        context.setFill(knife);
                        context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        context.setFill(brick);
                        break;
                    case 'G':
                        context.setFill(gun);
                        context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        context.setFill(brick);
                        break;
                    case 'D':
                        context.setFill(door);
                        context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        context.setFill(brick);
                        break;
                    case 'F':
                        context.setFill(flint);
                        context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        context.setFill(brick);
                        break;
                    case 'I':
                        context.setFill(iron);
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

    /**
     * draws hud and inventory data
     * @param player data to be displayed
     */
    public void drawHud(Player player)
    {
        GraphicsContext context = cvs.getGraphicsContext2D();
        context.setFill(hud);
        int x = 0, y = 720;
        for (int j = 0; j < 7; j++)
        {
            context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            x += 40;
        }

        for (Item e : player.getInventory().getInventory().values())
        {
            switch (e.getType())
            {
                case ITEM_GUN -> context.setFill(hudGun);
                case ITEM_KNIFE -> context.setFill(hudKnife);
                case ITEM_FLINT -> context.setFill(hudFlint);
                case ITEM_STEEL -> context.setFill(hudSteel);
                case ITEM_IRON -> context.setFill(hudIron);
            }
            context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            x += 40;
        }

        context.setFill(hud);
        for (int j = 7 + player.getInventory().getInventory().size(); j < 26; j++)
        {
            context.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            x += 40;
        }
    }

    public void clearCanvas()
    {
        this.cvs.getGraphicsContext2D().clearRect(0, 0, this.cvs.getWidth(), this.cvs.getHeight());
    }
}
