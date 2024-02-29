package modu.menu.place.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
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
            @NotNull(message = "위도는 필수입니다.") @PositiveOrZero(message = "위도는 0 또는 양수여야 합니다.") @RequestParam Double latitude,
            @NotNull(message = "경도는 필수입니다.") @PositiveOrZero(message = "경도는 0 또는 양수여야 합니다.") @RequestParam Double longitude,
            @Size(min = 1, message = "음식은 입력 시 최소 1개의 값이 필요합니다.") @RequestParam List<FoodType> food,
            @Size(min = 1, message = "분위기는 입력 시 최소 1개의 값이 필요합니다.") @RequestParam List<VibeType> vibe,
            @NotNull(message = "페이지 번호는 필수입니다.") @PositiveOrZero(message = "페이지 번호는 0 또는 양수여야 합니다.") @RequestParam(defaultValue = "0") Integer page
    ) {
        SearchPlaceResponse searchPlaceResponse = placeService.searchPlace(latitude, longitude, food, vibe, page);

        return ResponseEntity.ok(new ApiSuccessResponse<>(searchPlaceResponse));
    }
}