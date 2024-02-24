package modu.menu.review.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import modu.menu.review.api.request.CreateReviewRequest;
import modu.menu.review.api.request.VibeRequest;
import modu.menu.review.domain.HasRoom;
import modu.menu.review.service.ReviewService;
import modu.menu.vibe.domain.VibeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("ReviewController 단위테스트")
@ActiveProfiles("test")
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ReviewService reviewService;

    @DisplayName("리뷰를 등록하면 성공한다.")
    @Test
    void createReview() throws Exception {
        // given
        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(1)
                .vibes(List.of(new VibeRequest(VibeType.NOISY.name())))
                .participants(3)
                .hasRoom(HasRoom.NO.name())
                .content("리뷰")
                .build();

        // when
        doNothing().when(reviewService).createReview(anyLong(), anyLong(), any(CreateReviewRequest.class));

        // then
        mockMvc.perform(post("/api/place/{placeId}/review", placeId)
                        .requestAttr("userId", userId)
                        .content(objectMapper.writeValueAsString(createReviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.reason").value("OK"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("리뷰를 등록할 때 평점은 필수이다.")
    @Test
    void createReviewWithNoRating() throws Exception {
        // given
        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(null)
                .vibes(List.of(new VibeRequest(VibeType.NOISY.name())))
                .participants(3)
                .hasRoom(HasRoom.NO.name())
                .content("리뷰")
                .build();

        // when
        doNothing().when(reviewService).createReview(anyLong(), anyLong(), any(CreateReviewRequest.class));

        // then
        mockMvc.perform(post("/api/place/{placeId}/review", placeId)
                        .requestAttr("userId", userId)
                        .content(objectMapper.writeValueAsString(createReviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("rating은 필수입니다."));
    }

    @DisplayName("리뷰를 등록할 때 평점은 최소 1이다.")
    @Test
    void createReviewWithUnderRating() throws Exception {
        // given
        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(0)
                .vibes(List.of(new VibeRequest(VibeType.NOISY.name())))
                .participants(3)
                .hasRoom(HasRoom.NO.name())
                .content("리뷰")
                .build();

        // when
        doNothing().when(reviewService).createReview(anyLong(), anyLong(), any(CreateReviewRequest.class));

        // then
        mockMvc.perform(post("/api/place/{placeId}/review", placeId)
                        .requestAttr("userId", userId)
                        .content(objectMapper.writeValueAsString(createReviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("최소 평점은 1입니다."));
    }

    @DisplayName("리뷰를 등록할 때 평점은 최대 5이다.")
    @Test
    void createReviewWithOverRating() throws Exception {
        // given
        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(6)
                .vibes(List.of(new VibeRequest(VibeType.NOISY.name())))
                .participants(3)
                .hasRoom(HasRoom.NO.name())
                .content("리뷰")
                .build();

        // when
        doNothing().when(reviewService).createReview(anyLong(), anyLong(), any(CreateReviewRequest.class));

        // then
        mockMvc.perform(post("/api/place/{placeId}/review", placeId)
                        .requestAttr("userId", userId)
                        .content(objectMapper.writeValueAsString(createReviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("최대 평점은 5입니다."));
    }

    @DisplayName("리뷰를 등록할 때 분위기를 입력할 경우 하나 이상의 값을 가져야 한다.")
    @Test
    void createReviewWithNoVibe() throws Exception {
        // given
        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(6)
                .vibes(List.of())
                .participants(3)
                .hasRoom(HasRoom.NO.name())
                .content("리뷰")
                .build();

        // when
        doNothing().when(reviewService).createReview(anyLong(), anyLong(), any(CreateReviewRequest.class));

        // then
        mockMvc.perform(post("/api/place/{placeId}/review", placeId)
                        .requestAttr("userId", userId)
                        .content(objectMapper.writeValueAsString(createReviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("vibes는 하나 이상의 값이 필요합니다."));
    }

    @DisplayName("리뷰를 등록할 때 분위기에 올바른 값을 입력해야 한다.")
    @Test
    void createReviewWithCorrectVibe() throws Exception {
        // given
        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(1)
                .vibes(List.of(new VibeRequest("fail")))
                .participants(3)
                .hasRoom(HasRoom.NO.name())
                .content("리뷰")
                .build();

        // when
        doNothing().when(reviewService).createReview(anyLong(), anyLong(), any(CreateReviewRequest.class));

        // then
        mockMvc.perform(post("/api/place/{placeId}/review", placeId)
                        .requestAttr("userId", userId)
                        .content(objectMapper.writeValueAsString(createReviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("허용되는 값만 입력해주세요."));
    }

    @DisplayName("리뷰를 등록할 때 참가자는 0 이상의 양수여야 한다.")
    @Test
    void createReviewWithUnderParticipants() throws Exception {
        // given
        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(1)
                .vibes(List.of(new VibeRequest(VibeType.NOISY.name())))
                .participants(-1)
                .hasRoom(HasRoom.NO.name())
                .content("리뷰")
                .build();

        // when
        doNothing().when(reviewService).createReview(anyLong(), anyLong(), any(CreateReviewRequest.class));

        // then
        mockMvc.perform(post("/api/place/{placeId}/review", placeId)
                        .requestAttr("userId", userId)
                        .content(objectMapper.writeValueAsString(createReviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("participants는 0 이상의 양수여야 합니다."));
    }

    @DisplayName("리뷰를 등록할 때 룸 여부에 올바른 값을 입력해야 한다.")
    @Test
    void createReviewWithCorrectHasRoom() throws Exception {
        // given
        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(1)
                .vibes(List.of(new VibeRequest(VibeType.NOISY.name())))
                .participants(3)
                .hasRoom("fail")
                .content("리뷰")
                .build();

        // when
        doNothing().when(reviewService).createReview(anyLong(), anyLong(), any(CreateReviewRequest.class));

        // then
        mockMvc.perform(post("/api/place/{placeId}/review", placeId)
                        .requestAttr("userId", userId)
                        .content(objectMapper.writeValueAsString(createReviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("'YES', 'NO, 'UNKNOWN'만 입력해주세요."));
    }

    @DisplayName("리뷰를 등록할 때 내용이 100자 이내여야 한다.")
    @Test
    void createReviewWithOverContent() throws Exception {
        // given
        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(1)
                .vibes(List.of(new VibeRequest(VibeType.NOISY.name())))
                .participants(3)
                .hasRoom(HasRoom.NO.name())
                .content("--------------------------------------------------------------------" +
                        "--------------------------------------------------------------------" +
                        "--------------------------------------------------------------------")
                .build();

        // when
        doNothing().when(reviewService).createReview(anyLong(), anyLong(), any(CreateReviewRequest.class));

        // then
        mockMvc.perform(post("/api/place/{placeId}/review", placeId)
                        .requestAttr("userId", userId)
                        .content(objectMapper.writeValueAsString(createReviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.reason").value("Bad Request"))
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("100자 이내로 입력해주세요."));
    }
}
