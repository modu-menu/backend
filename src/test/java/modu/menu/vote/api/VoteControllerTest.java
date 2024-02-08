package modu.menu.vote.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import modu.menu.vote.api.request.VoteResultRequest;
import modu.menu.vote.api.response.VibeDto;
import modu.menu.vote.api.response.VoteResult;
import modu.menu.vote.api.response.VoteResultsResponse;
import modu.menu.vote.service.VoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("VoteController 단위테스트")
@ActiveProfiles("test")
@WebMvcTest(VoteController.class)
public class VoteControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private VoteService voteService;

    @DisplayName("투표 결과를 조회하면 성공한다.")
    @Test
    void getVoteResult() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .latitude(126.977966)
                .longitude(37.566535)
                .build();

        // when
        when(voteService.getVoteResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultsResponse(
                        List.of(createVoteResult("타코벨"),
                                createVoteResult("매드포갈릭 강남삼성타운점"),
                                createVoteResult("창고 43 강남점")))
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
    void getVoteResultWithNoLatitude() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .longitude(37.566535)
                .build();

        // when
        when(voteService.getVoteResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultsResponse(
                        List.of(createVoteResult("타코벨"),
                                createVoteResult("매드포갈릭 강남삼성타운점"),
                                createVoteResult("창고 43 강남점")))
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
    void getVoteResultWithNoLongitude() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .latitude(126.977966)
                .build();

        // when
        when(voteService.getVoteResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultsResponse(
                        List.of(createVoteResult("타코벨"),
                                createVoteResult("매드포갈릭 강남삼성타운점"),
                                createVoteResult("창고 43 강남점")))
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
    void getVoteResultWithZeroLatitude() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .latitude(0.0)
                .longitude(37.566535)
                .build();

        // when
        when(voteService.getVoteResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultsResponse(
                        List.of(createVoteResult("타코벨"),
                                createVoteResult("매드포갈릭 강남삼성타운점"),
                                createVoteResult("창고 43 강남점")))
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
    void getVoteResultWithZeroLongitude() throws Exception {
        // given
        Long voteId = 1L;
        VoteResultRequest voteResultRequest = VoteResultRequest.builder()
                .latitude(126.977966)
                .longitude(-1.1)
                .build();

        // when
        when(voteService.getVoteResult(anyLong(), any(VoteResultRequest.class)))
                .thenReturn(new VoteResultsResponse(
                        List.of(createVoteResult("타코벨"),
                                createVoteResult("매드포갈릭 강남삼성타운점"),
                                createVoteResult("창고 43 강남점")))
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

    private VoteResult createVoteResult(String name) {
        return VoteResult.builder()
                .name(name)
                .food("멕시칸, 브라질")
                .vibes(List.of(new VibeDto("시끌벅적해요")))
                .address("서울 종로구 삼일대로")
                .distance("10m")
                .img("이미지 주소")
                .voteRating("70%")
                .build();
    }
}
