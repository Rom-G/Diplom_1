package praktikum;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static praktikum.IngredientType.FILLING;

public class BurgerChangeIngredientsTest {

    private final int REMOVE_INDEX = 0;
    private final int NEW_INDEX = 2;
    private Burger burger;
    List<Ingredient> ingredients;

    @Before
    public void setUp() {
        burger = new Burger();
        Ingredient cutlet = new Ingredient(FILLING, "cutlet", 100);
        Ingredient dinosaur = new Ingredient(FILLING, "dinosaur", 200);
        Ingredient sausage = new Ingredient(FILLING, "sausage", 300);
        ingredients = List.of(cutlet, dinosaur, sausage);

        for (Ingredient ing : ingredients) {
            burger.addIngredient(ing);
        }
    }

    @Test
    public void removeIngredientDecreasesSizeListTest() {
        burger.removeIngredient(REMOVE_INDEX);

        assertEquals("Размер списка не совпадает с ожидаемым",
                ingredients.size() - 1,
                burger.ingredients.size());
    }

    @Test
    public void removeIngredientRemovesIngredientTest() {
        burger.removeIngredient(REMOVE_INDEX);

        assertFalse("Ингредиент " + ingredients.get(REMOVE_INDEX).getName() + " не удалён",
                burger.ingredients.contains(ingredients.get(REMOVE_INDEX)));
    }

    @Test
    public void removeIngredientShiftsTheRightElementTest() {
        Assume.assumeFalse("SKIPPED: удаляется последний ингредиент в списке",
                REMOVE_INDEX == ingredients.size() - 1);

        burger.removeIngredient(REMOVE_INDEX);

        assertEquals("Ингредиент на месте удалённого не совпадает с ожидаемым",
                ingredients.get(REMOVE_INDEX + 1),
                burger.ingredients.get(REMOVE_INDEX));
    }

    @Test
    public void moveIngredientSetsToNewIndexTest() {
        burger.moveIngredient(REMOVE_INDEX, NEW_INDEX);

        assertEquals("Ингредиент на новом месте не совпадает с ожидаемым",
                ingredients.get(REMOVE_INDEX).getName(),
                burger.ingredients.get(NEW_INDEX).getName());
    }

    @Test
    public void moveIngredientBringsListBackToOriginalSizeTest() {
        burger.moveIngredient(REMOVE_INDEX, NEW_INDEX);

        assertEquals("Размер списка не совпадает с ожидаемым",
                ingredients.size(),
                burger.ingredients.size());
    }

    @Test
    public void moveIngredientShiftsNeighboringElementTest() {
        List<Ingredient> expected = new ArrayList<>(ingredients);
        Ingredient moved = expected.remove(REMOVE_INDEX);
        expected.add(NEW_INDEX, moved);

        burger.moveIngredient(REMOVE_INDEX, NEW_INDEX);

        assertEquals("Порядок ингредиентов после перемещения не совпадает с ожидаемым",
                expected, burger.ingredients);
    }
}
