package modu.menu.place.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import modu.menu.core.annotation.EnumValidation;
import modu.menu.core.response.ApiFailResponse;
import modu.menu.core.response.ApiSuccessResponse;
import modu.menu.food.domain.FoodType;
import modu.menu.place.api.response.SearchPlaceResponse;
import modu.menu.place.service.PlaceService;
import modu.menu.vibe.domain.VibeType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "장소")
@Validated
@RequiredArgsConstructor
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "음식점 후보 검색", description = """
            투표에 포함시킬 음식점 후보를 사용자의 입력 값을 바탕으로 검색합니다. 조건을 만족하는 음식점이 없을 경우 null을 반환합니다.
                        
            다음 Query String의 경우 대소문자를 구분하지 않습니다.
            food: KOREAN_FOOD(한식), KOREAN_SEAFOOD(한식 > 해물,생선), CONGEE(한식 > 죽), JOKBAL(한식 > 족발, 보쌈), HOT_POP(한식 > 찌개,전골), MEAT(한식 > 육류,고기), SUNDAE(한식 > 순대), STREET_FOOD(한식 > 분식), NOODLE(한식 > 면), LUNCH_BOX(한식 > 도시락), KOREAN_CHICKEN(한식 > 닭요리), INTESTINE(한식 > 곱창,막창), TEPPAN_YAKI(한식 > 철판요리), JAPANESE_FOOD(일식), CHINESE_FOOD(중식), WESTERN_FOOD(양식), HAMBURGER(양식 > 햄버거), WESTERN_SEAFOOD(양식 > 해산물), PIZZA(양식 > 피자), FRENCH_FOOD(양식 > 프랑스음식), FAST_FOOD(양식 > 패스트푸드), FAMILY_RESTAURANT(양식 > 패밀리레스토랑), CHICKEN(양식 > 치킨), ITALIAN_FOOD(양식 > 이탈리안), SPANISH_FOOD(양식 > 스페인), SALAD(양식 > 샐러드), LATIN(양식 > 멕시칸,브라질), ASIAN_FOOD(아시아음식), BAR(술집), BUFFET(뷔페), DESSERT(디저트)
            vibe: NOISY(시끌벅적해요), TRENDY(트렌디해요), GOOD_SERVICE(서비스가 좋아요), QUIET(조용해요), MODERN(모던해요), NICE_VIEW(뷰맛집이에요)""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색이 성공한 경우"),
            @ApiResponse(responseCode = "400", description = "Query String이 형식에 맞지 않을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "500", description = "그 외 서버에서 처리하지 못한 에러가 발생했을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class)))
    })
    @GetMapping("/api/place")
    public ResponseEntity<ApiSuccessResponse<SearchPlaceResponse>> searchPlace(
            @PositiveOrZero(message = "위도는 0 또는 양수여야 합니다.") @RequestParam(defaultValue = "37.505098") Double latitude,
            @PositiveOrZero(message = "경도는 0 또는 양수여야 합니다.") @RequestParam(defaultValue = "127.032941") Double longitude,
            @EnumValidation(enumClass = FoodType.class, message = "음식에 허용된 값만 입력해주세요.") @Size(min = 1, message = "음식은 입력 시 최소 1개의 값이 필요합니다.")
            @RequestParam(required = false) List<String> food,
            @EnumValidation(enumClass = VibeType.class, message = "분위기에 허용된 값만 입력해주세요.") @Size(min = 1, message = "분위기는 입력 시 최소 1개의 값이 필요합니다.")
            @RequestParam(required = false) List<String> vibe,
            @PositiveOrZero(message = "페이지 번호는 0 또는 양수여야 합니다.") @RequestParam(defaultValue = "0") Integer page
    ) {
        SearchPlaceResponse searchPlaceResponse = placeService.searchPlace(
                latitude,
                longitude,
                food != null ? food.stream().map(f -> FoodType.valueOf(f.toUpperCase())).toList() : null,
                vibe != null ? vibe.stream().map(v -> VibeType.valueOf(v.toUpperCase())).toList() : null,
                page
        );

        return ResponseEntity.ok()
                .body(new ApiSuccessResponse<>(searchPlaceResponse));
    }
}