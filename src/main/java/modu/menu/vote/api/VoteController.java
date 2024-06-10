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
import modu.menu.vote.api.request.SaveVoteRequest;
import modu.menu.vote.api.request.VoteRequest;
import modu.menu.vote.api.request.VoteResultRequest;
import modu.menu.vote.api.response.VoteResultResponse;
import modu.menu.vote.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "투표")
@Validated
@RequiredArgsConstructor
@RestController
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "투표 생성", description = "투표를 생성합니다. 투표를 생성한 회원이 주최자가 됩니다.")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표 생성이 성공한 경우"),
            @ApiResponse(responseCode = "400", description = "RequestBody가 형식에 맞지 않을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 인증이 실패한 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "404", description = "투표에 포함시키려는 음식점이 존재하지 않을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "500", description = "그 외 서버에서 처리하지 못한 에러가 발생했을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class)))
    })
    @PostMapping("/api/vote")
    public ResponseEntity<ApiSuccessResponse> saveVote(
            @Valid @RequestBody SaveVoteRequest saveVoteRequest
    ) {
        voteService.saveVote(saveVoteRequest);

        return ResponseEntity.ok()
                .body(new ApiSuccessResponse<>());
    }

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

    // 투표 종료
    @Operation(summary = "투표 종료", description = "투표를 종료합니다. 투표 주최자만 가능합니다.")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표 종료가 성공한 경우"),
            @ApiResponse(responseCode = "400", description = "PathVariable이 형식에 맞지 않거나 투표가 이미 종료된 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 인증이 실패한 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "403", description = "투표에 권한이 없는 회원인 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "404", description = "조회하려는 투표 자체가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "500", description = "그 외 서버에서 처리하지 못한 에러가 발생했을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class)))
    })
    @PatchMapping("/api/vote/{voteId}/status")
    public ResponseEntity<ApiSuccessResponse> finishVote(
            @Positive(message = "voteId는 양수여야 합니다.") @PathVariable("voteId") Long voteId
    ) {
        voteService.finishVote(voteId);

        return ResponseEntity.ok()
                .body(new ApiSuccessResponse<>());
    }

    @Operation(summary = "투표, 재투표", description = """
            투표하거나 재투표합니다. 초대받은 사람만 투표할 수 있습니다.
            placeId가 null이라면 기존 투표 기록을 삭제합니다.
            """)
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/api/vote/{voteId}")
    public ResponseEntity<ApiSuccessResponse> vote(
            @Positive(message = "voteId는 양수여야 합니다.") @PathVariable("voteId") Long voteId,
            @Valid @RequestBody VoteRequest voteRequest
    ) {
        voteService.vote(voteId, voteRequest);

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
}
