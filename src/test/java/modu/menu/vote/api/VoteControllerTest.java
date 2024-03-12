package modu.menu.vote.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import modu.menu.vibe.domain.VibeType;
import modu.menu.vote.api.request.VoteResultRequest;
import modu.menu.vote.api.response.VoteResultResponse;
import modu.menu.vote.service.VoteService;
import modu.menu.vote.service.dto.VoteResultServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("VoteController 단위테스트")
@ActiveProfiles("test")
@WebMvcTest(VoteController.class)
class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private VoteService voteService;

    @DisplayName("투표에 회원을 초대하면 성공한다.")
    @Test
    void invite() throws Exception {
        // given
        Long voteId = 1L;
        Long userId = 1L;
        Long tokenUserId = 1L;

        // when
        doNothing().when(voteService).invite(anyLong(), anyLong());

        // then
        mockMvc.perform(post("/api/vote/{voteId}/user/{userId}", voteId, userId)
                        .requestAttr("userId", tokenUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.reason").value("OK"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("투표에 회원을 초대할 때 voteId는 양수이다.")
    @Test
    void inviteWithZeroVoteId() throws Exception {
        // given
        Long voteId = 0L;
        Long userId = 1L;
        Long tokenUserId = 1L;

        // when
        doNothing().when(voteService).invite(anyLong(), anyLong());

        // then
        mockMvc.perform(post("/api/vote/{voteId}/user/{userId}", voteId, userId)
                        .requestAttr("userId", tokenUserId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("voteId는 양수여야 합니다."));
    }

    @DisplayName("투표에 회원을 초대할 때 userId는 양수이다.")
    @Test
    void inviteWithZeroUserId() throws Exception {
        // given
        Long voteId = 1L;
        Long userId = 0L;
        Long tokenUserId = 1L;

        // when
        doNothing().when(voteService).invite(anyLong(), anyLong());

        // then
        mockMvc.perform(post("/api/vote/{voteId}/user/{userId}", voteId, userId)
                        .requestAttr("userId", tokenUserId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("userId는 양수여야 합니다."));
    }

    @DisplayName("투표에 회원을 초대할 때 userId와 tokenUserId는 같아야 한다.")
    @Test
    void inviteCantMatchUserIdWithTokenUserId() throws Exception {
        // given
        Long voteId = 1L;
        Long userId = 1L;
        Long tokenUserId = 2L;

        // when
        doNothing().when(voteService).invite(anyLong(), anyLong());

        // then
        mockMvc.perform(post("/api/vote/{voteId}/user/{userId}", voteId, userId)
                        .requestAttr("userId", tokenUserId))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.reason").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("토큰과 Path Variable의 id가 일치하지 않습니다."));
    }

    @DisplayName("투표 결과를 조회하면 성공한다.")
    @Test
    void getResult() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .latitude(126.977966)
                .longitude(37.566535)
                .build();

        // when
        when(voteService.getResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultResponse(
                        List.of(createResult("타코벨"),
                                createResult("매드포갈릭 강남삼성타운점"),
                                createResult("창고 43 강남점")))
                );

        // then
        mockMvc.perform(post("/api/vote/{voteId}/result", voteId)
                        .content(objectMapper.writeValueAsString(voteResultRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.reason").value("OK"))
                .andExpect(jsonPath("$.data").isMap());
    }

    @DisplayName("투표 결과를 조회할 때 위도는 필수이다.")
    @Test
    void getResultWithNoLatitude() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .longitude(37.566535)
                .build();

        // when
        when(voteService.getResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultResponse(
                        List.of(createResult("타코벨"),
                                createResult("매드포갈릭 강남삼성타운점"),
                                createResult("창고 43 강남점")))
                );

        // then
        mockMvc.perform(post("/api/vote/{voteId}/result", voteId)
                        .content(objectMapper.writeValueAsString(voteResultRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("위도는 필수입니다."));
    }

    @DisplayName("투표 결과를 조회할 때 경도는 필수이다.")
    @Test
    void getResultWithNoLongitude() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .latitude(126.977966)
                .build();

        // when
        when(voteService.getResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultResponse(
                        List.of(createResult("타코벨"),
                                createResult("매드포갈릭 강남삼성타운점"),
                                createResult("창고 43 강남점")))
                );

        // then
        mockMvc.perform(post("/api/vote/{voteId}/result", voteId)
                        .content(objectMapper.writeValueAsString(voteResultRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("경도는 필수입니다."));
    }

    @DisplayName("투표 결과를 조회할 때 위도는 양수이다.")
    @Test
    void getResultWithZeroLatitude() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .latitude(0.0)
                .longitude(37.566535)
                .build();

        // when
        when(voteService.getResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultResponse(
                        List.of(createResult("타코벨"),
                                createResult("매드포갈릭 강남삼성타운점"),
                                createResult("창고 43 강남점")))
                );

        // then
        mockMvc.perform(post("/api/vote/{voteId}/result", voteId)
                        .content(objectMapper.writeValueAsString(voteResultRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("위도는 양수여야 합니다."));
    }

    @DisplayName("투표 결과를 조회할 때 경도는 양수이다.")
    @Test
    void getResultWithZeroLongitude() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .latitude(126.977966)
                .longitude(-1.1)
                .build();

        // when
        when(voteService.getResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultResponse(
                        List.of(createResult("타코벨"),
                                createResult("매드포갈릭 강남삼성타운점"),
                                createResult("창고 43 강남점")))
                );

        // then
        mockMvc.perform(post("/api/vote/{voteId}/result", voteId)
                        .content(objectMapper.writeValueAsString(voteResultRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("경도는 양수여야 합니다."));
    }

    private VoteResultServiceResponse createResult(String name) {
        return VoteResultServiceResponse.builder()
                .name(name)
                .food("멕시칸, 브라질")
                .vibes(List.of(VibeType.NOISY))
                .address("서울 종로구 삼일대로")
                .distance("10m")
                .img("이미지 주소")
                .voteRating("70%")
                .build();
    }
}
