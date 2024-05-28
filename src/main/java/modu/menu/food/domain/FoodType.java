package modu.menu.food.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodType {

    // 한식
    KOREAN_FOOD("한식"),
    KOREAN_SEAFOOD("해물,생선"),
    CONGEE("죽"),
    JOKBAL("족발, 보쌈"),
    HOT_POP("찌개,전골"),
    MEAT("육류,고기"),
    SUNDAE("순대"),
    STREET_FOOD("분식"),
    NOODLE("면"),
    LUNCH_BOX("도시락"),
    KOREAN_CHICKEN("닭요리"),
    INTESTINE("곱창,막창"),
    TEPPAN_YAKI("철판요리"),

    // 일식, 중식
    JAPANESE_FOOD("일식"),
    CHINESE_FOOD("중식"),

    // 양식
    WESTERN_FOOD("양식"),
    HAMBURGER("햄버거"),
    WESTERN_SEAFOOD("해산물"),
    PIZZA("피자"),
    FRENCH_FOOD("프랑스음식"),
    FAST_FOOD("패스트푸드"),
    FAMILY_RESTAURANT("패밀리레스토랑"),
    CHICKEN("치킨"),
    ITALIAN_FOOD("이탈리안"),
    SPANISH_FOOD("스페인"),
    SALAD("샐러드"),
    LATIN("멕시칸,브라질"),

    // 기타
    ASIAN_FOOD("아시아음식"),
    BAR("술집"),
    BUFFET("뷔페"),
    DESSERT("디저트");

    private final String title;

}
