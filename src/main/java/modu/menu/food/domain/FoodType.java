package modu.menu.food.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FoodType {

    MEAT("한식", "육류,고기요리"),
    SEAFOOD("한식", "해물,생선요리"),
    WESTERN_FOOD("양식", "양식"),
    LATIN("양식", "멕시칸,브라질"),
    INDOOR_BAR("술집", "실내 포장마차"),
    FISHCAKE_BAR("술집", "오뎅바"),
    WINE_BAR("술집", "와인바"),
    COCKTAIL_BAR("술집", "칵테일바"),
    IZAKAYA("술집", "일본식주점"),
    HOF("술집", "호프,요리주점"),
    FUSION("퓨전요리", "퓨전요리"),
    CHICKEN("치킨", "치킨");

    private final String title;
    private final String detail;

    public String getTitle() {
        return title;
    }

    @JsonValue
    public String getDetail() {
        return detail;
    }
}
