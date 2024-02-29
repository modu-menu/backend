package modu.menu.vote.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import modu.menu.vote.service.dto.VoteResultServiceResponse;

import java.util.List;

@Schema(description = "투표 결과 조회 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteResultResponse {

    @Schema(description = "투표 결과 목록")
    private List<VoteResultServiceResponse> results;
}
