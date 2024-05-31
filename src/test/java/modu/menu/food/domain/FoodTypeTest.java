package modu.menu.food.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FoodTypeTest {

    @DisplayName("FoodType의 최상위 Enum 목록을 반환한다.")
    @Test
    void getTopLevelCategories() {
        // given

        // when
        List<FoodType> result = FoodType.getTopLevelCategories();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result)
                .extracting(Enum::name)
                .containsExactlyInAnyOrder(
                        FoodType.KOREAN_FOOD.name(),
                        FoodType.WESTERN_FOOD.name(),
                        FoodType.CHINESE_FOOD.name(),
                        FoodType.JAPANESE_FOOD.name(),
                        FoodType.ASIAN_FOOD.name(),
                        FoodType.BAR.name(),
                        FoodType.BUFFET.name(),
                        FoodType.DESSERT.name()
                )
                .doesNotContain(FoodType.CHICKEN.name());
    }

    @DisplayName("재귀 호출을 통해 parent의 FoodType 하위 Enum 목록을 반환한다.")
    @Test
    void getSubCategories() {
        // given
        FoodType parent = FoodType.KOREAN_FOOD;

        // when
        List<FoodType> result = FoodType.getSubCategories(parent);

        // then
        assertThat(result).isNotEmpty();
        System.out.println(result);
        assertThat(result)
                .extracting(Enum::name)
                .containsExactlyInAnyOrder(
                        FoodType.KOREAN_SEAFOOD.name(),
                        FoodType.CONGEE.name(),
                        FoodType.JOKBAL.name(),
                        FoodType.HOT_POT.name(),
                        FoodType.MEAT.name(),
                        FoodType.SUNDAE.name(),
                        FoodType.STREET_FOOD.name(),
                        FoodType.NOODLE.name(),
                        FoodType.LUNCH_BOX.name(),
                        FoodType.KOREAN_CHICKEN.name(),
                        FoodType.INTESTINE.name(),
                        FoodType.TEPPAN_YAKI.name()
                )
                .doesNotContain(
                        FoodType.CHINESE_FOOD.name(),
                        FoodType.HAMBURGER.name()
                );
    }
}