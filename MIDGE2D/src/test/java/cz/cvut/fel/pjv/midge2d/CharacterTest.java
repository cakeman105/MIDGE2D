package cz.cvut.fel.pjv.midge2d;

import cz.cvut.fel.pjv.midge2d.entity.character.Character;
import cz.cvut.fel.pjv.midge2d.entity.item.Item;
import cz.cvut.fel.pjv.midge2d.entity.item.ItemType;
import cz.cvut.fel.pjv.midge2d.logic.CollisionDetection;
import cz.cvut.fel.pjv.midge2d.logic.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharacterTest
{
    private Character character;

    @BeforeEach
    public void init()
    {
        this.character = new Character(100, new Item(ItemType.ITEM_GUN, 20));
    }

    @ParameterizedTest
    @Order(1)
    @ValueSource(ints = {0, 10, 20})
    public void receiveDamageTest(int damage)
    {
        int healthCompare = this.character.getHealth() - damage;
        this.character.receiveDamage(new Item(ItemType.ITEM_GUN, damage));
        assertEquals(healthCompare, this.character.getHealth());
    }

    @Test
    @Order(2)
    public void moveTest()
    {
        CollisionDetection detection = mock(CollisionDetection.class);
        when(detection.checkCoords(1, 1)).thenReturn(true);
        when(detection.checkCoords(0,0)).thenReturn(false);

        this.character.setPosition(1, 0);
        this.character.attachCollision(detection);
        this.character.setMoving(true);
        this.character.move(Direction.MOVEMENT_RIGHT);
        assertEquals(1, this.character.getPositionY());

        this.character.move(Direction.MOVEMENT_LEFT);
        this.character.move(Direction.MOVEMENT_UP);
        assert(this.character.getPositionY() != 0 && this.character.getPositionX() != 0);
    }
}
