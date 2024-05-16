package modu.menu.food.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FoodType {

    KOREAN_FOOD("한식", "한식"),
    KOREAN_SEAFOOD("한식", "해물,생선"),
    CONGEE("한식", "죽"),
    JOKBAL("한식", "족발, 보쌈"),
    HOT_POP("한식", "찌개,전골"),
    MEAT("한식", "육류,고기"),
    SUNDAE("한식", "순대"),
    STREET_FOOD("한식", "분식"),
    NOODLE("한식", "면"),
    LUNCH_BOX("한식", "도시락"),
    KOREAN_CHICKEN("한식", "닭요리"),
    INTESTINE("한식", "곱창,막창"),
    TEPPAN_YAKI("한식", "철판요리"),
    JAPANESE_FOOD("일식", "일식"),
    CHINESE_FOOD("중식", "중식"),
    WESTERN_FOOD("양식", "양식"),
    HAMBURGER("양식", "햄버거"),
    WESTERN_SEAFOOD("양식", "해산물"),
    PIZZA("양식", "피자"),
    FRENCH_FOOD("양식", "프랑스음식"),
    FAST_FOOD("양식", "패스트푸드"),
    FAMILY_RESTAURANT("양식", "패밀리레스토랑"),
    CHICKEN("양식", "치킨"),
    ITALIAN_FOOD("양식", "이탈리안"),
    SPANISH_FOOD("양식", "스페인"),
    SALAD("양식", "샐러드"),
    LATIN("양식", "멕시칸,브라질"),
    ASIAN_FOOD("아시아음식", "아시아음식"),
    BAR("술집", "술집"),
    BUFFET("뷔페", "뷔페"),
    DESSERT("디저트", "디저트");

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
