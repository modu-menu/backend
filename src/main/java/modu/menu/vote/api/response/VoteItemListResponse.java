package modu.menu.vote.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import modu.menu.vote.service.dto.VoteItemServiceResponse;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "투표 음식점 조회 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemListResponse {

    @Schema(description = "투표함 음식점 목록")
    private List<VoteItemServiceResponse> list = new ArrayList<>();
}
