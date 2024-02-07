package modu.menu.vote.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import modu.menu.core.exception.Exception400;
import modu.menu.core.response.ApiSuccessResponse;
import modu.menu.vote.api.request.VoteResultRequest;
import modu.menu.vote.api.response.VoteResultsResponse;
import modu.menu.vote.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
public class VoteController {

    private final VoteService voteService;

    /**
     * 투표 결과 조회
     * 회원의 위도와 경도를 외부에 노출해선 안 된다고 판단했고,
     * HTTPS로 통신하는 점을 이용해 RequestBody에 위치 데이터를 담아서 요청하도록 설계
     */
    @PostMapping("/api/vote/{voteId}/result")
    public ResponseEntity<ApiSuccessResponse<VoteResultsResponse>> getVoteResult(
            @Positive @PathVariable Long voteId,
            BindingResult requestParambindingResult,
            @RequestBody VoteResultRequest voteResultRequest,
            BindingResult requestBodybindingResult
    ) {
        // TODO ConstraintViolationException, MethodArgumentNotValidException 처리하는 ExceptionHandler 작성
        bindingResultResolver(requestParambindingResult);
        bindingResultResolver(requestBodybindingResult);

        VoteResultsResponse response = voteService.getVoteResult(voteId, voteResultRequest);

        return ResponseEntity.ok()
                .body(new ApiSuccessResponse<>(response));
    }

    // BindingResult에 에러가 있을 시(@Validated에 의해 유효성 검사를 통과하지 못한 경우) Exception400을 던진다.
    private void bindingResultResolver(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new Exception400(
                    bindingResult.getFieldErrors().get(0).getField(),
                    bindingResult.getFieldErrors().get(0).getDefaultMessage()
            );
        }
    }
}
