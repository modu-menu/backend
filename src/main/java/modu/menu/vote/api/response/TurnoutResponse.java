package modu.menu.vote.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import modu.menu.vote.service.dto.IsVoteServiceResponse;

import java.util.List;

@Schema(description = "투표율 조회 응답 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TurnoutResponse {

    @Schema(description = "투표율", example = "30%")
    private String turnout;

    @Schema(description = "투표 여부 목록")
    private List<IsVoteServiceResponse> participants;
}
