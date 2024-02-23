package modu.menu.review.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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
