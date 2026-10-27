package praktikum;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.dto.IngredientData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static praktikum.IngredientType.FILLING;

@RunWith(MockitoJUnitRunner.class)
public class BurgerChangeIngredientsTest {

    private final int REMOVE_INDEX = 0;
    private final int NEW_INDEX = 2;
    private Burger burger;
    List<Ingredient> ingredientMocks;

    @Before
    public void setUp() {
        burger = new Burger();
        ingredientMocks = new ArrayList<>();
        List<IngredientData> ingredientDataList = List.of(
                new IngredientData(FILLING, "cutlet", 100),
                new IngredientData(FILLING, "dinosaur", 200),
                new IngredientData(FILLING, "sausage", 300));

        for (IngredientData ingData : ingredientDataList) {
            Ingredient ingMock = Mockito.mock(Ingredient.class);
            when(ingMock.getName()).thenReturn(ingData.name);
            ingredientMocks.add(ingMock);
            burger.addIngredient(ingMock);
        }
    }

    @Test
    public void removeIngredientDecreasesSizeListTest() {
        burger.removeIngredient(REMOVE_INDEX);

        assertEquals("Размер списка не совпадает с ожидаемым",
                ingredientMocks.size() - 1,
                burger.ingredients.size());
    }

    @Test
    public void removeIngredientRemovesIngredientTest() {
        burger.removeIngredient(REMOVE_INDEX);

        assertFalse("Ингредиент " + ingredientMocks.get(REMOVE_INDEX).getName() + " не удалён",
                burger.ingredients.contains(ingredientMocks.get(REMOVE_INDEX)));
    }

    @Test
    public void removeIngredientShiftsTheRightElementTest() {
        Assume.assumeFalse("SKIPPED: удаляется последний ингредиент в списке",
                REMOVE_INDEX == ingredientMocks.size() - 1);

        burger.removeIngredient(REMOVE_INDEX);

        assertEquals("Ингредиент на месте удалённого не совпадает с ожидаемым",
                ingredientMocks.get(REMOVE_INDEX + 1),
                burger.ingredients.get(REMOVE_INDEX));
    }

    @Test
    public void moveIngredientSetsToNewIndexTest() {
        burger.moveIngredient(REMOVE_INDEX, NEW_INDEX);

        assertEquals("Ингредиент на новом месте не совпадает с ожидаемым",
                ingredientMocks.get(REMOVE_INDEX).getName(),
                burger.ingredients.get(NEW_INDEX).getName());
    }

    @Test
    public void moveIngredientBringsListBackToOriginalSizeTest() {
        burger.moveIngredient(REMOVE_INDEX, NEW_INDEX);

        assertEquals("Размер списка не совпадает с ожидаемым",
                ingredientMocks.size(),
                burger.ingredients.size());
    }

    @Test
    public void moveIngredientShiftsNeighboringElementTest() {
        List<Ingredient> expected = new ArrayList<>(ingredientMocks);
        Ingredient moved = expected.remove(REMOVE_INDEX);
        expected.add(NEW_INDEX, moved);

        burger.moveIngredient(REMOVE_INDEX, NEW_INDEX);

        assertEquals("Порядок ингредиентов после перемещения не совпадает с ожидаемым",
                expected, burger.ingredients);
    }
}
