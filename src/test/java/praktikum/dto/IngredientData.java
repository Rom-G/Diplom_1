package praktikum.dto;

import praktikum.IngredientType;

public class IngredientData {

    public final IngredientType type;
    public final String name;
    public final float price;

    public IngredientData(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }
}
