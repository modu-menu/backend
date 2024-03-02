package modu.menu.place.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import modu.menu.core.annotation.EnumValidation;
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

    @Operation(summary = "음식점 후보 검색", description = "투표에 포함시킬 음식점 후보를 사용자의 입력 값을 바탕으로 검색합니다.")
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