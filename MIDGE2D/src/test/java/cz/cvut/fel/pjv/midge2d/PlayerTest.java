package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.entity.character.Enemy;
import cz.cvut.fel.pjv.midge2d.entity.character.Player;
import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;
import cz.cvut.fel.pjv.midge2d.logic.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static cz.cvut.fel.pjv.midge2d.entity.item.ItemType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest
{
    private Player player;
    private Enemy enemy;
    private Item weapon;
    @BeforeEach
    public void init()
    {
        weapon = mock(Item.class);
        when(weapon.getType()).thenReturn(ItemType.ITEM_KNIFE);
        when(weapon.getValue()).thenReturn(20);
        this.player = new Player();
        enemy = new Enemy(weapon, 100, Direction.MOVEMENT_RIGHT);
    }

    @Test
    @Order(1)
    public void initialWeaponTest()
    {
        assertEquals(ITEM_FISTS, this.player.getCurrentWeapon());
    }

    @Test
    @Order(2)
    public void hitTest()
    {
        int initialHealth = enemy.getHealth();
        player.hit(enemy);
        assertTrue(enemy.getHealth() < initialHealth);
    }

    @Test
    @Order(3)
    public void setCurrentWeaponTest()
    {
        player.getInventory().addItem(weapon);
        player.setCurrentWeapon(0);
        assertEquals(ITEM_KNIFE, player.getCurrentWeapon());
    }

    @Test
    @Order(4)
    public void invalidIndexTest()
    {
        player.setCurrentWeapon(99);
        assertEquals(ITEM_FISTS, player.getCurrentWeapon());
    }

    @Test
    @Order(5)
    public void inventoryInitializedTest()
    {
        assertNotNull(player.getInventory());
    }

}
