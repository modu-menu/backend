package modu.menu.vote.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import modu.menu.food.domain.Food;
import modu.menu.food.domain.FoodType;
import modu.menu.placevibe.domain.PlaceVibe;
import modu.menu.vibe.domain.VibeType;

import java.util.List;

@Schema(description = "투표함 정보 DTO")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteItemServiceResponse {

    @Schema(description = "일련번호")
    private Long id;

    @Schema(description = "음식점 이름")
    private String name;

    @Schema(description = "음식점 카테고리")
    private FoodType category;

    @Schema(description = "음식점 이미지 URL")
    private String imgUrl;

    @Schema(description = "음식점 분위기")
    private VibeType mood;

    @Schema(description = "음식점 주소")
    private String addr;


}
