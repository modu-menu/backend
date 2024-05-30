package modu.menu.place.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import modu.menu.food.domain.FoodType;

import java.util.List;

@Schema(description = "음식점 카테고리 DTO")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodTypeServiceResponse {

    @Schema(description = "음식점 카테고리 key")
    private String key;

    @Schema(description = "음식점 카테고리 value")
    private String value;

    @Schema(description = "하위 카테고리 목록")
    private List<FoodTypeServiceResponse> children;

    // 최상위 카테고리부터 시작하여 전체 계층 구조를 반환
    public static List<FoodTypeServiceResponse> getFoodTypeHierarchy() {
        return FoodType.getTopLevelCategories().stream()
                .map(FoodTypeServiceResponse::toFoodTypeServiceResponse)
                .toList();
    }

    private static FoodTypeServiceResponse toFoodTypeServiceResponse(FoodType foodType) {
        return FoodTypeServiceResponse.builder()
                .key(foodType.name())
                .value(foodType.getTitle())
                .children(FoodType.getSubCategoriesStream(foodType)
                        .map(FoodTypeServiceResponse::toFoodTypeServiceResponse)
                        .toList())
                .build();
    }
}
