package praktikum;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import praktikum.dto.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static praktikum.IngredientType.*;
import static praktikum.ReceiptData.*;

@RunWith(Parameterized.class)
public class BurgerParameterizedTest {

    private final BunData bunData;
    private final List<IngredientData> ingredientDataList;
    private final float expectedPrice;
    private final String expectedReceipt;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Bun bun;

    private Burger burger;

    public BurgerParameterizedTest(BunData bunData, List<IngredientData> ingredientDataList,
                                   float expectedPrice, String expectedReceipt) {
        this.bunData = bunData;
        this.ingredientDataList = ingredientDataList;
        this.expectedPrice = expectedPrice;
        this.expectedReceipt = expectedReceipt;
    }

    @Before
    public void setUp() {
        burger = Mockito.spy(new Burger());

        when(bun.getName()).thenReturn(bunData.name);
        when(bun.getPrice()).thenReturn(bunData.price);
        burger.setBuns(bun);

        for (IngredientData ingData : ingredientDataList) {
            Ingredient ingredient = Mockito.mock(Ingredient.class);
            when(ingredient.getType()).thenReturn(ingData.type);
            when(ingredient.getName()).thenReturn(ingData.name);
            when(ingredient.getPrice()).thenReturn(ingData.price);
            burger.addIngredient(ingredient);
        }
    }

    @Parameterized.Parameters
    public static Object[][] getBurgerData() {
        return new Object[][] {
                {
                        new BunData("black bun", 100),
                        List.of(
                                new IngredientData(SAUCE, "hot sauce", 100),
                                new IngredientData(FILLING, "sausage", 300)
                        ),
                        600.0f,
                        RECEIPT_BLACK_HOT_SAUSAGE
                },
                {
                        new BunData("white bun", 200),
                        List.of(
                                new IngredientData(SAUCE, "sour cream", 200),
                                new IngredientData(SAUCE, "chili sauce", 300),
                                new IngredientData(FILLING, "cutlet", 100),
                                new IngredientData(FILLING, "dinosaur", 200)
                        ),
                        1200.0f,
                        RECEIPT_WHITE_SOUR_CHILI_DINOSAUR
                },
                {
                        new BunData("red bun", 300),
                        List.of(
                                new IngredientData(SAUCE, "hot sauce", 100),
                                new IngredientData(SAUCE, "sour cream", 200),
                                new IngredientData(SAUCE, "chili sauce", 300),
                                new IngredientData(FILLING, "sausage", 300),
                                new IngredientData(FILLING, "dinosaur", 200),
                                new IngredientData(FILLING, "cutlet", 100)
                        ),
                        1800.0f,
                        RECEIPT_RED_DOUBLE_TRIPLE
                },
                {
                        new BunData("red bun", 300),
                        List.of(
                                new IngredientData(SAUCE, "sour cream", 200)
                        ),
                        800.0f,
                        RECEIPT_LONELY_SOUR
                },
                {
                        new BunData("black bun", 100),
                        List.of(
                                new IngredientData(FILLING, "dinosaur", 200)
                        ),
                        400.0f,
                        RECEIPT_LONELY_FILLING
                }
        };
    }

    @Test
    public void getPriceReturnsExpectedValueTest() {
        assertEquals("Expected price: " + expectedPrice + " differ from the actual price: " + burger.getPrice(),
                expectedPrice,
                burger.getPrice(),
                0.001f);
    }

    @Test
    public void getReceiptReturnsExpectedValueTest() {
        assertEquals("Рецепты отличаются",
                expectedReceipt.replaceAll("\\R", "\n"),
                burger.getReceipt().replaceAll("\\R", "\n"));
    }
}
