package modu.menu.place.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import modu.menu.food.domain.FoodType;
import modu.menu.place.service.dto.SearchResultServiceResponse;
import modu.menu.vibe.domain.VibeType;

import java.util.List;

@Schema(description = "음식점 후보 검색 결과 DTO")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchPlaceResponse {

    @Schema(description = "검색에 사용한 음식 조건 목록", examples = {
            "멕시칸,브라질",
            "양식",
            "육류,고기요리",
            "오뎅바"})
    private List<FoodType> foods;

    @Schema(description = "검색에 사용한 분위기 조건 목록", examples = {
            "시끌벅적해요",
            "트렌디해요",
            "조용해요",
            "모던해요",
            "뷰맛집이에요",
            "서비스가 좋아요"})
    private List<VibeType> vibes;

    @Schema(description = "검색 결과 목록")
    private List<SearchResultServiceResponse> results;

    @Schema(description = "페이지 내 목록 갯수")
    private Long totalElements;

    @Schema(description = "총 페이지 수")
    private Integer totalPages;

    @Schema(description = "현재 페이지 번호")
    private Integer currentPageNumber;

    @Schema(description = "현재 페이지가 첫 번째 페이지인지 여부")
    private Boolean isFirst;

    @Schema(description = "현재 페이지가 마지막 페이지인지 여부")
    private Boolean isLast;

    @Schema(description = "보여줄 페이지가 하나도 없는지 여부")
    private Boolean isEmpty;
}