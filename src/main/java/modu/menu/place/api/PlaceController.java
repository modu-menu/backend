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

    @Operation(summary = "음식점 후보 검색", description = "투표에 포함시킬 음식점 후보를 사용자의 입력 값을 바탕으로 검색합니다.\n\n" +
            "다음 Query String의 경우 대소문자를 구분하지 않습니다.\n\n" +
            "food: MEAT(육류,고기요리), SEAFOOD(해물,생선요리), WESTERN_FOOD(양식), LATIN(멕시칸,브라질), INDOOR_BAR(실내 포장마차), FISHCAKE_BAR(오뎅바), WINE_BAR(와인바), COCKTAIL_BAR(칵테일바), IZAKAYA(일본식주점), HOF(호프,요리주점), FUSION(퓨전요리), CHICKEN(치킨)\n\n" +
            "vibe: NOISY(시끌벅적해요), TRENDY(트렌디해요), GOOD_SERVICE(서비스가 좋아요), QUIET(조용해요), MODERN(모던해요), NICE_VIEW(뷰맛집이에요)")
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