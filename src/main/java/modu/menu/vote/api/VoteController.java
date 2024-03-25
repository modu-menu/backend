package modu.menu.vote.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import modu.menu.core.exception.Exception401;
import modu.menu.core.response.ApiFailResponse;
import modu.menu.core.response.ApiSuccessResponse;
import modu.menu.core.response.ErrorMessage;
import modu.menu.vote.api.request.VoteResultRequest;
import modu.menu.vote.api.response.VoteResultResponse;
import modu.menu.vote.service.VoteService;
import modu.menu.vote.service.dto.VoteItemServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "투표")
@Validated
@RequiredArgsConstructor
@RestController
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "투표 초대", description = "투표에 회원을 초대합니다.")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "초대가 성공한 경우"),
            @ApiResponse(responseCode = "400", description = "PathVariable이 형식에 맞지 않을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 인증이 실패한 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "404", description = "조회하려는 투표 자체가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "500", description = "그 외 서버에서 처리하지 못한 에러가 발생했을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class)))
    })
    @PostMapping("/api/vote/{voteId}/user/{userId}")
    public ResponseEntity<ApiSuccessResponse> invite(
            @Positive(message = "voteId는 양수여야 합니다.") @PathVariable("voteId") Long voteId,
            @Positive(message = "userId는 양수여야 합니다.") @PathVariable("userId") Long userId,
            @RequestAttribute("userId") Long tokenUserId
    ) {
        if (!userId.equals(tokenUserId)) {
            throw new Exception401(ErrorMessage.CANT_MATCH_TOKEN_WITH_PATH_VARIABLE);
        }

        voteService.invite(voteId, userId);

        return ResponseEntity.ok()
                .body(new ApiSuccessResponse<>());
    }

    /**
     * 회원의 위도와 경도를 외부에 노출해선 안 된다고 판단하여
     * HTTPS로 통신하는 점을 이용해 RequestBody에 위치 데이터를 담아서 요청하도록 설계
     */
    @Operation(summary = "투표 결과 조회", description = "투표 결과를 조회합니다. 회원이라면 모두 조회 가능합니다.")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회가 성공한 경우"),
            @ApiResponse(responseCode = "400", description = "VoteResultRequest 또는 PathVariable이 형식에 맞지 않을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 인증이 실패한 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "404", description = "조회하려는 투표 자체가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "500", description = "그 외 서버에서 처리하지 못한 에러가 발생했을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class)))
    })
    @PostMapping("/api/vote/{voteId}/result")
    public ResponseEntity<ApiSuccessResponse<VoteResultResponse>> getResult(
            @Positive(message = "voteId는 양수여야 합니다.") @PathVariable("voteId") Long voteId,
            @Valid @RequestBody VoteResultRequest voteResultRequest
    ) {

        VoteResultResponse response = voteService.getResult(voteId, voteResultRequest);

        return ResponseEntity.ok()
                .body(new ApiSuccessResponse<>(response));
    }

    @Operation(summary = "투표 음식점 리스트 조회", description = "투표 음식점 리스트 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회가 성공한 경우"),
            @ApiResponse(responseCode = "401", description = "토큰 인증이 실패한 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(schema = @Schema(implementation = ApiFailResponse.class)))
    })
    @GetMapping("/api/vote-list")
    public ResponseEntity<ApiSuccessResponse<List<List<VoteItemServiceResponse>>>> getList(Long[] itemId) {
        List<List<VoteItemServiceResponse>> voteItemList = voteService.getVoteItemList(new Long[]{1L});
        return ResponseEntity.ok().body(new ApiSuccessResponse<>(voteItemList));
    }
}
