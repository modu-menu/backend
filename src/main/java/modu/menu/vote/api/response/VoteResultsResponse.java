package modu.menu.vote.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "투표 결과 조회 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteResultsResponse {

    @Schema(description = "투표 결과 목록")
    private List<VoteResult> results;
}
