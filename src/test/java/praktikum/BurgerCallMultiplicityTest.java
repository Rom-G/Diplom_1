package praktikum;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.dto.IngredientData;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static praktikum.IngredientType.FILLING;

@RunWith(MockitoJUnitRunner.class)
public class BurgerCallMultiplicityTest {

    @Mock
    private Bun bun;

    private Burger burger;
    List<Ingredient> ingredientMocks;

    @Before
    public void setUp() {
        burger = Mockito.spy(new Burger());
        burger.setBuns(bun);
        ingredientMocks = new ArrayList<>();
        List<IngredientData> ingredientDataList = List.of(
                new IngredientData(FILLING, "cutlet", 100),
                new IngredientData(FILLING, "dinosaur", 200),
                new IngredientData(FILLING, "sausage", 300));

        for (IngredientData ingData : ingredientDataList) {
            Ingredient ingMock = Mockito.mock(Ingredient.class);
            when(ingMock.getType()).thenReturn(ingData.type);
            when(ingMock.getName()).thenReturn(ingData.name);
            when(ingMock.getPrice()).thenReturn(ingData.price);
            ingredientMocks.add(ingMock);
            burger.addIngredient(ingMock);
        }
    }

    @Test
    public void getPriceCallsBunGetPriceOnceTest() {
        burger.getPrice();
        verify(bun, times(1)).getPrice();
    }

    @Test
    public void getPriceCallsIngredientGetPriceOnceTest() {
        burger.getPrice();

        for (Ingredient ing : ingredientMocks) {
            verify(ing, times(1)).getPrice();
        }
    }

    @Test
    public void getReceiptCallsBunGetNameTwiceTest() {
        burger.getReceipt();
        verify(bun, times(2)).getName();
    }

    @Test
    public void getReceiptCallsIngredientGetTypeOnceTest() {
        burger.getReceipt();

        for (Ingredient ing : ingredientMocks) {
            verify(ing, times(1)).getType();
        }
    }

    @Test
    public void getReceiptCallsIngredientGetNameOnceTest() {
        burger.getReceipt();

        for (Ingredient ing : ingredientMocks) {
            verify(ing, times(1)).getName();
        }
    }

    @Test
    public void getReceiptCallsBurgerGetPriceOnceTest() {
        burger.getReceipt();

        verify(burger, times(1)).getPrice();
    }
}
