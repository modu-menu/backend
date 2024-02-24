package modu.menu.review.api;

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
import modu.menu.core.response.ApiFailResponse;
import modu.menu.core.response.ApiSuccessResponse;
import modu.menu.review.api.request.CreateReviewRequest;
import modu.menu.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "리뷰")
@Validated
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록이 성공한 경우"),
            @ApiResponse(responseCode = "400", description = "CreateReviewRequest 또는 PathVariable이 형식에 맞지 않을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 인증이 실패한 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "404", description = "조회하려는 투표 자체가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class))),
            @ApiResponse(responseCode = "500", description = "그 외 서버에서 처리하지 못한 에러가 발생했을 경우", content = @Content(schema = @Schema(implementation = ApiFailResponse.class)))
    })
    @PostMapping("/api/place/{placeId}/review")
    public ResponseEntity<ApiSuccessResponse> createReview(
            @Positive(message = "placeId는 양수여야 합니다.") @PathVariable("placeId") Long placeId,
            @Valid @RequestBody CreateReviewRequest createReviewRequest,
            @RequestAttribute("userId") Long userId
    ) {
        reviewService.createReview(userId, placeId, createReviewRequest);

        return ResponseEntity.ok(new ApiSuccessResponse());
    }
}
