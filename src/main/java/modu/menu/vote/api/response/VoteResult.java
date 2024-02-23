package modu.menu.vote.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "투표 결과 DTO")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteResult {

    @Schema(description = "음식점 이름", example = "타코벨")
    private String name;

    @Schema(description = "음식 종류", example = "멕시칸, 브라질")
    private String food;

    @Schema(description = "음식점 분위기 목록")
    private List<VibeResponse> vibes;

    @Schema(description = "음식점 도로명 주소", example = "서울 서초구 서초대로74길 11 삼성전자 강남사옥 지하1층 B108호")
    private String address;

    @Schema(description = "음식점과의 거리", example = "10m")
    private String distance;

    @Schema(description = "음식점 관련 이미지 주소")
    private String img;

    @Schema(description = "해당 음식점 투표율", example = "70%")
    private String voteRating;
}
